package com.agent404.audiobook.ingestservice.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.web.reactive.function.client.WebClient;

import com.agent404.audiobook.ingestservice.model.Sentences;
import com.agent404.audiobook.ingestservice.service.INlpLlmClient;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class NlpLlmClientImpl implements INlpLlmClient{

    private final WebClient nlpLlmWebClient;

    @Override
    public Mono<List<Sentences>> sendChunkForClassification(List<String> chunk, UUID bookId, UUID userId) {

    List<Sentences> fakeResults = new ArrayList<>();
    int sentenceIndex = 0;

    for (String line : chunk) {
        Sentences s = new Sentences();
        s.setId(UUID.randomUUID());
        s.setBookId(bookId);
        s.setSentenceOrder(sentenceIndex++);
        s.setText(line);
        s.setCreatedAt(java.time.LocalDateTime.now());

        // fake type (alternate between narration/dialogue/internal)
        // String[] types = {"narration", "dialogue", "internal"};
        // s.setType(types[sentenceIndex % types.length]);

        fakeResults.add(s);
    }

    return Mono.just(fakeResults);


        // return nlpLlmWebClient.get()
        //     .uri("v1/api/nlpllm/classify")
        //     .bodyValue(new ClassificationRequest(chunk, bookId, userId))
        //     .retrieve()
        //     .bodyToFlux(Sentences.class)
        //     .collectList();
    }



}
