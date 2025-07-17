package com.agent404.audiobook.ingestservice.service;

import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.multipart.MultipartFile;

import reactor.core.publisher.Mono;

public interface IFileUploadService {

    Mono<String> handleUpload(FilePart  file, String username);

}
