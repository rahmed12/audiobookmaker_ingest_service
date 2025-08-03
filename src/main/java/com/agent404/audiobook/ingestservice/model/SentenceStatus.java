package com.agent404.audiobook.ingestservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sentence_status")
public class SentenceStatus {

    @Id
    @Column("sentence_id")
    private UUID sentenceId;

    @Column("classification_status")
    private String classificationStatus;

    @Column("tts_status")
    private String ttsStatus;

    @Column("audio_path")
    private String audioPath;

    @Column("audio_ready")
    private Boolean audioReady;

    @Column("llm_dispatched")
    private Boolean llmDispatched;

    @Column("classification_dispatched_at")
    private LocalDateTime classificationDispatchedAt;
}