package com.agent404.audiobook.ingestservice.util;

import org.springframework.web.multipart.MultipartFile;

public class FileTypeValidator {
    public static boolean isValid(MultipartFile file) {
        String type = file.getContentType();
        return type != null && (type.equals("application/pdf") || type.equals("text/plain"));
    }
}
