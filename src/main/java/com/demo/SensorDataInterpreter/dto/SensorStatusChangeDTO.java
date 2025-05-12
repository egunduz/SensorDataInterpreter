package com.demo.SensorDataInterpreter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * DTO for sensor status change events.
 * This class is used to transfer data between the API and the service layer.
 * The json representation (JsonProperty) is used to be compatible with the incoming data
 */
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

    @JsonProperty("event_timen") // in the task document event_timen was used. I don't know if it's a typo or not
    private Long eventTime;

    @JsonProperty("event_location")
    private SensorEventLocationDTO eventLocation;
}