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
@Table(name = "ingest_checkpoints")
public class IngestCheckpoints {

    @Id
    private Integer id;

    @Column("book_id")
    private UUID bookId;

    @Column("user_id")
    private UUID userId;

    @Column("last_sentence_index")
    private Integer lastSentenceIndex;

    @Column("total_sentences")
    private Integer totalSentences;

    @Column("next_batch_start")
    private Integer nextBatchStart;

    @Column("status")
    private String status;

    @Column("message")
    private String message;

    @Column("updated_at")
    private LocalDateTime updatedAt;

    @Column("created_at")
    private LocalDateTime createdAt;
}