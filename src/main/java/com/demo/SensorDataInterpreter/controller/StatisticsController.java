package com.demo.SensorDataInterpreter.controller;

import com.demo.SensorDataInterpreter.entity.SensorStatisticalEntity;
import com.demo.SensorDataInterpreter.repository.SensorStatisticalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Provides endpoints to retrieve sensor statistics.
 * Normally I would use a service layer instead of direct use of repository
 * but for simplicity I am directly using the repository here
 */
@RestController
@RequestMapping("/api/statistics")
@RequiredArgsConstructor
public class StatisticsController {

    private final SensorStatisticalRepository repository;

    @GetMapping
    public List<SensorStatisticalEntity> getAll() {
        return repository.findAll();
    }
}
