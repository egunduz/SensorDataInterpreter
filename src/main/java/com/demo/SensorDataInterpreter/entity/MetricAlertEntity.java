package com.demo.SensorDataInterpreter.entity;

import com.demo.SensorDataInterpreter.enums.MetricType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Entity class representing metric alerts generated based on threshold conditions.
 * We will list those alerts in the UI.
 * Table can also be viewed in h2-console.
 */
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
    private String level; // warning, critical
    private String message;

    private LocalDateTime createdAt = LocalDateTime.now();
}
