package com.demo.SensorDataInterpreter.service;

import com.demo.SensorDataInterpreter.common.OperationResult;
import com.demo.SensorDataInterpreter.dto.SensorDataDTO;
import com.demo.SensorDataInterpreter.entity.SensorStatisticalEntity;
import com.demo.SensorDataInterpreter.processor.SensorDataProcessor;
import com.demo.SensorDataInterpreter.repository.SensorStatisticalRepository;
import com.demo.SensorDataInterpreter.util.SensorDataMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatisticalDataService implements SensorDataProcessor {
    private final SensorStatisticalRepository sensorStatisticalRepository;
    @Override
    public OperationResult process(SensorDataDTO sensorData) {
        SensorStatisticalEntity sensorStatisticalEntity = SensorDataMapper.toStatisticsEntity(sensorData);
        sensorStatisticalRepository.save(sensorStatisticalEntity);

        return OperationResult.builder()
                .success(true)
                .build();
    }
}
