package com.agent404.audiobook.ingestservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import com.agent404.audiobook.ingestservice.service.IFileStorageService;
import com.agent404.audiobook.ingestservice.service.IFileUploadService;
import com.agent404.audiobook.ingestservice.service.IUserValidationService;
import com.agent404.audiobook.ingestservice.service.impl.FileUploadServiceImpl;
import com.agent404.audiobook.ingestservice.service.impl.LocalFileStorageServiceImpl;
import com.agent404.audiobook.ingestservice.service.impl.UserValidationServiceImpl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

@Configuration
public class FileUploadControllerConfig {

    @Bean
    public IFileStorageService allocateFileStorageService(){
        return new LocalFileStorageServiceImpl();
    }

   @Bean
    public IUserValidationService allocateUserValidationService(
        @Qualifier("userServiceWebClient") WebClient webClientUserService
    ) {
        return new UserValidationServiceImpl(webClientUserService);

    }

    @Bean
    public IFileUploadService allocateFileUploadService(
        IFileStorageService storageService,
        @Value("${app.ingestService.fileupload.timeout:30}") int timeoutInSeconds,
        IUserValidationService userValidationServiceImpl
    ) {
        return new FileUploadServiceImpl(storageService, timeoutInSeconds, userValidationServiceImpl);

    }

    

}
