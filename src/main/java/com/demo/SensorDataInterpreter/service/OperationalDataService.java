package com.demo.SensorDataInterpreter.service;
import com.demo.SensorDataInterpreter.common.OperationResult;
import com.demo.SensorDataInterpreter.dto.LocationHistoryRequestDTO;
import com.demo.SensorDataInterpreter.dto.LocationHistoryResponseDTO;
import com.demo.SensorDataInterpreter.dto.SensorDataDTO;
import com.demo.SensorDataInterpreter.entity.SensorOperationalEntity;
import com.demo.SensorDataInterpreter.processor.SensorDataProcessor;
import com.demo.SensorDataInterpreter.repository.SensorOperationalRepository;
import com.demo.SensorDataInterpreter.util.DateTimeConverter;
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

    @Override
    public OperationResult process(SensorDataDTO sensorData) {

        SensorOperationalEntity operationalEntity = SensorDataMapper.toOperationalEntity(sensorData);
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
