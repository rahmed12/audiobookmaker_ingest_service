package com.agent404.audiobook.ingestservice.service.impl;



import org.slf4j.LoggerFactory;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;

import com.agent404.audiobook.ingestservice.exception.FileUploadException;
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
    public Mono<String> handleUpload(FilePart  filePart, String username){

        String uploadId = java.util.UUID.randomUUID().toString();
        String fileName = uploadId + "_" + filePart.filename();

        return fileStorageService.save(filePart, fileName)
            .doOnSuccess(path ->  logger.info("User '{}' uploaded file '{}'. UploadId:  {} (timeout {}s)", username, fileName, uploadId, timeoutInSecs))
            // any execption in save() becomes FileUploadException and is handled globally
            .onErrorMap(e -> new FileUploadException("Could not store file: " + e.getMessage()))
            .thenReturn(uploadId);



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
