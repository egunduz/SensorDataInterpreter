package com.demo.SensorDataInterpreter.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Entity class representing operational metrics collected form sensors.
 * Use h2-console to view the data in the database.
 */
@Data
@Entity
@Table(name = "sensor_metrics")
public class SensorMetricsEntity {

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
