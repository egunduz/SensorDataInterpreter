package com.demo.SensorDataInterpreter.exception;

import lombok.Getter;

/**
 * Custom exception class for handling sensor data related errors.
 * This class extends RuntimeException and includes an error code for better error categorization.
 */
@Getter
public class AppException extends RuntimeException {
    private final ErrorCode errorCode;

    public AppException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public AppException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.errorCode = errorCode;
    }
}