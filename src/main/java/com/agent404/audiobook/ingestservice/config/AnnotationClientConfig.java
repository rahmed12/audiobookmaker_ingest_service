package com.agent404.audiobook.ingestservice.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import com.agent404.audiobook.ingestservice.service.IAnnotationClient;
import com.agent404.audiobook.ingestservice.service.impl.AnnotationClientImpl;


@Configuration
public class AnnotationClientConfig {

    @Bean
    public IAnnotationClient allocateAnnotationClient (
        @Qualifier("AnnotationClient") WebClient webClientAnnotation
    ){
        return new AnnotationClientImpl(webClientAnnotation);
    }

}
