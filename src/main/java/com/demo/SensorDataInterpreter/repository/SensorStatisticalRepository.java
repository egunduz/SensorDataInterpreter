package com.demo.SensorDataInterpreter.repository;

import com.demo.SensorDataInterpreter.entity.SensorStatisticalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SensorStatisticalRepository extends JpaRepository<SensorStatisticalEntity, Long> {
    @Query("SELECT e FROM SensorStatisticalEntity e ORDER BY e.timestamp DESC LIMIT 1")
    SensorStatisticalEntity findLatest();
}
