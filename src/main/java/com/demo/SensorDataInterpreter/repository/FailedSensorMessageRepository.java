package com.demo.SensorDataInterpreter.repository;

import com.demo.SensorDataInterpreter.entity.FailedSensorMessageEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FailedSensorMessageRepository extends MongoRepository<FailedSensorMessageEntity, String> {
}
