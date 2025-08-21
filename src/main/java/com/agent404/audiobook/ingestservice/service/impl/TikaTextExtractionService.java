package com.agent404.audiobook.ingestservice.service.impl;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.tika.metadata.HttpHeaders;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.http.MediaType;

import com.agent404.audiobook.ingestservice.service.ITextExtractionService;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class TikaTextExtractionService implements ITextExtractionService{

    private final AutoDetectParser parser = new AutoDetectParser();

    @Override
        public Mono<Path> extractToTxt(Path sourceFile, MediaType contentType) {
        return Mono.fromCallable(() -> {
            Path out = Files.createTempFile("extract-", ".txt");
            try (InputStream in = Files.newInputStream(sourceFile);
                 BufferedWriter writer = Files.newBufferedWriter(out, StandardCharsets.UTF_8)) {

                BodyContentHandler handler = new BodyContentHandler(-1);
                Metadata md = new Metadata();
                if (contentType != null) {
                    md.set(HttpHeaders.CONTENT_TYPE, contentType.toString());
                }
                ParseContext ctx = new ParseContext();
                parser.parse(in, handler, md, ctx);

                String text = handler.toString().replace("\r\n", "\n");
                writer.write(text);
            } catch (Exception e) {
                try { Files.deleteIfExists(out); } catch (IOException ignore) {}
                throw new RuntimeException("Text extraction failed: " + e.getMessage(), e);
            }
            return out;
        }).subscribeOn(Schedulers.boundedElastic());
    }
}

