package com.demo.SensorDataInterpreter.repository;

import com.demo.SensorDataInterpreter.entity.FailedMessageEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FailedMessageRepository extends MongoRepository<FailedMessageEntity, String> {
}
