package com.demo.SensorDataInterpreter.config;

import com.demo.SensorDataInterpreter.entity.MetricThresholdEntity;
import com.demo.SensorDataInterpreter.enums.MetricChangeDirection;
import com.demo.SensorDataInterpreter.enums.MetricThresholdType;
import com.demo.SensorDataInterpreter.enums.MetricType;
import com.demo.SensorDataInterpreter.repository.MetricThresholdRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * In the application we need to have some default thresholds for the metric alert generation.
 * For simplicity, I am using the H2 database for development and testing.
 * If the database is empty, it will populate it with the following thresholds:
 * The thresholds are saved h2 database table named metric_thresholds.
 * Use http://localhost:8080/h2-console to access the H2 console.
 */
@Configuration
@RequiredArgsConstructor
@Slf4j
public class DataInitializer {

    private final MetricThresholdRepository thresholdRepository;

    @Bean
    public CommandLineRunner initMetricThresholds() {
        return args -> {
            try {
                if (thresholdRepository.count() == 0) {
                    List<MetricThresholdEntity> thresholds = List.of(
                            // Batter charge should be between 0 and 100
                            new MetricThresholdEntity(null, MetricType.BATTERY_CHARGE, MetricChangeDirection.NONE, 0, 0.0, 10.0, 100.0, MetricThresholdType.SCALAR),
                            // Battery voltage change should be between 5 and 10
                            new MetricThresholdEntity(null, MetricType.BATTERY_VOLTAGE, MetricChangeDirection.BOTH, 5, 10.0, 0.0, 220.0, MetricThresholdType.PERCENTAGE),
                            // Temperature should be between -4 and 80
                            new MetricThresholdEntity(null, MetricType.TEMPERATURE, MetricChangeDirection.BOTH, 0, 20.0, -4.0, 80.0, MetricThresholdType.SCALAR),
                            // Humidity should be between 0 and 100
                            new MetricThresholdEntity(null, MetricType.HUMIDITY, MetricChangeDirection.INCREASE, 5, 0.0, 0.0, 100.0, MetricThresholdType.PERCENTAGE),
                            // Light level should be between 0 and 10000
                            new MetricThresholdEntity(null, MetricType.LIGHT_LEVEL, MetricChangeDirection.DECREASE, 5, 0.0, 100.0, 10000.0, MetricThresholdType.SCALAR),
                            // Air pressure should be between 1 and 3
                            new MetricThresholdEntity(null, MetricType.AIR_PRESSURE, MetricChangeDirection.BOTH, 0, 0.0, 1.0, 3.0, MetricThresholdType.SCALAR)
                    );
                    thresholdRepository.saveAll(thresholds);
                }
            } catch (Exception e) {
                log.error("Could not initialize metric thresholds. Please check metric_thresholds table. {}", e.getMessage());
            }
        };
    }
}
