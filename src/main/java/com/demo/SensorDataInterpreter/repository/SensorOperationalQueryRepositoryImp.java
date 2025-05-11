package com.demo.SensorDataInterpreter.repository;

import com.demo.SensorDataInterpreter.entity.SensorOperationalEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class SensorOperationalQueryRepositoryImp implements SensorOperationalQueryRepository {
    private final static String collectionName = "sensor_operations";
    private final MongoTemplate mongoTemplate;

    @Override
    public List<SensorOperationalEntity> findLocationHistory(String deviceId, long from, long to) {
        MatchOperation match = Aggregation.match(Criteria.where("statusChanges")
                .elemMatch(Criteria.where("deviceId").is(deviceId)
                        .and("eventTime").gte(from).lte(to)));

        ProjectionOperation project = Aggregation.project("statusChanges");

        Aggregation aggregation = Aggregation.newAggregation(match, project);

        AggregationResults<SensorOperationalEntity> results =
                mongoTemplate.aggregate(aggregation, "sensor_operations", SensorOperationalEntity.class);

        return results.getMappedResults();
    }
}
