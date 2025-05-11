package com.demo.SensorDataInterpreter.service;
import com.demo.SensorDataInterpreter.common.OperationResult;
import com.demo.SensorDataInterpreter.dto.LocationHistoryRequestDTO;
import com.demo.SensorDataInterpreter.dto.LocationHistoryResponseDTO;
import com.demo.SensorDataInterpreter.dto.SensorDataDTO;
import com.demo.SensorDataInterpreter.entity.MetricAlertEntity;
import com.demo.SensorDataInterpreter.entity.MetricThresholdEntity;
import com.demo.SensorDataInterpreter.entity.SensorOperationalEntity;
import com.demo.SensorDataInterpreter.processor.SensorDataProcessor;
import com.demo.SensorDataInterpreter.repository.MetricAlertRepository;
import com.demo.SensorDataInterpreter.repository.MetricThresholdRepository;
import com.demo.SensorDataInterpreter.repository.SensorOperationalRepository;
import com.demo.SensorDataInterpreter.util.DateTimeConverter;
import com.demo.SensorDataInterpreter.util.MetricCalculator;
import com.demo.SensorDataInterpreter.util.SensorDataMapper;
import com.demo.SensorDataInterpreter.util.StatusChangeToLocationHistoryMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OperationalDataService implements SensorDataProcessor {
    private final SensorOperationalRepository operationalRepository;
    private final MetricThresholdRepository thresholdRepository;
    private final MetricAlertRepository alertRepository;

    @Override
    public OperationResult process(SensorDataDTO sensorData) {
        // Collect the sensor data
        SensorOperationalEntity operationalEntity = SensorDataMapper.toOperationalEntity(sensorData);

        // Get the metric thresholds from the repository
        List<MetricThresholdEntity> thresholds = thresholdRepository.findAll();

        // Check if the device ID is present in the sensor data
        for (MetricThresholdEntity threshold : thresholds) {
            try {
                log.info("Calculating metric for type: {}", threshold.getMetric().name());

                // Check if the metric is present in the sensor data
                if (MetricCalculator.isThresholdViolated(oldVal, newVal, threshold)) {
                    MetricAlertEntity alert = new MetricAlertEntity();
                    alert.setDeviceId(deviceId);
                    alert.setMetric(threshold.getMetric());
                    alert.setOldValue(0.0);
                    alert.setNewValue(0.0);
                    alert.setTimeWindowInMinutes(0.0);
                    alert.setMessage("Threshold exceeded for " + threshold.getMetric());

                    alertRepository.save(alert);
                }
            } catch (Exception e) {
                // Handle the exception as needed
                System.out.println("Error calculating metric: " + e.getMessage());
            }
        }

        operationalRepository.save(operationalEntity);

        return OperationResult.builder().
                success(true).
                build();
    }


    public List<LocationHistoryResponseDTO> getDeviceLocationHistory(LocationHistoryRequestDTO requestDTO) {
        var statusChanges = operationalRepository
                .findLocationHistory(requestDTO.getDeviceId(),
                        DateTimeConverter.toSeconds(requestDTO.getFrom()),
                        DateTimeConverter.toSeconds(requestDTO.getTo()));

        // convert the entity to response DTO
        return statusChanges.stream()
                .flatMap(sensorOperationalEntity -> sensorOperationalEntity.getStatusChanges().stream())
                .filter(x-> x.getEventLocation() != null)
                .map(StatusChangeToLocationHistoryMapper::map)
                .toList();
    }
}
