package com.example.SpringSecurity.advices;



import com.example.SpringSecurity.dtos.ErrorResponseDTO;
import com.example.SpringSecurity.exceptions.CustomerAlreadyExistsException;
import com.example.SpringSecurity.exceptions.CustomerNotFoundException;
import com.example.SpringSecurity.modelbeans.APIResponse;


import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * ============================================================
     * INPUT VALIDATION ERRORS
     * ============================================================
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<APIResponse<Object>> handleValidationException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        Map<String, String> validationErrors = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error -> validationErrors.put(
                        error.getField(),
                        error.getDefaultMessage()));

        ErrorResponseDTO errorResponse = ErrorResponseDTO.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message("Input Validation Failed")
                .path(request.getRequestURI())
                .validationErrors(validationErrors)
                .build();

        APIResponse<Object> apiResponse = APIResponse.builder()
                .data(null)
                .error(errorResponse)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(apiResponse);
    }

    /**
     * ============================================================
     * CUSTOMER NOT FOUND
     * ============================================================
     */
    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<APIResponse<Object>> handleCustomerNotFoundException(
            CustomerNotFoundException ex,
            HttpServletRequest request) {

        ErrorResponseDTO errorResponse = ErrorResponseDTO.builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        APIResponse<Object> apiResponse = APIResponse.builder()
                .data(null)
                .error(errorResponse)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(apiResponse);
    }

    /**
     * ============================================================
     * CUSTOMER ALREADY EXISTS
     * ============================================================
     */
    @ExceptionHandler(CustomerAlreadyExistsException.class)
    public ResponseEntity<APIResponse<Object>> handleCustomerAlreadyExistsException(
            CustomerAlreadyExistsException ex,
            HttpServletRequest request) {

        ErrorResponseDTO errorResponse = ErrorResponseDTO.builder()
                .statusCode(HttpStatus.CONFLICT.value())
                .error(HttpStatus.CONFLICT.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        APIResponse<Object> apiResponse = APIResponse.builder()
                .data(null)
                .error(errorResponse)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(apiResponse);
    }

    /**
     * ============================================================
     * DATABASE CONSTRAINT VIOLATIONS
     * ============================================================
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<APIResponse<Object>> handleDataIntegrityViolationException(
            DataIntegrityViolationException ex,
            HttpServletRequest request) {

        ErrorResponseDTO errorResponse = ErrorResponseDTO.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .error("Database Error")
                .message("Database Constraint Violation")
                .path(request.getRequestURI())
                .build();

        APIResponse<Object> apiResponse = APIResponse.builder()
                .data(null)
                .error(errorResponse)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(apiResponse);
    }

    /**
     * ============================================================
     * ILLEGAL ARGUMENT EXCEPTION
     * ============================================================
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<APIResponse<Object>> handleIllegalArgumentException(
            IllegalArgumentException ex,
            HttpServletRequest request) {

        ErrorResponseDTO errorResponse = ErrorResponseDTO.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        APIResponse<Object> apiResponse = APIResponse.builder()
                .data(null)
                .error(errorResponse)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(apiResponse);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<APIResponse<Object>> handleAuthenticationException(
            AuthenticationException ex,
            HttpServletRequest request) {
        System.out.println("AuthenticationException caught");
        ErrorResponseDTO errorResponse = ErrorResponseDTO.builder()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .error(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                .message(ex.getLocalizedMessage())
                .path(request.getRequestURI())
                .build();

        APIResponse<Object> apiResponse = APIResponse.builder()
                .data(null)
                .error(errorResponse)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(apiResponse);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<APIResponse<Object>> handleJWTException(
            Exception ex,
            HttpServletRequest request) {

        ErrorResponseDTO errorResponse = ErrorResponseDTO.builder()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .error(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                .message(ex.getLocalizedMessage())
                .path(request.getRequestURI())
                .build();

        APIResponse<Object> apiResponse = APIResponse.builder()
                .data(null)
                .error(errorResponse)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(apiResponse);
    }

    /**
     * ============================================================
     * ALL UNHANDLED SYSTEM EXCEPTIONS
     * ============================================================
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIResponse<Object>> handleException(
            Exception ex,
            HttpServletRequest request) {

        ErrorResponseDTO errorResponse = ErrorResponseDTO.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .message("Unexpected Error Occurred")
                .path(request.getRequestURI())
                .build();
        System.out.println("AuthenticationException caught");

        APIResponse<Object> apiResponse = APIResponse.builder()
                .data(null)
                .error(errorResponse)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(apiResponse);
    }
}
