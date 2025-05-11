package com.demo.SensorDataInterpreter.repository;

import com.demo.SensorDataInterpreter.entity.MetricThresholdEntity;
import com.demo.SensorDataInterpreter.enums.MetricType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MetricThresholdRepository extends JpaRepository<MetricThresholdEntity, Long> {
    Optional<MetricThresholdEntity> findByMetric(MetricType metric);
}
