package com.agent404.audiobook.ingestservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import com.agent404.audiobook.ingestservice.service.IFileUploadService;
import com.agent404.audiobook.ingestservice.dto.FileUploadResponse;
import com.agent404.audiobook.ingestservice.util.FileTypeValidator;

import java.security.Principal;

@RestController
@RequestMapping("/ingest")
public class FileUploadController {

    private final IFileUploadService fileUploadService;


    public FileUploadController(IFileUploadService fileUploadService){
        this.fileUploadService = fileUploadService;
    }

    @PostMapping("/upload")
    public Mono<ResponseEntity<FileUploadResponse>> uploadFile(@RequestParam("file") MultipartFile file, Principal principal) {
        if (!FileTypeValidator.isValid(file)) {
            return Mono.just(ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).build());
        }

        return fileUploadService.handleUpload(file, principal.getName())
            .map(uploadId -> ResponseEntity.accepted().body(new FileUploadResponse(uploadId)))
            .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()));


    }
}
