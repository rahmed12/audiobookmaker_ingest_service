package com.agent404.audiobook.ingestservice.util;

import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.multipart.MultipartFile;

public class FileTypeValidator {
    public static boolean isValid(FilePart  file) {
        String type = file.headers().getContentType() != null ? file.headers().getContentType().toString() : null;
        return type != null && (type.equals("application/pdf") || type.equals("text/plain"));
    }
}
