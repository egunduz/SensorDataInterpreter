package com.demo.SensorDataInterpreter.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/*
    Global Exception Handler for Sensor Data Interpreter Application.
    Responsibilities:
    Logging: Logs exceptions with their details.
    Caching: Tracks the count of each exception type using a ConcurrentHashMap.
    Custom Responses: Returns meaningful HTTP responses for specific exceptions
 */
@Getter
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private final Map<String, Integer> exceptionCache = new ConcurrentHashMap<>();

    @ExceptionHandler(AppException.class)
    public ResponseEntity<String> handleSensorDataException(AppException ex) {
        logException(ex);
        cacheException(ex.getErrorCode().getCode());
        return new ResponseEntity<>(ex.getErrorCode().getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        log.error("Unhandled exception: {}", ex.getMessage(), ex);
        return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.error("DTO Validation Error: {}", ex.getMessage(), ex);

        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    private void logException(AppException ex) {
        log.error("Error Code: {}, Message: {}", ex.getErrorCode().getCode(), ex.getMessage(), ex);
    }

    /**
     * Caches the exception count for a specific error code.
     * This method is used to track the frequency of exceptions
     * @param errorCode The error code to cache.
     */
    private void cacheException(String errorCode) {
        exceptionCache.merge(errorCode, 1, Integer::sum);
        log.info("Cached exception count for {}: {}", errorCode, exceptionCache.get(errorCode));
    }

}