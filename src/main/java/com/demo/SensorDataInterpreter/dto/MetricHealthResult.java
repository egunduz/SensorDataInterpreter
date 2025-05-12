package com.demo.SensorDataInterpreter.dto;

import com.demo.SensorDataInterpreter.enums.MetricHealthStatus;
import lombok.Builder;
import lombok.Data;

/**
 * Represents the health status of a metric for given time window.
 */
@Data
@Builder
public class MetricHealthResult {
    private final String metricName;
    private final Double currentValue;
    private final Double previousValue;
    private final Long timeWindowInMinutes;
    private final MetricHealthStatus status;
    private final String diagnosis;

    // Factory method for creating a critical metric result
    public static MetricHealthResult critical(
            String metricName,
            Double currentValue,
            Double previousValue,
            Long timeWindowInMinutes,
            String reason) {
        return new MetricHealthResult(
                metricName,
                currentValue,
                previousValue,
                timeWindowInMinutes,
                MetricHealthStatus.CRITICAL,
                reason
        );
    }

    public static MetricHealthResult warn(
            String metricName,
            Double currentValue,
            Double previousValue,
            Long timeWindowInMinutes,
            String reason) {
        return new MetricHealthResult(
                metricName,
                currentValue,
                previousValue,
                timeWindowInMinutes,
                MetricHealthStatus.WARNING,
                reason
        );
    }

    // Using builder pattern for healthy metric result
    public static MetricHealthResult healthy(
            String metricName) {
        return MetricHealthResult.builder().
                metricName(metricName)
                .status(MetricHealthStatus.HEALTHY)
                .build();
    }
}