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
@Table(name = "sentence_nlp_tokens")
public class SentenceNlpTokens {

    @Id
    private Integer id;

    @Column("sentence_id")
    private UUID sentenceId;

    @Column("token_order")
    private Integer tokenOrder;

    @Column("text")
    private String text;

    @Column("lemma")
    private String lemma;

    @Column("pos")
    private String pos;

    @Column("tag")
    private String tag;

    @Column("dep")
    private String dep;

    @Column("ent_type")
    private String entType;
}