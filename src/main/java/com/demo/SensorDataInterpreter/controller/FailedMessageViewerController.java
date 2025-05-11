package com.demo.SensorDataInterpreter.controller;

import com.demo.SensorDataInterpreter.entity.FailedSensorMessageEntity;
import com.demo.SensorDataInterpreter.repository.FailedSensorMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing failed sensor messages.
 * Provides endpoints to retrieve and delete failed messages.
 * Normally I would use a service layer, but for simplicity
 * , I am directly using the repository here. I will fix this soon.
 */
@RestController
@RequestMapping("/api/failed-messages")
@RequiredArgsConstructor
public class FailedMessageViewerController {

    private final FailedSensorMessageRepository repository;

    @GetMapping
    public List<FailedSensorMessageEntity> getAll() {
        return repository.findAll();
    }
}

