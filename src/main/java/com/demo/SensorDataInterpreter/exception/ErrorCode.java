package com.demo.SensorDataInterpreter.exception;

import lombok.Getter;

/**
 * Error codes constants
 */
@Getter
public enum ErrorCode {
    SENSOR_DATA_SERIALIZATION_ERROR("SENSOR_DATA_SERIALIZATION_ERROR", "Failed to serialize sensor data"),
    SENSOR_DATA_DESERIALIZATION_ERROR("SENSOR_DATA_DESERIALIZATION_ERROR", "Failed to deserialize sensor data"),
    SENSOR_DATA_PROCESSING_ERROR("SENSOR_DATA_PROCESSING_ERROR", "Error processing sensor data"),
    DATETIME_PARSE_ERROR("DATETIME_PARSE_ERROR", "Error parsing date time"),;

    private final String code;
    private final String message;

    ErrorCode(String invalidInput, String s) {
        this.code = invalidInput;
        this.message = s;
    }

    public String fromErrorCode(String errorCode) {
        for (ErrorCode ec : ErrorCode.values()) {
            if (ec.getCode().equals(errorCode)) {
                return ec.code;
            }
        }

        throw new IllegalArgumentException("Unknown error code: " + code);
    }

    @Override
    public String toString() {
        return "ErrorCode {" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}

