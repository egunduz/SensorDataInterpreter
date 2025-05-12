package com.demo.SensorDataInterpreter.controller;

import com.demo.SensorDataInterpreter.entity.SensorMetricsEntity;
import com.demo.SensorDataInterpreter.repository.SensorMetricsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Provides endpoints to retrieve metrics from db.
 * Normally I would use a service layer instead of direct use of repository
 * but for simplicity I am directly using the repository here
 */
@RestController
@RequestMapping("/api/metrics")
@RequiredArgsConstructor
public class MetricsController {

    // for simplicity, I am directly using the repository
    private final SensorMetricsRepository repository;

    // instead of returning entity directly, I would use a DTO but for simplicity I am keeping it simple
    // is it's not a part of the task
    @GetMapping
    public List<SensorMetricsEntity> getAll() {
        return repository.findAll();
    }
}
