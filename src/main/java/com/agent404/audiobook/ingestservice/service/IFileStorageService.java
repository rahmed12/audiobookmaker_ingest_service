package com.agent404.audiobook.ingestservice.service;

import org.springframework.web.multipart.MultipartFile;

public interface IFileStorageService {
    int save(MultipartFile file, String uploadId);
}
