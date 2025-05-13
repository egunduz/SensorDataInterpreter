package com.demo.SensorDataInterpreter.util;

import com.demo.SensorDataInterpreter.dto.MetricHealthResult;
import com.demo.SensorDataInterpreter.entity.MetricThresholdEntity;
import com.demo.SensorDataInterpreter.entity.SensorMetricsEntity;
import com.demo.SensorDataInterpreter.enums.MetricThresholdType;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

/**
 * helper class to calculate metrics from the sensor data.
 */
@Slf4j
public class MetricCalculator {
    /**
     * Calculate the healthiness of a metric based on the previous and new sensor data.
     *
     * @param prevStat  Previous sensor data
     * @param newStat   New sensor data
     * @param threshold Metric threshold entity
     * @return Metric health result
     */
    public static MetricHealthResult calculateHealthiness(SensorMetricsEntity prevStat, SensorMetricsEntity newStat, MetricThresholdEntity threshold) {
        if (threshold.getMetric() == null) {
            log.warn("Metric is null, cannot calculate healthiness");
            return MetricHealthResult.healthy("Unknown");
        }

        String metricName = threshold.getMetric().name();
        Double oldValue = null;
        Double newValue = null;
        long timeWindowMinutes = 0;

        // calculate time change between the two status
        if (prevStat != null && newStat != null && prevStat.getTimestamp() != null && newStat.getTimestamp() != null) {
            timeWindowMinutes = Duration.between(prevStat.getTimestamp(),newStat.getTimestamp()).toMinutes();
        }

        switch (threshold.getMetric()) {
            case BATTERY_CHARGE:
                oldValue = prevStat != null ? prevStat.getBatteryCharge() : null;
                newValue = newStat != null ? newStat.getBatteryCharge() : null;
                break;
            case BATTERY_VOLTAGE:
                oldValue = prevStat != null ? prevStat.getBatteryVoltage() : null;
                newValue = newStat != null ? newStat.getBatteryVoltage() : null;
                break;
            case TEMPERATURE:
                oldValue = prevStat != null ? prevStat.getTemperature() : null;
                newValue = newStat != null ? newStat.getTemperature() : null;
                break;
            default:
                return MetricHealthResult.warn(
                        metricName,
                        newValue,
                        oldValue,
                        timeWindowMinutes,
                        "Unsupported metric type: " + metricName
                );
        }

        if (oldValue == null) {
            // can not evaluate the metric if the old value is null
            return MetricHealthResult.healthy(metricName);
        }

        if (newValue == null) {
            // can not evaluate the metric if the new value is null
            return MetricHealthResult.healthy(metricName);
        }

        // Calculate the absolute change between the old and new values
        double change = Math.abs(newValue - oldValue);
        // Calculate the percentage change if the threshold type is percentage
        if (threshold.getThresholdType() == MetricThresholdType.PERCENTAGE) {
            if (oldValue == 0.0) {
                // to avoid divide-by-zero
                return MetricHealthResult.critical(
                                metricName,
                                newValue,
                                oldValue,
                                timeWindowMinutes,
                                "Old value is zero, cannot calculate percentage change");
            }
            change = (change / oldValue) * 100;
        }

        // Check if the change exceeds the threshold
        Double thresholdValue = threshold.getChangeThreshold();
        if (thresholdValue != null
                && change > thresholdValue
                && timeWindowMinutes > threshold.getTimeWindowMinutes()) {

            return MetricHealthResult.critical(
                            metricName,
                            newValue,
                            oldValue,
                            timeWindowMinutes,
                    "Threshold exceeded in time window: " + timeWindowMinutes
                            + ". Change: " + change
                            + ". Threshold: " + thresholdValue);

        }

        // Check if new value is under minimum
        if (threshold.getMinValue() != null && newValue < threshold.getMinValue()) {
            return MetricHealthResult.critical(
                            metricName,
                            newValue,
                            oldValue,
                            timeWindowMinutes,
                            "Minimum value: " + threshold.getMinValue() + " not met");
        }

        // Check if new value is over maximum
        if (threshold.getMaxValue() != null && newValue > threshold.getMaxValue()) {
            return MetricHealthResult.critical(
                    metricName,
                    newValue,
                    oldValue,
                    timeWindowMinutes,
                    "Max value: " + threshold.getMaxValue() + " exceeded");
        }

        // everything is ok
        return MetricHealthResult.healthy(metricName);
    }
}
