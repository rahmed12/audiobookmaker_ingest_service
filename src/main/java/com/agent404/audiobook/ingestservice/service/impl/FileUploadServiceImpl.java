package com.agent404.audiobook.ingestservice.service.impl;



import org.slf4j.LoggerFactory;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

import org.slf4j.Logger;

import com.agent404.audiobook.ingestservice.dto.FileUploadRequest;
import com.agent404.audiobook.ingestservice.exception.FileUploadException;
import com.agent404.audiobook.ingestservice.exception.ResourceNotFoundException;
import com.agent404.audiobook.ingestservice.service.IFileStorageService;
import com.agent404.audiobook.ingestservice.service.IFileUploadService;
import com.agent404.audiobook.ingestservice.service.IUserValidationService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Getter
@RequiredArgsConstructor
public class FileUploadServiceImpl implements IFileUploadService{
    
    private static final Logger logger = LoggerFactory.getLogger(FileUploadServiceImpl.class);
    

    private final IFileStorageService fileStorageService;
    private final int timeoutInSecs;
    private final IUserValidationService userValidationServiceImpl;


    @Override
    public Mono<String> handleUpload(FilePart  filePart, 
                                     String username,
                                     FileUploadRequest fileUploadRequest
                                    ) {

        String userId = fileUploadRequest.getUserId();

        return getUserValidationServiceImpl().validateUser(userId)
            .filter(Boolean::booleanValue) // if false filter so Mono completes empty
            .switchIfEmpty(Mono.error(new ResourceNotFoundException("User Not Found when validating: " + userId)))
            .flatMap( __ -> {

                String fileName = userId + "_" + filePart.filename();

                return fileStorageService.save(filePart, fileName)
                    .doOnSuccess(path ->  logger.info("User '{}' uploaded file '{}'. UploadId:  {} (timeout {}s)", username, fileName, userId, timeoutInSecs))
                    // any execption in save() becomes FileUploadException and is handled globally
                    .onErrorMap(e -> new FileUploadException("Could not store file: " + e.getMessage()))
                    .thenReturn(userId);

            });
        

        



        // return Mono.fromCallable(() -> {
        //     String uploadId = java.util.UUID.randomUUID().toString();
        //     try {
        //         // fileStorageService.save(file, uploadId);
        //         fileStorageService.save(null, uploadId); // testing
        //     } catch (RuntimeException e) {
        //         logger.error("Failed to store file for user '{}': {}", username, e.getMessage(), e);
        //         throw e;
        //     }
        //     logger.info("User '{}' uploaded file '{}'. UploadId: {} (timeout {} ms)", username, file.getOriginalFilename(), uploadId, timeoutInSecs);
        //     return uploadId;
        // });
    
    }

}
