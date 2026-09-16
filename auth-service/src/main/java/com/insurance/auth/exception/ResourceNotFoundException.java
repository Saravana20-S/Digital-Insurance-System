package com.insurance.auth.exception;

/**
 * Exception thrown when a requested resource does not exist.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}