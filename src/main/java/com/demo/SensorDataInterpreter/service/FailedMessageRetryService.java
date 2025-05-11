package com.demo.SensorDataInterpreter.service;

import com.demo.SensorDataInterpreter.dto.SensorDataDTO;
import com.demo.SensorDataInterpreter.entity.FailedSensorMessageEntity;
import com.demo.SensorDataInterpreter.repository.FailedSensorMessageRepository;
import com.demo.SensorDataInterpreter.util.SensorJsonConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for retrying the processing of failed sensor messages.
 * It attempts to reprocess messages that have failed previously, up to a maximum retry count.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class FailedMessageRetryService {
    private final static int MAX_RETRY_COUNT = 2;
    private final FailedSensorMessageRepository failedRepository;
    private final StatisticalDataService statisticalDataService;
    private final OperationalDataService operationalDataService;

    public int reprocess() {
        // Fetch all failed messages that have not exceeded the maximum retry count
        // and attempt to reprocess them

        List<FailedSensorMessageEntity> failedMessages =
                failedRepository.findAll().stream()
                        .filter(failed -> failed.getRetryCount() < MAX_RETRY_COUNT)
                        .toList();

        int successCount = 0;

        for (FailedSensorMessageEntity failed : failedMessages) {
            try {
                SensorDataDTO dto = SensorJsonConverter.fromJson(failed.getRawPayload(), SensorDataDTO.class);
                operationalDataService.process(dto);
                statisticalDataService.process(dto);

                successCount++;
            } catch (Exception e) {
                log.error("Retry failed for message ID {}: {}", failed.getId(), e.getMessage());
                failed.setRetryCount(failed.getRetryCount() + 1);
                failedRepository.save(failed);
            }
        }

        return successCount;
    }
}
