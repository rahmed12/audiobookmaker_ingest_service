package com.agent404.audiobook.ingestservice.service.impl;

import com.agent404.audiobook.ingestservice.service.IFileStorageService;

import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;

import org.slf4j.Logger;

public class LocalFileStorageServiceImpl implements IFileStorageService{

    private static final Logger logger = LoggerFactory.getLogger(LocalFileStorageServiceImpl.class);
    private static final String UPLOAD_DIR = "uploads";

    @Override
    public int save(MultipartFile file, String uploadId) {
        try {
            Path dirPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(dirPath)) {
                Files.createDirectories(dirPath);
            }
            Path filePath = dirPath.resolve(uploadId + "_" + file.getOriginalFilename());
            file.transferTo(filePath.toFile());
            logger.info("File saved to: {}", filePath);
            return 0;
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }
    }

}
