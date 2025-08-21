package com.agent404.audiobook.ingestservice.service.impl;



import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

import org.flywaydb.core.internal.util.StringUtils;
import org.slf4j.Logger;

import com.agent404.audiobook.ingestservice.dto.FileUploadRequest;
import com.agent404.audiobook.ingestservice.exception.FileUploadException;
import com.agent404.audiobook.ingestservice.exception.ResourceNotFoundException;
import com.agent404.audiobook.ingestservice.model.BookStatus;
import com.agent404.audiobook.ingestservice.model.Books;
import com.agent404.audiobook.ingestservice.repository.BooksRepository;
import com.agent404.audiobook.ingestservice.service.IFileStorageService;
import com.agent404.audiobook.ingestservice.service.IFileUploadService;
import com.agent404.audiobook.ingestservice.service.ITextExtractionService;
import com.agent404.audiobook.ingestservice.service.IUserValidationClient;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Getter
@RequiredArgsConstructor
public class FileUploadServiceImpl implements IFileUploadService{
    
    private static final Logger logger = LoggerFactory.getLogger(FileUploadServiceImpl.class);
    

    private final IFileStorageService fileStorageService;
    private final int timeoutInSecs;
    private final IUserValidationClient userValidationClientImpl;
    private final String rawFilePatternPath;
    private final String textFilePatternPath;
    private final BooksRepository booksRepository;
    private final ITextExtractionService textExtractionService;


    @Override
    public Mono<String> handleUpload(FilePart  filePart,
                                     MediaType mediaType,
                                     String username,
                                     FileUploadRequest fileUploadRequest
                                    ) {

        
        
        // Build a context we’ll enrich step by step
        UploadContext ctx = new UploadContext();

        ctx.bookId = StringUtils.hasText(fileUploadRequest.getBookId())
        ? UUID.fromString(fileUploadRequest.getBookId())
        : UUID.randomUUID();
        
        ctx.userId = UUID.fromString(fileUploadRequest.getUserId());
        ctx.title  = fileUploadRequest.getTitle();
        ctx.originalFilename = sanitizeFilename(filePart.filename());
        ctx.rawFilePath  = String.format(rawFilePatternPath,  ctx.bookId, ctx.originalFilename);
        ctx.textFilePath = String.format(textFilePatternPath, ctx.bookId, ctx.bookId + ".txt");

        return getUserValidationClientImpl().validateUser(fileUploadRequest.getUserId())
        .filter(Boolean::booleanValue)
        .switchIfEmpty(Mono.error(new ResourceNotFoundException("User not found: " + fileUploadRequest.getUserId())))
        .then(createTempForUpload(ctx))                // ctx.tempRaw
        .flatMap(c -> writeUploadToTemp(filePart, c)) // write filePart -> tempRaw
        .flatMap(c -> storeRaw(c))                    // ctx.rawUri
        .flatMap(c -> extractTxtToTemp(mediaType, c)) // ctx.tempTxt
        .flatMap(c -> storeTxt(c))                    // ctx.textUri
        .flatMap(c -> computeShaAndLength(c))         // ctx.textSha256, ctx.textLength
        .flatMap(c -> insertBookRow(c))               // insert book row
        .map(saved -> saved.getId().toString())
        .timeout(Duration.ofSeconds(timeoutInSecs))
        .doOnError(e -> logger.error(
            "Upload failed for userId={} file={} bookId={}",
            fileUploadRequest.getUserId(),
            filePart.filename(),
            ctx.bookId,
            e))
        .onErrorMap(e -> new FileUploadException("Upload failed: " + e.getMessage()));
    }


    private static String sanitizeFilename(String name) {
        String base = name == null ? "upload" : name;
        // strip path, keep simple characters
        base = base.replace("\\", "/");
        base = base.substring(base.lastIndexOf('/') + 1);
        return base.replaceAll("[^a-zA-Z0-9._-]", "_");
    }

    private static String sha256(String text) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(text.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder(64);
            for (byte b : hash) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (Exception e) { throw new RuntimeException(e); }
    }


    private Mono<UploadContext> createTempForUpload(UploadContext ctx) {
    return Mono.fromCallable(() -> Files.createTempFile("upload-", "-" + ctx.originalFilename))
               .subscribeOn(Schedulers.boundedElastic())
               .map(p -> { ctx.tempRaw = p; return ctx; });
    }

    private Mono<UploadContext> writeUploadToTemp(FilePart filePart, UploadContext ctx) {
        return filePart.transferTo(ctx.tempRaw).thenReturn(ctx);
    }

    private Mono<UploadContext> storeRaw(UploadContext ctx) {
        return fileStorageService.save(ctx.tempRaw, ctx.rawFilePath)
        .doOnSuccess(uri -> logger.info("Stored RAW {} as {}", ctx.originalFilename, uri))
        .map(uri -> { ctx.rawUri = uri; return ctx; });
    }

    private Mono<UploadContext> extractTxtToTemp( MediaType mediaType, UploadContext ctx) {
        return textExtractionService.extractToTxt(ctx.tempRaw, mediaType)
        .map(p -> { ctx.tempTxt = p; return ctx; });
    }

    private Mono<UploadContext> storeTxt(UploadContext ctx) {
        return fileStorageService.save(ctx.tempTxt, ctx.textFilePath)
        .doOnSuccess(uri -> logger.info("Stored TXT for book {} as {}", ctx.bookId, uri))
        .map(uri -> { ctx.textUri = uri; return ctx; });
    }

    private Mono<UploadContext> computeShaAndLength(UploadContext ctx) {
        return Mono.fromCallable(() -> Files.readString(ctx.tempTxt, StandardCharsets.UTF_8))
                .subscribeOn(Schedulers.boundedElastic())
                .map(text -> {
                    ctx.textSha256 = sha256(text);
                    ctx.textLength = text.length();
                    return ctx;
                });
    }

    private Mono<Books> insertBookRow(UploadContext ctx) {
        Books books = new Books();
        books.markNew(true);
        books.setId(ctx.bookId);
        books.setUser_id(ctx.userId);
        books.setTitle(ctx.title);
        books.setRaw_uri(ctx.rawUri);
        books.setText_uri(ctx.textUri);
        books.setText_sha256(ctx.textSha256);
        books.setText_length(ctx.textLength);
        books.setStatus(BookStatus.TEXT_READY);
        books.setText_ready_at(LocalDateTime.now());
        books.setSegments_ready_at(null);
        return booksRepository.save(books);
    }


    // single, readable container for passing data through the reactive steps
    private static final class UploadContext {
        UUID bookId;
        UUID userId;
        String title;
        String originalFilename;

        String rawFilePath;
        String textFilePath;

        Path tempRaw;
        Path tempTxt;

        String rawUri;
        String textUri;

        String textSha256;
        Integer textLength;
    }

}
