package com.demo.SensorDataInterpreter.processor;

import com.demo.SensorDataInterpreter.dto.SensorDataDTO;
import com.demo.SensorDataInterpreter.enums.FailureType;
import com.demo.SensorDataInterpreter.entity.FailedSensorMessageEntity;
import com.demo.SensorDataInterpreter.exception.ErrorCode;
import com.demo.SensorDataInterpreter.repository.FailedSensorMessageRepository;
import com.demo.SensorDataInterpreter.service.OperationalDataService;
import com.demo.SensorDataInterpreter.service.StatisticalDataService;
import com.demo.SensorDataInterpreter.util.SensorJsonConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SensorMessageProcessor {

    private final OperationalDataService operationalDataService;
    private final StatisticalDataService statisticalDataService;
    private final FailedSensorMessageRepository failedMessageRepository;

    public void handleRawMessage(String rawMessage) {
        SensorDataDTO dto = null;
        try {
            dto = SensorJsonConverter.fromJson(rawMessage, SensorDataDTO.class);
        } catch (Exception e) {
            log.error(ErrorCode.SENSOR_DATA_DESERIALIZATION_ERROR.getCode(), rawMessage, e);

            FailedSensorMessageEntity failed = FailedSensorMessageEntity.builder()
                    .rawPayload(rawMessage)
                    .failureType(FailureType.PROCESSING_FAILURE.getValue())
                    .failureReason(e.getMessage())
                    .failedAt(java.time.Instant.now())
                    .build();

            failedMessageRepository.save(failed);
        }

        if (dto != null) {
            operationalDataService.process(dto);
            statisticalDataService.process(dto);
        }else {
            FailedSensorMessageEntity failed = FailedSensorMessageEntity.builder()
                    .rawPayload(rawMessage)
                    .failureType(FailureType.VALIDATION_FAILURE.getValue())
                    .failureReason("Empty DTO after deserialization")
                    .failedAt(java.time.Instant.now())
                    .build();

            failedMessageRepository.save(failed);
        }
    }
}

