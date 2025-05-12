package com.demo.SensorDataInterpreter.enums;

import lombok.Getter;

@Getter
public enum MetricHealthStatus {
    HEALTHY(0), WARNING(1), CRITICAL(2);
    // this field is for quick comparisons like if (status.getSeverity() > 0)
    private final int severity;
    MetricHealthStatus(int severity) { this.severity = severity; }
}