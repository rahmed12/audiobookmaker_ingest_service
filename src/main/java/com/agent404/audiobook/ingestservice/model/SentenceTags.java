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
@Table(name = "sentence_tags")
public class SentenceTags {

    @Id
    private Integer id;

    @Column("sentence_id")
    private UUID sentenceId;

    @Column("tag")
    private String tag;

    @Column("confidence")
    private Float confidence;

    @Column("source")
    private String source;
}