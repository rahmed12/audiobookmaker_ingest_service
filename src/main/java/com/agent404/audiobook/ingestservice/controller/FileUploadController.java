package com.agent404.audiobook.ingestservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;

import reactor.core.publisher.Mono;

import com.agent404.audiobook.ingestservice.service.IFileUploadService;
import com.agent404.audiobook.ingestservice.dto.FileUploadResponse;
import com.agent404.audiobook.ingestservice.exception.FileUploadException;
import com.agent404.audiobook.ingestservice.util.FileTypeValidator;

import java.security.Principal;

@RestController
@RequestMapping("/ingest")
public class FileUploadController {

    private final IFileUploadService fileUploadService;


    public FileUploadController(IFileUploadService fileUploadService){
        this.fileUploadService = fileUploadService;
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<FileUploadResponse> uploadFile(
        @RequestPart("file") FilePart filePart,
        Principal principal) {

        if (!FileTypeValidator.isValid(filePart)) {
            throw new FileUploadException("Unsupported media type: " +
            filePart.headers().getContentType());
        }

        // TODO: remember to add security back to make principal work
        String uploader = (principal != null) ? principal.getName() : "anonymous";

        return fileUploadService.handleUpload(filePart, uploader)
            .map(uploadId -> new FileUploadResponse(uploadId));
    }
}
