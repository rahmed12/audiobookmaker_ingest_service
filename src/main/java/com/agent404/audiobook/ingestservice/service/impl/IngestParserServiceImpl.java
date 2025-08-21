package com.agent404.audiobook.ingestservice.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.agent404.audiobook.ingestservice.repository.SentenceRepository;
import com.agent404.audiobook.ingestservice.service.IIngestParserService;
import com.agent404.audiobook.ingestservice.service.INlpLlmClient;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class IngestParserServiceImpl implements IIngestParserService{

    private static final Logger log = LoggerFactory.getLogger(IngestParserServiceImpl.class);

    private  final SentenceRepository sentenceRepository;
    private  final INlpLlmClient nlpLlmClient;

    @Override
    public Mono<Void> processBook(Path plainTextPath, UUID bookId, UUID userId) {
        return Mono.fromCallable(() -> chunkText(plainTextPath))
        .flatMap(chunks -> dispatchChunks(chunks, bookId, userId))
        .doOnSuccess(__ -> log.info("Ingest complete for book {}", bookId))
        ;

        
    }

    private List<List<String>> chunkText(Path path) throws IOException {
        List<String> lines = Files.readAllLines(path).stream()
        .map(String::trim)
        .filter(StringUtils::hasText)
        .toList();

        int chunkSize = 160;
        int coreSize = 100;
        int buffer = (chunkSize - coreSize) / 2;

        List<List<String>> chunks = new ArrayList<>();
        for (int i = 0; i < lines.size(); i += coreSize) {
            int start = Math.max(i - buffer, 0);
            int end = Math.min(i + coreSize + buffer, lines.size());
            chunks.add(lines.subList(start, end));
        }
        return chunks;

    }

    private Mono<Void> dispatchChunks(List<List<String>> chunks, UUID bookId, UUID userId) {

        return Flux.fromIterable(chunks)
            .concatMap(chunk->
                nlpLlmClient.sendChunkForClassification(chunk, bookId, userId)
                    .flatMapMany(sentences -> sentenceRepository.saveAll(Flux.fromIterable(sentences)))
            )
            .then();
    }

}
