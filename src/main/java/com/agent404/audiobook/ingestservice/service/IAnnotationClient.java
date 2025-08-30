package com.agent404.audiobook.ingestservice.service;

import java.util.UUID;

import com.agent404.audiobook.ingestservice.dto.AnnotationResponse;

import reactor.core.publisher.Mono;

public interface IAnnotationClient {
    Mono<AnnotationResponse> requestExtraction(UUID bookId);

}
