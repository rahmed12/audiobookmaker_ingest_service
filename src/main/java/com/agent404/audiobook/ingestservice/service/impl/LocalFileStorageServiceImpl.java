package com.agent404.audiobook.ingestservice.service.impl;

import com.agent404.audiobook.ingestservice.service.IFileStorageService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import org.slf4j.LoggerFactory;
import org.springframework.http.codec.multipart.FilePart;


import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.slf4j.Logger;

@Getter
@RequiredArgsConstructor
public class LocalFileStorageServiceImpl implements IFileStorageService{

    // TODO: make UPLOAD_DIR into a config for different env: prod, non prod, cloud etc...
    private static final Logger logger = LoggerFactory.getLogger(LocalFileStorageServiceImpl.class);
    
    private final String storageRootDir;


    private Path ensureParent(String key) {
        try {
        
            // Root: where you keep files
            Path root = Path.of(getStorageRootDir()).toAbsolutePath().normalize();

            // Normalize the key, strip leading slahes, unify separator
            String cleanKey = key.replace("\\", "/").replaceFirst("^/+", "");
            Path target = root.resolve(cleanKey).normalize();

            // Security: refure to write outside the rood
            if (!target.startsWith(root)) {
                throw new SecurityException("Refusing to write outside storage root: " + key);
            }

            // Ensure parent dir exists (handle null when key has no parent)
            Path parent = target.getParent();
            if (parent != null) {
                Files.createDirectories(parent); // idempotent
            } else {
                Files.createDirectories(root);   // at least ensure root exists
            }

            return target;
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to prepare storage path for key: " + key, e);
        }
    }

    @Override
    public Mono<String> save(FilePart filePart, String objectKey) {
        return Mono.fromCallable(() -> ensureParent(objectKey))
                   .subscribeOn(Schedulers.boundedElastic())
                   .flatMap(target -> filePart.transferTo(target)
                                              .thenReturn(target))
                   .map(p -> p.toUri().toString());
    }

    @Override
    public Mono<String> save(Path localPath, String objectKey) {
        return Mono.fromCallable(() -> {
                    Path target = ensureParent(objectKey);
                    Files.copy(localPath, target, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                    return target.toUri().toString();
                })
                .subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono<Boolean> deleteTemp( Path path) {
        return Mono.fromCallable(() -> {
            if (path == null) return false;
            
            Path p = path.toAbsolutePath().normalize();

            if (!Files.exists(p)) return false;    // already gone
            try {
                return Files.deleteIfExists(p);    // delete file only
                
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }).subscribeOn(Schedulers.boundedElastic());
    }

}
