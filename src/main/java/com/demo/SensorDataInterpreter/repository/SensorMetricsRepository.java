package com.demo.SensorDataInterpreter.repository;

import com.demo.SensorDataInterpreter.entity.SensorMetricsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SensorMetricsRepository extends JpaRepository<SensorMetricsEntity, Long> {
    @Query("SELECT e FROM SensorMetricsEntity e ORDER BY e.timestamp DESC LIMIT 1")
    SensorMetricsEntity findLatest();
}
