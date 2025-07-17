package com.agent404.audiobook.ingestservice.exception;


public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String msg) {
        super(msg);
    }
    
}
