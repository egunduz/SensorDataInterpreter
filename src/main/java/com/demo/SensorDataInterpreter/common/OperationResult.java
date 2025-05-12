package com.demo.SensorDataInterpreter.common;

import lombok.Builder;
import lombok.Data;

/**
 * Helper class to encapsulate the result of an operation.
 * It contains a success flag and an optional message.
 */
@Data
@Builder
public class OperationResult {
    private final boolean success;
    private final String message;

    public OperationResult(boolean success) {
        this.success = success;
        this.message = null;
    }

    public OperationResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    // Factory method for creating a success operation result
    public static OperationResult success() {
        return new OperationResult(true);
    }

    // Factory method for creating a failed operation result with a message
    public static OperationResult failure(String message) {
        return new OperationResult(false, message);
    }
}
