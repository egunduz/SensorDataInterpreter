package com.demo.SensorDataInterpreter.repository;

import com.demo.SensorDataInterpreter.entity.SensorStatusChangeEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SensorStatusChangeRepository extends MongoRepository<SensorStatusChangeEntity, String>{

    /**
     * Finds status changes for a given device ID within a specified time range.
     *
     * @param deviceId the device ID
     * @param startTime the start time (inclusive)
     * @param endTime the end time (inclusive)
     * @return a list of objects representing the status changes
     */
    @Query("{ 'deviceId': ?0, 'eventTime': { $gte: ?1, $lte: ?2 } }")
    List<SensorStatusChangeEntity> findLocationHistory(String deviceId, long startTime, long endTime);
}