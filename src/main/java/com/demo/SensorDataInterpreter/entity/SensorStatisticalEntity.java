package com.demo.SensorDataInterpreter.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "sensor_statistics")
public class SensorStatisticalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;

    private Double temperature;
    private Double airPressure;
    private Double humidity;
    private Double lightLevel;
    private Double batteryCharge;
    private Double batteryVoltage;

    private LocalDateTime timestamp;
}
