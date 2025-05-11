package com.demo.SensorDataInterpreter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;


@Data
public class SensorStatusChangeDTO {
    @JsonProperty("device_id")
    private String deviceId;

    @JsonProperty("vehicle_id")
    private String vehicleId;

    @JsonProperty("vehicle_type")
    private String vehicleType;

    @JsonProperty("propulsion_type")
    private List<String> propulsionType;

    @JsonProperty("event_type")
    private String eventType;

    @JsonProperty("event_type_reason")
    private String eventTypeReason;

    @JsonProperty("event_timen")
    private Long eventTime;

    @JsonProperty("event_location")
    private SensorEventLocationDTO eventLocation;
}