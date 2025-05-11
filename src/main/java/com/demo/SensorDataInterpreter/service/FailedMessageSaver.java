package com.demo.SensorDataInterpreter.service;

import com.demo.SensorDataInterpreter.entity.FailedSensorMessageEntity;
import com.demo.SensorDataInterpreter.repository.FailedSensorMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class FailedMessageSaver {

    private final FailedSensorMessageRepository repository;

    public void saveDeserializationError(String payload, String errorMessage) {
        FailedSensorMessageEntity failed = FailedSensorMessageEntity.builder()
                .failureType("DESERIALIZATION_ERROR")
                .failureReason(errorMessage)
                .rawPayload(payload)
                .failedAt(Instant.now())
                .build();

        repository.save(failed);
    }

    public void saveProcessingError(Object dto, String errorMessage) {
        FailedSensorMessageEntity failed = FailedSensorMessageEntity.builder()
                .failureType("PROCESSING_ERROR")
                .failureReason(errorMessage)
                .extractedData(dto instanceof Map ? (Map<String, Object>) dto : Map.of("object", dto))
                .failedAt(Instant.now())
                .build();

        repository.save(failed);
    }
}
