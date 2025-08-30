package com.agent404.audiobook.ingestservice.dto;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AnnotationRequest {
    @JsonProperty("book_id") // match FastAPI’s snake_case
    private UUID bookId;
}
