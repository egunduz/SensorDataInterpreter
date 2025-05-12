package com.demo.SensorDataInterpreter.controller;

import com.demo.SensorDataInterpreter.entity.MetricAlertEntity;
import com.demo.SensorDataInterpreter.repository.MetricAlertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Provides endpoints to retrieve generated alerts from db.
 * Normally I would use a service layer instead of direct use of repository
 * but for simplicity I am directly using the repository and entity objects.
 */
@RestController
@RequestMapping("/api/alerts")
@RequiredArgsConstructor
public class AlertsController {

    // for simplicity, I am directly using the repository
    private final MetricAlertRepository repository;

    // instead of returning entity directly, I would use a DTO but for simplicity I am keeping it simple
    // is it's not a part of the task
    @GetMapping
    public List<MetricAlertEntity> getAll() {
        return repository.findAll();
    }
}
