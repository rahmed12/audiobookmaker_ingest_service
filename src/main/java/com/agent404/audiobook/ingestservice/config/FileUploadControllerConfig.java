package com.agent404.audiobook.ingestservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import com.agent404.audiobook.ingestservice.repository.BooksRepository;
import com.agent404.audiobook.ingestservice.service.IFileStorageService;
import com.agent404.audiobook.ingestservice.service.IFileUploadService;
import com.agent404.audiobook.ingestservice.service.ITextExtractionService;
import com.agent404.audiobook.ingestservice.service.IUserValidationClient;
import com.agent404.audiobook.ingestservice.service.impl.FileUploadServiceImpl;
import com.agent404.audiobook.ingestservice.service.impl.LocalFileStorageServiceImpl;
import com.agent404.audiobook.ingestservice.service.impl.TikaTextExtractionService;
import com.agent404.audiobook.ingestservice.service.impl.UserValidationClientImpl;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

@Configuration
public class FileUploadControllerConfig {

    @Bean
    public IFileStorageService allocateFileStorageService(
        @Value("${app.ingestService.storage.paths.root:/workspaces/audiobook-ai-docker-image/myProjects/tmp/uploads}") String storageRootDir 
    ){
        return new LocalFileStorageServiceImpl(storageRootDir);
    }

    @Bean
    public ITextExtractionService allocateTextExtractionService(){ return new TikaTextExtractionService(); }


   @Bean
    public IUserValidationClient allocateUserValidationService(
        @Qualifier("userWebClient") WebClient webClientUser
    ) {
        return new UserValidationClientImpl(webClientUser);

    }

    @Bean
    public IFileUploadService allocateFileUploadService(
        IFileStorageService storageService,
        @Value("${app.ingestService.fileupload.timeout:30}") int timeoutInSeconds,
        IUserValidationClient userValidationServiceImpl,
        @Value("${app.ingestService.fileupload.paths.rawPattern:books/%s/raw/%s}") String rawFilePatternPath,
        @Value("${app.ingestService.fileupload.paths.textPattern:books/%s/text/%s}") String textFilePatternPath,
        BooksRepository bookRepository,
        ITextExtractionService textExtractionService 
    ) {
        return new FileUploadServiceImpl(storageService, timeoutInSeconds, userValidationServiceImpl, rawFilePatternPath, textFilePatternPath,
        bookRepository, textExtractionService);

    }

    

}
