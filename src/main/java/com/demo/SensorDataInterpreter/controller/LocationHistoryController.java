package com.demo.SensorDataInterpreter.controller;

import com.demo.SensorDataInterpreter.dto.LocationHistoryRequestDTO;
import com.demo.SensorDataInterpreter.dto.LocationHistoryResponseDTO;
import com.demo.SensorDataInterpreter.service.StatisticalDataService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Provides endpoints to retrieve location history of a given device.
 */
@RestController
@RequestMapping("/api/locations")
@RequiredArgsConstructor
@Validated
public class LocationHistoryController {

    private final StatisticalDataService service;

    @PostMapping("/history")
    public List<LocationHistoryResponseDTO> get(@Valid @RequestBody LocationHistoryRequestDTO requestDTO) {
        return service.getDeviceLocationHistory(requestDTO);
    }
}
