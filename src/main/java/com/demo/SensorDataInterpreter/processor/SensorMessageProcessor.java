package com.demo.SensorDataInterpreter.processor;

import com.demo.SensorDataInterpreter.dto.SensorDataDTO;
import com.demo.SensorDataInterpreter.enums.FailureType;
import com.demo.SensorDataInterpreter.entity.FailedMessageEntity;
import com.demo.SensorDataInterpreter.exception.ErrorCode;
import com.demo.SensorDataInterpreter.service.FailedMessageService;
import com.demo.SensorDataInterpreter.service.StatisticalDataService;
import com.demo.SensorDataInterpreter.service.OperationalDataService;
import com.demo.SensorDataInterpreter.util.SensorJsonConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 *  Intermediate layer between the raw message and the service layer.
 *  It is responsible for deserializing the raw message and passing it to the appropriate service.
 *  It also handles any errors that occur during deserialization or processing.
 *  If processing fails, it saves the failed message to the database for later retry.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class SensorMessageProcessor {

    private final StatisticalDataService operationalDataService;
    private final OperationalDataService statisticalDataService;
    private final FailedMessageService failedMessageService;

    public void handleRawMessage(String rawMessage) {
        SensorDataDTO dto = null;
        try {
            dto = SensorJsonConverter.fromJson(rawMessage, SensorDataDTO.class);
        } catch (Exception e) {
            log.error(ErrorCode.SENSOR_DATA_DESERIALIZATION_ERROR.getCode(), rawMessage, e);

            FailedMessageEntity failed = FailedMessageEntity.builder()
                    .rawPayload(rawMessage)
                    .failureType(FailureType.PROCESSING_FAILURE.getValue())
                    .failureReason(e.getMessage())
                    .failedAt(java.time.Instant.now())
                    .build();

            failedMessageService.save(failed);
        }

        if (dto != null) {
            operationalDataService.process(dto);
            statisticalDataService.process(dto);
        }else {
            FailedMessageEntity failed = FailedMessageEntity.builder()
                    .rawPayload(rawMessage)
                    .failureType(FailureType.VALIDATION_FAILURE.getValue())
                    .failureReason("Empty DTO after deserialization")
                    .failedAt(java.time.Instant.now())
                    .build();

            failedMessageService.save(failed);
        }
    }
}

