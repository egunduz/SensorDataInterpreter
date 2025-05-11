package com.demo.SensorDataInterpreter.util;

import com.demo.SensorDataInterpreter.dto.SensorDataDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.jms.core.MessageCreator;
import org.springframework.lang.NonNull;

import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.Session;

public class SensorMessageCreator implements MessageCreator {

    private final SensorDataDTO sensorData;
    private final ObjectMapper objectMapper;

    public SensorMessageCreator(SensorDataDTO sensorData) {
        this.sensorData = sensorData;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    @Override
    public @NonNull Message createMessage(@NonNull Session session) throws JMSException {
        try {
            String jsonPayload = objectMapper.writeValueAsString(sensorData);
            return session.createTextMessage(jsonPayload);
        } catch (JsonProcessingException e) {
            JMSException jmsException = new JMSException("Failed to serialize SensorDataDTO to JSON");
            jmsException.setLinkedException(e);
            throw jmsException;
        }
    }
}