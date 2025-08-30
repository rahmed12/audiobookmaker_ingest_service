package com.agent404.audiobook.ingestservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class AnnotationResponse {
    @JsonProperty("book_id") // match FastAPI’s snake_case
    private String bookId;
    private String engine;
}
