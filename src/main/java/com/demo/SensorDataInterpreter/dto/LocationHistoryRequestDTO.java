package com.demo.SensorDataInterpreter.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LocationHistoryRequestDTO {
    @NotNull
    private String deviceId;
    @NotEmpty
    private String from;
    @NotEmpty
    private String to;
}
