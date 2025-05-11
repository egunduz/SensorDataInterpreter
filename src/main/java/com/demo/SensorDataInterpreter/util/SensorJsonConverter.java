package com.demo.SensorDataInterpreter.util;

import com.demo.SensorDataInterpreter.exception.ErrorCode;
import com.demo.SensorDataInterpreter.exception.AppException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Utility class for converting between Java objects and JSON strings.
 * This class uses Jackson's ObjectMapper for serialization and deserialization.
 */
public class SensorJsonConverter {

    private static final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        // Ignore unknown fields
        objectMapper.configure(DeserializationFeature
                .FAIL_ON_UNKNOWN_PROPERTIES, false);
    }


    // Convert Java object to JSON string
    public static String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new AppException(ErrorCode.SENSOR_DATA_SERIALIZATION_ERROR, e);
        }
    }

    // Convert JSON string to Java object of given class
    public static <T> T fromJson(String json, Class<T> targetClass) {
        try {
            return objectMapper.readValue(json, targetClass);
        } catch (JsonProcessingException e) {
            throw new AppException(ErrorCode.SENSOR_DATA_DESERIALIZATION_ERROR, e);
        }
    }
}
