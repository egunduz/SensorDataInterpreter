package com.demo.SensorDataInterpreter.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Map;

/**
 * Entity class representing a failed sensor message.
 * This class is used to store information about messages that failed to be processed,
 * including the reason for failure, the raw payload, and any extracted data if applicable.
 * We will later use this information to retry processing the message.
 */
@Document(collection = "failed_sensor_messages")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FailedMessageEntity {

    @Id
    private String id;
    private String failureType; // failure step: "DESERIALIZATION_ERROR", "PROCESSING_ERROR"
    private String failureReason; // exception message or error description
    private String rawPayload; // original JSON string if deserialization failed
    private int retryCount; // number of retries

    private Map<String, Object> extractedData; // structured data if deserialization succeeded
    private Instant failedAt; // timestamp
}
