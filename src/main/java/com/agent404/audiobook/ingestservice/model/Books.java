package com.agent404.audiobook.ingestservice.model;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;



@Data
@Table(name = "books")
public class Books implements Persistable<UUID>{

    @Id
    private UUID id;
    private UUID user_id;
    private String title;
    private String raw_uri;
    private String text_uri;
    private String text_sha256;
    private Integer text_length;
    private BookStatus status;
    private LocalDateTime text_ready_at;
    private LocalDateTime segments_ready_at;
    @CreatedDate
    private LocalDateTime created_at;
    @LastModifiedDate
    private LocalDateTime updated_at;

    @Transient  // don't map this field to a column
    private boolean isNew;                 // not a column in table

    @Override public boolean isNew() { return this.isNew || id == null; }
    public void markNew(boolean isNew) { this.isNew = isNew; }
}
