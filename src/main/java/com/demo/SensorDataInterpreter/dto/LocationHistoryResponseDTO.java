package com.demo.SensorDataInterpreter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class LocationHistoryResponseDTO {
    private String deviceId;
    private String eventTime; // ISO 8601
    private EventLocation eventLocation;

    @Data
    @AllArgsConstructor
    public static class EventLocation {
        private Geometry geometry;
    }

    @Data
    @AllArgsConstructor
    public static class Geometry {
        private String type;
        private List<Double> coordinates;
    }
}
