package com.demo.SensorDataInterpreter.util;

import com.demo.SensorDataInterpreter.entity.MetricThresholdEntity;

/**
 * helper class to calculate metrics from the sensor data.
 */
public class MetricCalculator {

    /**
     * Checks if the threshold is violated based on the old and new values.
     * @param oldValue            The old value of the metric.
     * @param newValue            The new value of the metric.
     * @param durationOfChangeInMinutes The time window in minutes
     * @param threshold           The threshold entity containing the threshold values.
     * @return true if the threshold is violated, false otherwise.
     */
    public static boolean isThresholdViolated(Double oldValue, Double newValue, Double durationOfChangeInMinutes, MetricThresholdEntity threshold) {
        if (oldValue == null || newValue == null || threshold == null) return false;

        double change = Math.abs(newValue - oldValue);

        switch (threshold.getThresholdType()) {
            case SCALAR:
                return (threshold.getMinValue() != null && change < threshold.getMinValue()) ||
                        (threshold.getMaxValue() != null && change > threshold.getMaxValue());

            case PERCENTAGE:
                if (oldValue == 0.0) return false; // to avoid divide-by-zero
                double percentageChange = (change / oldValue) * 100;
                return (threshold.getMinValue() != null && percentageChange < threshold.getMinValue()) ||
                        (threshold.getMaxValue() != null && percentageChange > threshold.getMaxValue());

            default:
                return false;
        }
    }
}
