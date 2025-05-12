package com.demo.SensorDataInterpreter.entity;

import com.demo.SensorDataInterpreter.enums.MetricType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "metric_alerts")
public class MetricAlertEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private MetricType metric;

    private Double oldValue;
    private Double newValue;
    private Long timeWindowInMinutes;
    private String message;

    private LocalDateTime createdAt = LocalDateTime.now();
}
