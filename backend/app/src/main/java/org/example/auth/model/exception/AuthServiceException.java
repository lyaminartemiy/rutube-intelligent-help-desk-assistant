package org.example.auth.model.exception;

public class AuthServiceException extends RuntimeException {
    public AuthServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
