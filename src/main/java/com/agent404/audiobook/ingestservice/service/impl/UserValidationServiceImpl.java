package com.agent404.audiobook.ingestservice.service.impl;
import org.springframework.web.reactive.function.client.WebClient;

import com.agent404.audiobook.ingestservice.service.IUserValidationService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;


@RequiredArgsConstructor
public class UserValidationServiceImpl implements IUserValidationService{

    private final WebClient userServiceWebClient;

    
    public Mono<Boolean> validateUser(String userId){

        return userServiceWebClient.get()
            .uri("v1/api/users/{userId}/validate", userId)
            .retrieve()
            .bodyToMono(Boolean.class);
        
    }

}
