package com.demo.SensorDataInterpreter.messaging;

import com.demo.SensorDataInterpreter.dto.SensorDataDTO;
import com.demo.SensorDataInterpreter.util.SensorJsonConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

/**
 * This class is responsible for sending sensor data messages to a JMS queue.
 */
@Service
public class SensorMessageProducer {

    private final JmsTemplate jmsTemplate;
    private final String queueName;

    public SensorMessageProducer(JmsTemplate jmsTemplate,
                                 @Value("${sensor.queue.name}") String queueName) {
        this.jmsTemplate = jmsTemplate;
        this.queueName = queueName;
    }

    public void send(SensorDataDTO data) {
        try {
            String json = SensorJsonConverter.toJson(data);
            jmsTemplate.convertAndSend(queueName, json);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send sensor message to the queue", e);
        }
    }
}

