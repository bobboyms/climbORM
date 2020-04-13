package br.com.climb.exception;

import java.io.IOException;

public class CacheSystemException extends IOException {
    public CacheSystemException(String message) {
        super(message);
    }
}
