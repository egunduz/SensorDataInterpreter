package com.demo.SensorDataInterpreter.enums;

import lombok.Getter;

@Getter
public enum MetricHealthStatus {
    HEALTHY(0), WARNING(1), CRITICAL(2);
    private final int severity;
    MetricHealthStatus(int severity) { this.severity = severity; }
}