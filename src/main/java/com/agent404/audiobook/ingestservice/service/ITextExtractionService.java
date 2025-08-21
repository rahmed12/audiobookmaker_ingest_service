package com.agent404.audiobook.ingestservice.service;

import org.springframework.http.MediaType;

import reactor.core.publisher.Mono;
import java.nio.file.Path;

public interface ITextExtractionService {
    /**
     * Extracts plain UTF-8 text to a temp .txt file and returns its Path.
     * Caller is responsible for deleting the returned temp file.
     */
    Mono<Path> extractToTxt(Path sourceFile, MediaType contentType);

}
