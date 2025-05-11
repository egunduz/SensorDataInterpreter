package com.demo.SensorDataInterpreter.repository;

import com.demo.SensorDataInterpreter.entity.MetricAlertEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MetricAlertRepository extends JpaRepository<MetricAlertEntity, Integer> {
}
