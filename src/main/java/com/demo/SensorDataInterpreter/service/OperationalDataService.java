package com.demo.SensorDataInterpreter.service;

import com.demo.SensorDataInterpreter.common.OperationResult;
import com.demo.SensorDataInterpreter.dto.MetricHealthResult;
import com.demo.SensorDataInterpreter.dto.SensorDataDTO;
import com.demo.SensorDataInterpreter.entity.MetricAlertEntity;
import com.demo.SensorDataInterpreter.entity.MetricThresholdEntity;
import com.demo.SensorDataInterpreter.entity.SensorMetricsEntity;
import com.demo.SensorDataInterpreter.enums.MetricHealthStatus;
import com.demo.SensorDataInterpreter.processor.SensorDataProcessor;
import com.demo.SensorDataInterpreter.repository.MetricAlertRepository;
import com.demo.SensorDataInterpreter.repository.MetricThresholdRepository;
import com.demo.SensorDataInterpreter.repository.SensorMetricsRepository;
import com.demo.SensorDataInterpreter.util.MetricCalculator;
import com.demo.SensorDataInterpreter.util.SensorDataMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service/handler for processing operational data.
 * It checks metrics if they are within the defined thresholds.
 * If a threshold is violated, it creates an alert.
 * Later all metrics are saved to the database.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class OperationalDataService implements SensorDataProcessor {
    private final SensorMetricsRepository sensorStatisticalRepository;
    private final MetricThresholdRepository thresholdRepository;
    private final MetricAlertService alertService;

    @Override
    public OperationResult process(SensorDataDTO sensorData) {
        SensorMetricsEntity newMetric = null;
        try {
            // Current metrics
            newMetric = SensorDataMapper.toStatisticsEntity(sensorData);
            // Previous metrics
            SensorMetricsEntity oldMetric = sensorStatisticalRepository.findLatest();
            // Metric thresholds
            List<MetricThresholdEntity> thresholds = thresholdRepository.findAll();

            // For each metric, check if the threshold is violated
            for (MetricThresholdEntity threshold : thresholds) {
                String metricName = threshold.getMetric().name();

                try {
                    // Check if the metric threshold is violated
                    MetricHealthResult healthiness = MetricCalculator.calculateHealthiness(oldMetric, newMetric, threshold);
                    if (healthiness.getStatus().getSeverity() > MetricHealthStatus.WARNING.getSeverity()) {
                        // generate alert for WARNING and CRITICAL status
                        MetricAlertEntity alert = new MetricAlertEntity();
                        alert.setMetric(threshold.getMetric());
                        alert.setOldValue(healthiness.getPreviousValue());
                        alert.setNewValue(healthiness.getCurrentValue());
                        alert.setTimeWindowInMinutes(healthiness.getTimeWindowInMinutes());
                        alert.setLevel(healthiness.getStatus().name());
                        alert.setMessage(healthiness.getDiagnosis());

                        alertService.save(alert);
                    }
                } catch (Exception e) {
                    // We can tolerate this error. Log it and continue with the next metric
                    log.error("Error while evaluating metric: {} ", metricName,  e);
                }
            }

            // Save the new metric to the database
            sensorStatisticalRepository.save(newMetric);

        } catch (Exception e) {
            // Log the error and return a failure result to be handled by the caller
            log.error("Failed to proceed operational data process", e);
            return OperationResult.failure("Operational data process failed: " + e.getMessage());
        }

        return OperationResult.success();
    }
}
