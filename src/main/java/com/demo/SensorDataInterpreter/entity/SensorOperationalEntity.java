package com.demo.SensorDataInterpreter.entity;

import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "sensor_operations")
public class SensorOperationalEntity {

    @Id
    private String id;
    private String type;

    private List<StatusChange> statusChanges;

    @Data
    public static class StatusChange {
        private String deviceId;
        private String vehicleId;
        private String vehicleType;
        private List<String> propulsionType;
        private String eventType;
        private String eventTypeReason;
        private Long eventTime;
        private EventLocation eventLocation;
    }

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
