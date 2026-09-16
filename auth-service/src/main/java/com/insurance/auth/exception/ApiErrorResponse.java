package com.insurance.auth.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Standard API error response.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiErrorResponse {

    private LocalDateTime timestamp;

    private int status;

    private String error;

    private String message;

    private String path;

    private Map<String, String> validationErrors;
}