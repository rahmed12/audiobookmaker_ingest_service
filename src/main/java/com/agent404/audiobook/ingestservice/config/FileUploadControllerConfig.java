package com.agent404.audiobook.ingestservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.agent404.audiobook.ingestservice.service.IFileStorageService;
import com.agent404.audiobook.ingestservice.service.IFileUploadService;
import com.agent404.audiobook.ingestservice.service.impl.FileUploadServiceImpl;
import com.agent404.audiobook.ingestservice.service.impl.LocalFileStorageServiceImpl;

import org.springframework.beans.factory.annotation.Value;

@Configuration
public class FileUploadControllerConfig {

    @Bean
    public IFileStorageService allocateFileStorageService(){
        return new LocalFileStorageServiceImpl();
    }

    @Bean
    public IFileUploadService allocateFileUploadService(
        IFileStorageService storageService,
        @Value("${app.ingestService.fileupload.timeout:30}") int timeoutInSeconds
    ) {
        return new FileUploadServiceImpl(storageService, timeoutInSeconds);

    }

}
