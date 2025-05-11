package com.demo.SensorDataInterpreter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class SensorDataDTO {
    private String id;
    private String type;
    private Double temperature;
    private Double airPressure;
    private Double humidity;
    private Double lightLevel;
    private Double batteryCharge;
    private Double batteryVoltage;

    @JsonProperty("status_changes")
    private List<SensorStatusChangeDTO> statusChanges;
}
