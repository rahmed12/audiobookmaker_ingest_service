package com.agent404.audiobook.ingestservice.service;

import org.springframework.web.multipart.MultipartFile;

import reactor.core.publisher.Mono;

public interface IFileUploadService {

    Mono<String> handleUpload(MultipartFile file, String username);

}
