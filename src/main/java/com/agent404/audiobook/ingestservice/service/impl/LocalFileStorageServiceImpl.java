package com.agent404.audiobook.ingestservice.service.impl;

import com.agent404.audiobook.ingestservice.service.IFileStorageService;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import org.slf4j.LoggerFactory;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.multipart.MultipartFile;
import org.stringtemplate.v4.compiler.CodeGenerator.region_return;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;

import org.slf4j.Logger;

public class LocalFileStorageServiceImpl implements IFileStorageService{

    // TODO: make UPLOAD_DIR into a config for different env: prod, non prod, cloud etc...
    private static final Logger logger = LoggerFactory.getLogger(LocalFileStorageServiceImpl.class);
    private static final String UPLOAD_DIR = "/workspaces/audiobook-ai-docker-image/myProjects/tmp/uploads";

    @Override
    public Mono<Path> save(FilePart filePart, String fileName) {

        Path targPath = Paths.get(UPLOAD_DIR).resolve(fileName);

        // Using defer for blocking i/o calls
        return Mono.defer(() -> {
            try { 
                Path dir = targPath.getParent();
                if (!Files.exists(dir)){
                    Files.createDirectories(dir);
                }
            return Mono.just(targPath);
            } catch (IOException e){
                // Bubble the error into the reactive pipeline
                return Mono.error(e);
            }
            
        })
        .subscribeOn(Schedulers.boundedElastic())
        .flatMap(path -> filePart.transferTo(path).thenReturn(path))
        .doOnSuccess(path -> logger.info("File saved to: {}", path))
        .doOnError(e -> logger.error("File save failed: {}", e.getMessage(), e));
        
    }

        

}
