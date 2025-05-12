package com.demo.SensorDataInterpreter.service;
import com.demo.SensorDataInterpreter.common.OperationResult;
import com.demo.SensorDataInterpreter.dto.LocationHistoryRequestDTO;
import com.demo.SensorDataInterpreter.dto.LocationHistoryResponseDTO;
import com.demo.SensorDataInterpreter.dto.SensorDataDTO;
import com.demo.SensorDataInterpreter.entity.SensorStatusChangeEntity;
import com.demo.SensorDataInterpreter.processor.SensorDataProcessor;
import com.demo.SensorDataInterpreter.repository.SensorStatusChangeRepository;
import com.demo.SensorDataInterpreter.util.DateTimeConverter;
import com.demo.SensorDataInterpreter.util.SensorDataMapper;
import com.demo.SensorDataInterpreter.util.LocationHistoryMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatisticalDataService implements SensorDataProcessor {
    private final SensorStatusChangeRepository operationalRepository;

    @Override
    public OperationResult process(SensorDataDTO sensorData) {

        List<SensorStatusChangeEntity> entities = SensorDataMapper.toStatisticalEntity(sensorData);
        operationalRepository.saveAll(entities);

        return OperationResult.success();
    }

    public List<LocationHistoryResponseDTO> getDeviceLocationHistory(LocationHistoryRequestDTO requestDTO) {
        var statusChanges = operationalRepository
                .findLocationHistory(requestDTO.getDeviceId(),
                        DateTimeConverter.toSeconds(requestDTO.getFrom()),
                        DateTimeConverter.toSeconds(requestDTO.getTo()));

        // convert the entity to response DTO
        return statusChanges.stream()
                .filter(x-> x.getEventLocation() != null)
                .map(LocationHistoryMapper::map)
                .toList();
    }
}
