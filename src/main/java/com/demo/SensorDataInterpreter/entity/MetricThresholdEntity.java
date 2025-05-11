package com.demo.SensorDataInterpreter.entity;

import com.demo.SensorDataInterpreter.enums.MetricChangeDirection;
import com.demo.SensorDataInterpreter.enums.MetricThresholdType;
import com.demo.SensorDataInterpreter.enums.MetricType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity class representing metric threshold for alert generation.
 */
@Entity
@Table(name = "metric_thresholds")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MetricThresholdEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MetricType metric;

    @Enumerated(EnumType.STRING)
    private MetricChangeDirection direction;

    private Integer timeWindowMinutes;

    private Double changeThreshold;

    private Double minValue;

    private Double maxValue;

    @Enumerated(EnumType.STRING)
    private MetricThresholdType thresholdType;
}

