package com.agent404.audiobook.ingestservice.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;


import com.agent404.audiobook.ingestservice.service.INlpLlmClient;

import com.agent404.audiobook.ingestservice.service.impl.NlpLlmClientImpl;


@Configuration
public class NlpLlmClientConfig {

    @Bean
    public INlpLlmClient allocateNlpLlmClient(
        @Qualifier("NlpLlmWebClient") WebClient webClientNlpLlm
    ){
        return new NlpLlmClientImpl(webClientNlpLlm);
    }


}
