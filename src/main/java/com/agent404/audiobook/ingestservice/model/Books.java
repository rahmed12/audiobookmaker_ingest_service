package com.agent404.audiobook.ingestservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import jakarta.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "books")
public class Books {

    @Id
    private UUID id;

    @Column("user_id")
    @NotBlank
    private UUID userId;

    @Column("title")
    private String title;

    @Column("original_filename")
    private String originalFilename;

    @Column("language_code")
    private String languageCode;

    @Column("priority_level")
    private String priorityLevel;

    @Column("upload_path")
    private String uploadPath;

    @Column("plain_text_path")
    private String plainTextPath;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("deleted_at")
    private LocalDateTime deletedAt;
}
