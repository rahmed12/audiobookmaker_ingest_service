package com.agent404.audiobook.ingestservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.agent404.audiobook.ingestservice.repository.SentenceRepository;
import com.agent404.audiobook.ingestservice.service.IIngestParserService;
import com.agent404.audiobook.ingestservice.service.INlpLlmClient;
import com.agent404.audiobook.ingestservice.service.impl.IngestParserServiceImpl;

@Configuration
public class IngestServiceConfig {

    @Bean
    public IIngestParserService ingestParserService (
        SentenceRepository sentenceRepository,
        INlpLlmClient nlpLlmClient
    ) {
            return new IngestParserServiceImpl(sentenceRepository, nlpLlmClient);
    }

}
