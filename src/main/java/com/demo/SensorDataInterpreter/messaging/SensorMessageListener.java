package com.demo.SensorDataInterpreter.messaging;

import com.demo.SensorDataInterpreter.processor.SensorMessageProcessor;
import com.demo.SensorDataInterpreter.service.FailedMessageSaver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SensorMessageListener {

    private final FailedMessageSaver failedMessageSaver;
    private final SensorMessageProcessor sensorMessageProcessor;

    @JmsListener(destination = "${sensor.queue.name}")
    public void onMessage(String jsonMessage) {
        sensorMessageProcessor.handleRawMessage(jsonMessage);
    }
}
