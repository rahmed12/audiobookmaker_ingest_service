package com.agent404.audiobook.ingestservice.service;

import java.nio.file.Path;
import java.util.UUID;

import reactor.core.publisher.Mono;

public interface IIngestParserService {
    Mono<Void> processBook(Path plainTextPath, UUID bookId, UUID userUuid);
}
