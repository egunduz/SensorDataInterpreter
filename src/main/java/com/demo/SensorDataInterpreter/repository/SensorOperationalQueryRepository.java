package com.demo.SensorDataInterpreter.repository;

import com.demo.SensorDataInterpreter.entity.SensorOperationalEntity;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SensorOperationalQueryRepository {
    @Query("{ 'statusChanges.deviceId': ?0, 'statusChanges.eventTime': { $gte: ?1, $lte: ?2 } }")
    List<SensorOperationalEntity> findLocationHistory(String deviceId, long startTime, long endTime);
}