package com.agent404.audiobook.ingestservice.service;

import org.springframework.http.codec.multipart.FilePart;

import reactor.core.publisher.Mono;
import java.nio.file.Path;

public interface IFileStorageService {
    Mono<Path> save(FilePart filePart, String fileName);
}
