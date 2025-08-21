package com.agent404.audiobook.ingestservice.service;

import org.springframework.http.codec.multipart.FilePart;

import reactor.core.publisher.Mono;
import java.nio.file.Path;

public interface IFileStorageService {
    
    // Store from FilePart (raw upload)
    Mono<String> save(FilePart filePart, String objectKey); // returns object key or URI

    // Store from a local temp file (extracted text, etc.)
    Mono<String> save(Path localPath, String objectKey);

    // Optional: delete temp/local backing if you use a local bucket
    Mono<Boolean> deleteTemp(Path path);
}
