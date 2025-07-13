package com.agent404.audiobook.ingestservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileUploadResponse {

    private String uploadId;

    public FileUploadResponse(String uploadId){
        this.uploadId = uploadId;
    }

}
