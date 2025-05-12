package com.demo.SensorDataInterpreter.entity;

import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Represents statistical data collected from sensors.
 * Stored in MongoDB to reflect changes in data structure.
 * Can be moved to h2 database if needed.
 */
@Data
@Document(collection = "sensor_status_changes")
public class SensorStatusChangeEntity {

    @Id
    private String id;

    private String deviceId;
    private String vehicleId;
    private String vehicleType;
    private List<String> propulsionType;
    private String eventType;
    private String eventTypeReason;
    private Long eventTime;
    private EventLocation eventLocation;
    private LocalDateTime createdAt = LocalDateTime.now();

    @Data
    public static class EventLocation {
        private Geometry geometry;
    }

    @Data
    public static class Geometry {
        private String type;
        private List<Double> coordinates;
    }
}
