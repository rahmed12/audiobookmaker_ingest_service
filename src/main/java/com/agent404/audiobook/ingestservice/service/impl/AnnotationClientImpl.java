package com.agent404.audiobook.ingestservice.service.impl;

import java.time.Duration;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import com.agent404.audiobook.ingestservice.dto.AnnotationRequest;
import com.agent404.audiobook.ingestservice.dto.AnnotationResponse;
import com.agent404.audiobook.ingestservice.service.IAnnotationClient;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class AnnotationClientImpl implements IAnnotationClient{
    
    private static final Logger logger = LoggerFactory.getLogger(FileUploadServiceImpl.class);

    private final WebClient annotationWebClient;
    
    @Override
    public Mono<AnnotationResponse> requestExtraction(UUID bookId) {
        
        return annotationWebClient.post()
            .uri("v1/extract")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(new AnnotationRequest(bookId))
            .exchangeToMono(resp -> {
                int code = resp.statusCode().value();
                if (resp.statusCode().is2xxSuccessful()) {      // 200 or 202 both OK
                return resp.bodyToMono(AnnotationResponse.class)
                    .doOnNext(body ->
                        logger.info("Annotator HTTP {} for book {} body={}", code, bookId, body)
                    );
                } else {
                // read error body for useful logs
                return resp.bodyToMono(String.class).defaultIfEmpty("")
                    .flatMap(err -> {
                        logger.warn("Annotator HTTP {} for book {} err={}", code, bookId, err);
                        
                        return Mono.error(new IllegalStateException("Annotator returned " + code));
                    });
                }
            })
            .timeout(Duration.ofSeconds(30))
            .doOnError(e -> logger.warn("Annotation trigger failed for {}: {}", bookId, e.toString()));
    }

    
}
