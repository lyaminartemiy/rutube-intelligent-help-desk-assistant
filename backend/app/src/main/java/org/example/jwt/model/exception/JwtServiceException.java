package org.example.jwt.model.exception;

public class JwtServiceException extends RuntimeException {
    public JwtServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
