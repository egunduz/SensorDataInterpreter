package com.demo.SensorDataInterpreter.entity;

import com.demo.SensorDataInterpreter.enums.MetricType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class MetricAlertEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String deviceId;

    @Enumerated(EnumType.STRING)
    private MetricType metric;

    private Double oldValue;
    private Double newValue;
    private Double timeWindowInMinutes;
    private String message;

    private LocalDateTime createdAt = LocalDateTime.now();
}
