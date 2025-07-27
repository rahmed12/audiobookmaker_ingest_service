package com.agent404.audiobook.ingestservice.service;

import org.springframework.http.codec.multipart.FilePart;

import com.agent404.audiobook.ingestservice.dto.FileUploadRequest;

import reactor.core.publisher.Mono;

public interface IFileUploadService {

    Mono<String> handleUpload(FilePart  file, String username, FileUploadRequest fileUploadRequest);

}
