package com.demo.SensorDataInterpreter.util;

import com.demo.SensorDataInterpreter.dto.MetricHealthResult;
import com.demo.SensorDataInterpreter.entity.MetricThresholdEntity;
import com.demo.SensorDataInterpreter.entity.SensorMetricsEntity;
import com.demo.SensorDataInterpreter.enums.MetricHealthStatus;
import com.demo.SensorDataInterpreter.enums.MetricThresholdType;
import com.demo.SensorDataInterpreter.enums.MetricType;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class MetricCalculatorTest {

    // Tests null handling for old value
    @Test
    void whenOldValueIsNull_thenReturnHealthy() {
        SensorMetricsEntity newStat = new SensorMetricsEntity();
        newStat.setBatteryCharge(80.0);
        MetricThresholdEntity threshold = new MetricThresholdEntity();
        threshold.setMetric(MetricType.BATTERY_CHARGE);

        MetricHealthResult result = MetricCalculator.calculateHealthiness(null, newStat, threshold);

        assertEquals(MetricHealthStatus.HEALTHY, result.getStatus());
    }

    // Tests null handling for new value
    @Test
    void whenNewValueIsNull_thenReturnHealthy() {
        SensorMetricsEntity oldStat = new SensorMetricsEntity();
        oldStat.setBatteryCharge(80.0);
        MetricThresholdEntity threshold = new MetricThresholdEntity();
        threshold.setMetric(MetricType.BATTERY_CHARGE);

        MetricHealthResult result = MetricCalculator.calculateHealthiness(oldStat, null, threshold);

        assertEquals(MetricHealthStatus.HEALTHY, result.getStatus());
    }

    // Tests percentage threshold violation
    @Test
    void whenPercentageChangeExceedsThreshold_thenReturnCritical() {
        SensorMetricsEntity oldStat = new SensorMetricsEntity();
        oldStat.setBatteryCharge(100.0);
        oldStat.setTimestamp(LocalDateTime.now().minusMinutes(10));

        SensorMetricsEntity newStat = new SensorMetricsEntity();
        newStat.setBatteryCharge(70.0); // 30% drop
        newStat.setTimestamp(LocalDateTime.now());

        MetricThresholdEntity threshold = new MetricThresholdEntity();
        threshold.setMetric(MetricType.BATTERY_CHARGE);
        threshold.setThresholdType(MetricThresholdType.PERCENTAGE);
        threshold.setChangeThreshold(20.0);
        threshold.setTimeWindowMinutes(5);

        MetricHealthResult result = MetricCalculator.calculateHealthiness(oldStat, newStat, threshold);

        assertEquals(MetricHealthStatus.CRITICAL, result.getStatus());
    }

    // Tests minimum value violation
    @Test
    void whenValueBelowMinimum_thenReturnCritical() {
        SensorMetricsEntity oldStat = new SensorMetricsEntity();
        oldStat.setTemperature(25.0);
        oldStat.setTimestamp(LocalDateTime.now().minusMinutes(2));

        SensorMetricsEntity newStat = new SensorMetricsEntity();
        newStat.setTemperature(15.0); // Below minimum of 20
        newStat.setTimestamp(LocalDateTime.now());

        MetricThresholdEntity threshold = new MetricThresholdEntity();
        threshold.setMetric(MetricType.TEMPERATURE);
        threshold.setMinValue(20.0);

        MetricHealthResult result = MetricCalculator.calculateHealthiness(oldStat, newStat, threshold);

        assertEquals(MetricHealthStatus.CRITICAL, result.getStatus());
    }

    // Tests maximum value violation
    @Test
    void whenValueAboveMaximum_thenReturnCritical() {
        SensorMetricsEntity oldStat = new SensorMetricsEntity();
        oldStat.setBatteryVoltage(12.0);
        oldStat.setTimestamp(LocalDateTime.now().minusMinutes(3));

        SensorMetricsEntity newStat = new SensorMetricsEntity();
        newStat.setBatteryVoltage(15.0); // Above maximum of 14
        newStat.setTimestamp(LocalDateTime.now());

        MetricThresholdEntity threshold = new MetricThresholdEntity();
        threshold.setMetric(MetricType.BATTERY_VOLTAGE);
        threshold.setMaxValue(14.0);

        MetricHealthResult result = MetricCalculator.calculateHealthiness(oldStat, newStat, threshold);

        assertEquals(MetricHealthStatus.CRITICAL, result.getStatus());
    }

    // Tests division by zero handling
    @Test
    void whenOldValueIsZero_thenReturnCritical() {
        SensorMetricsEntity oldStat = new SensorMetricsEntity();
        oldStat.setBatteryCharge(0.0);
        oldStat.setTimestamp(LocalDateTime.now().minusMinutes(7));

        SensorMetricsEntity newStat = new SensorMetricsEntity();
        newStat.setBatteryCharge(50.0);
        newStat.setTimestamp(LocalDateTime.now());

        MetricThresholdEntity threshold = new MetricThresholdEntity();
        threshold.setMetric(MetricType.BATTERY_CHARGE);
        threshold.setThresholdType(MetricThresholdType.PERCENTAGE);

        MetricHealthResult result = MetricCalculator.calculateHealthiness(oldStat, newStat, threshold);

        assertEquals(MetricHealthStatus.CRITICAL, result.getStatus());
    }

    // Tests unsupported metric handling
    @Test
    void whenUnsupportedMetricType_thenReturnWarning() {
        SensorMetricsEntity oldStat = new SensorMetricsEntity();
        oldStat.setTimestamp(LocalDateTime.now().minusMinutes(6));

        SensorMetricsEntity newStat = new SensorMetricsEntity();
        newStat.setTimestamp(LocalDateTime.now());

        MetricThresholdEntity threshold = new MetricThresholdEntity();
        threshold.setMetric(null);

        MetricHealthResult result = MetricCalculator.calculateHealthiness(oldStat, newStat, threshold);

        assertEquals(MetricHealthStatus.HEALTHY, result.getStatus());
    }
}