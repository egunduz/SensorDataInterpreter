package com.demo.SensorDataInterpreter.messaging;

import com.demo.SensorDataInterpreter.processor.SensorMessageProcessor;
import com.demo.SensorDataInterpreter.testutil.SensorDataTestFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SensorMessageListenerTest {

    @Mock
    private SensorMessageProcessor sensorMessageProcessor;

    @InjectMocks
    private SensorMessageListener sensorMessageListener;

    @Test
    void onMessage_processesValidJsonMessage() throws JsonProcessingException {
        // use the SensorDataTestFactory to generate a random sensor data object
        String validJsonMessage = new ObjectMapper().writeValueAsString(SensorDataTestFactory.generateRandomSensorData());

        sensorMessageListener.onMessage(validJsonMessage);

        verify(sensorMessageProcessor, times(1)).handleRawMessage(validJsonMessage);
    }

    @Test
    void onMessage_logsErrorForEmptyMessage() {
        // the listener should handle empty message gracefully

        String emptyMessage = "";

        sensorMessageListener.onMessage(emptyMessage);

        verify(sensorMessageProcessor, times(1)).handleRawMessage(emptyMessage);
    }

    @Test
    void onMessage_handlesMalformedJsonMessage() {
        // the listener should handle invalid messages gracefully

        String malformedJsonMessage = "{id:01, type: HUD21, value:45.6}";

        sensorMessageListener.onMessage(malformedJsonMessage);

        verify(sensorMessageProcessor, times(1)).handleRawMessage(malformedJsonMessage);
    }

    @Test
    void onMessage_processesMessageWithAdditionalFields() {
        // additional fields should not affect the processing of the message

        String jsonMessageWithExtraFields = "{\"id\":\"01\",\"type\":\"HUD21\", \"extraField\":\"extraValue\"}";

        sensorMessageListener.onMessage(jsonMessageWithExtraFields);

        verify(sensorMessageProcessor, times(1)).handleRawMessage(jsonMessageWithExtraFields);
    }
}