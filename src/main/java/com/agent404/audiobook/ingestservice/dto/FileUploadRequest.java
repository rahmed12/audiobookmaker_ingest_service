package com.agent404.audiobook.ingestservice.dto;

import lombok.Data;


@Data
public class FileUploadRequest {
    private String userId;
    private String fileName;
    private String title;
    private String bookId;
}
