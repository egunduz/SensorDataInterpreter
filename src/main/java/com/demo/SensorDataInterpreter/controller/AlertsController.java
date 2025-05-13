package com.demo.SensorDataInterpreter.controller;

import com.demo.SensorDataInterpreter.entity.MetricAlertEntity;
import com.demo.SensorDataInterpreter.repository.MetricAlertRepository;
import com.demo.SensorDataInterpreter.service.MetricAlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Provides endpoints to retrieve generated alerts from db.
 */
@RestController
@RequestMapping("/api/alerts")
@RequiredArgsConstructor
public class AlertsController {

    private final MetricAlertService service;

    @GetMapping
    public List<MetricAlertEntity> getAll() {
        return service.getAll();
    }
}
