package com.demo.SensorDataInterpreter.scheduler;

import com.demo.SensorDataInterpreter.service.FailedMessageRetryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Scheduler for retrying failed messages.
 * This class is responsible for periodically checking and reprocessing failed messages.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class FailedMessageRetryScheduler {

    private final FailedMessageRetryService retryService;

    @Scheduled(fixedRate = 100000) // Runs every 10 seconds
    public void retryFailedMessages() {
        int successCount = retryService.reProcess();
        log.info("Retried failed messages. Successfully processed: {}", successCount);
    }
}
