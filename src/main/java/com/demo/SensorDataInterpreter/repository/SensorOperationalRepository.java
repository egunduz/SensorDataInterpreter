package com.demo.SensorDataInterpreter.repository;

import com.demo.SensorDataInterpreter.entity.SensorOperationalEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SensorOperationalRepository extends MongoRepository<SensorOperationalEntity, String>, SensorOperationalQueryRepository {
}