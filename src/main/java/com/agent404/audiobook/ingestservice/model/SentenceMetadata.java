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
@Table(name = "sentence_metadata")
public class SentenceMetadata {

    @Id
    @Column("sentence_id")
    private UUID sentenceId;

    @Column("type")
    private String type;

    @Column("character")
    private String character;

    @Column("gender")
    private String gender;

    @Column("age_group")
    private String ageGroup;

    @Column("emotion")
    private String emotion;

    @Column("confidence")
    private Float confidence;

    @Column("perspective")
    private String perspective;
}