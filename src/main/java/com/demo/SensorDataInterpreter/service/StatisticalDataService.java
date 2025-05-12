package com.demo.SensorDataInterpreter.service;

import com.demo.SensorDataInterpreter.common.OperationResult;
import com.demo.SensorDataInterpreter.dto.MetricHealthResult;
import com.demo.SensorDataInterpreter.dto.SensorDataDTO;
import com.demo.SensorDataInterpreter.entity.MetricAlertEntity;
import com.demo.SensorDataInterpreter.entity.MetricThresholdEntity;
import com.demo.SensorDataInterpreter.entity.SensorStatisticalEntity;
import com.demo.SensorDataInterpreter.enums.MetricHealthStatus;
import com.demo.SensorDataInterpreter.processor.SensorDataProcessor;
import com.demo.SensorDataInterpreter.repository.MetricAlertRepository;
import com.demo.SensorDataInterpreter.repository.MetricThresholdRepository;
import com.demo.SensorDataInterpreter.repository.SensorStatisticalRepository;
import com.demo.SensorDataInterpreter.util.MetricCalculator;
import com.demo.SensorDataInterpreter.util.SensorDataMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatisticalDataService implements SensorDataProcessor {
    private final SensorStatisticalRepository sensorStatisticalRepository;
    private final MetricThresholdRepository thresholdRepository;
    private final MetricAlertRepository alertRepository;

    @Override
    public OperationResult process(SensorDataDTO sensorData) {
        // Current metrics
        SensorStatisticalEntity newMetric = SensorDataMapper.toStatisticsEntity(sensorData);
        // Previous metrics
        SensorStatisticalEntity oldMetric = sensorStatisticalRepository.findLatest();
        // Metric thresholds
        List<MetricThresholdEntity> thresholds = thresholdRepository.findAll();

        // For each metric, check if the threshold is violated
        for (MetricThresholdEntity threshold : thresholds) {
            String metricName = threshold.getMetric().name();

            try {
                log.info("Calculating metric for type: {}", metricName);

                // Check if the metric threshold is violated
                MetricHealthResult healthiness = MetricCalculator.calculateHealthiness(oldMetric, newMetric, threshold);
                if (healthiness.getStatus().getSeverity() > MetricHealthStatus.WARNING.getSeverity()) {
                    MetricAlertEntity alert = new MetricAlertEntity();
                    alert.setMetric(threshold.getMetric());
                    alert.setOldValue(healthiness.getPreviousValue());
                    alert.setNewValue(healthiness.getCurrentValue());
                    alert.setTimeWindowInMinutes(healthiness.getTimeWindowInMinutes());
                    alert.setMessage(healthiness.getStatus() + ": " + healthiness.getDiagnosis());

                    alertRepository.save(alert);
                }
            } catch (Exception e) {
                log.error("Error while evaluating metric: {} ", metricName,  e);
            }
        }

        sensorStatisticalRepository.save(newMetric);

        return OperationResult.builder().
                success(true).
                build();
    }
}
