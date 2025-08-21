package com.agent404.audiobook.ingestservice.service;

import reactor.core.publisher.Mono;

public interface IUserValidationClient {
    Mono<Boolean>  validateUser(String userId);
}
