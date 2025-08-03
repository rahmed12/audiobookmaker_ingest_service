package com.agent404.audiobook.ingestservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sentence_entities")
public class SentenceEntities {

    @Id
    private Integer id;

    @Column("sentence_id")
    private UUID sentenceId;

    @Column("entity_text")
    private String entityText;

    @Column("entity_type")
    private String entityType;

    @Column("start_token")
    private Integer startToken;

    @Column("end_token")
    private Integer endToken;
}