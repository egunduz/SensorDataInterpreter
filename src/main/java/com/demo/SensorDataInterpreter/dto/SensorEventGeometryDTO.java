package com.demo.SensorDataInterpreter.dto;

import lombok.Data;
import java.util.List;

@Data
public class SensorEventGeometryDTO {
    private String type;
    private List<Double> coordinates;
}