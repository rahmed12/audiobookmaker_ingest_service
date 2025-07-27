package com.agent404.audiobook.ingestservice.service;

import reactor.core.publisher.Mono;

public interface IUserValidationService {
    Mono<Boolean>  validateUser(String userId);
}
