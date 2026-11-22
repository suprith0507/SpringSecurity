package com.example.SpringSecurity.dtos;


import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponseDTO {

    /**
     * Exception occurrence time
     */
    private LocalDateTime timestamp;

    /**
     * HTTP Status Code
     * Example:
     * 400
     * 404
     * 409
     * 500
     */
    private int statusCode;

    /**
     * HTTP Status Text
     * Example:
     * Bad Request
     * Not Found
     * Conflict
     * Internal Server Error
     */
    private String error;

    /**
     * Actual Error Message
     */
    private String message;

    /**
     * API Endpoint
     * Example:
     * /customers/100
     */
    private String path;

    /**
     * Validation Errors
     *
     * Only populated when
     * MethodArgumentNotValidException occurs.
     */
    private Map<String, String> validationErrors;
}
