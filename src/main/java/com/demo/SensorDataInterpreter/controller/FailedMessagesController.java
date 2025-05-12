package com.demo.SensorDataInterpreter.controller;

import com.demo.SensorDataInterpreter.entity.FailedMessageEntity;
import com.demo.SensorDataInterpreter.service.FailedMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for listing failed/unprocessed sensor messages.
 */
@RestController
@RequestMapping("/api/failed-messages")
@RequiredArgsConstructor
public class FailedMessagesController {

    private final FailedMessageService service;

    /**
     * for simplicity, I am returning entity objects directly instead of DTOs.
     * Normally, we would use DTOs to avoid exposing internal data structures.
     * @return list of failed/unprocessed messages
     */
    @GetMapping
    public List<FailedMessageEntity> getAll() {
        return service.findAll();
    }
}

