package com.agent404.audiobook.ingestservice.service.impl;



import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;

import com.agent404.audiobook.ingestservice.service.IFileStorageService;
import com.agent404.audiobook.ingestservice.service.IFileUploadService;

import lombok.Getter;
import reactor.core.publisher.Mono;

@Getter
public class FileUploadServiceImpl implements IFileUploadService{
    
    private static final Logger logger = LoggerFactory.getLogger(FileUploadServiceImpl.class);
    

    private final IFileStorageService fileStorageService;
    private final int timeoutInSecs;

    public FileUploadServiceImpl(IFileStorageService fileStorageService, int timeoutInSecs){
        this.fileStorageService = fileStorageService;
        this.timeoutInSecs = timeoutInSecs;
    }

    @Override
    public Mono<String> handleUpload(MultipartFile file, String username){

        return Mono.fromCallable(() -> {
            String uploadId = java.util.UUID.randomUUID().toString();
            try {
                fileStorageService.save(file, uploadId);
            } catch (RuntimeException e) {
                logger.error("Failed to store file for user '{}': {}", username, e.getMessage(), e);
                throw e;
            }
            logger.info("User '{}' uploaded file '{}'. UploadId: {} (timeout {} ms)", username, file.getOriginalFilename(), uploadId, timeoutInSecs);
            return uploadId;
        });
    
    }

}
