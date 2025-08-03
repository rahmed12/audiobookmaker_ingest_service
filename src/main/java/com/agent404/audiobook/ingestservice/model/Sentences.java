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
@Table(name = "sentences")
public class Sentences {

    @Id
    private UUID id;

    @Column("book_id")
    private UUID bookId;

    @Column("sentence_order")
    private Integer sentenceOrder;

    @Column("text")
    private String text;

    @Column("embedding")
    private String embedding; // Assuming vector(384) can be represented as a String or byte array

    @Column("is_orphaned")
    private Boolean isOrphaned;

    @Column("revision_of")
    private UUID revisionOf;

    @Column("created_at")
    private LocalDateTime createdAt;
}