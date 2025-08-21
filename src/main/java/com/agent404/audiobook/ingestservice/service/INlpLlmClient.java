package com.agent404.audiobook.ingestservice.service;

import java.util.List;
import java.util.UUID;

import com.agent404.audiobook.ingestservice.model.Sentences;

import reactor.core.publisher.Mono;

public interface INlpLlmClient {
    Mono<List<Sentences>> sendChunkForClassification(List<String> chunk, UUID bookId, UUID userId);
}
