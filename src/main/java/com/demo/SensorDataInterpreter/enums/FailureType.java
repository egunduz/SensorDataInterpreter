package com.demo.SensorDataInterpreter.enums;

public enum FailureType {
    PARSING_FAILURE("PARSING_FAILURE"),
    PROCESSING_FAILURE("PROCESSING_FAILURE"),
    VALIDATION_FAILURE("VALIDATION_FAILURE");
    private final String value;
    FailureType(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
