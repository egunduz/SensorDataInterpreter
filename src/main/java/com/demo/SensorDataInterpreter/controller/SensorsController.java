package com.demo.SensorDataInterpreter.controller;

import com.demo.SensorDataInterpreter.dto.SensorDataDTO;
import com.demo.SensorDataInterpreter.messaging.SensorMessageProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

/**
 * Use this class to behave like a sensor and send events to centralized queue.
 */
@RestController
@RequestMapping("/api/sensors")
public class SensorsController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final SensorMessageProducer sensorMessageProducer;

    public SensorsController(SensorMessageProducer sensorMessageProducer) {
        this.sensorMessageProducer = sensorMessageProducer;
    }

    @GetMapping()
    public String greeting() {
        return "Use POST method to send your data.";
    }

    @PostMapping()
    public String produce(@RequestBody SensorDataDTO sensorDataDTO) {
        sensorMessageProducer.send(sensorDataDTO);
        return "Sensor data produced!";
    }
}
