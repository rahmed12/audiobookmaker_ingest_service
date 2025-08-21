package com.agent404.audiobook.ingestservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    @Bean(name = "userWebClient")
    public WebClient userWebClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder
            .baseUrl("http://localhost:8080")
            .build();
    }

    @Bean(name = "NlpLlmWebClient")
    public WebClient LlmWebClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder
            .baseUrl("http://localhost:8082")
            .build();
    }
}
