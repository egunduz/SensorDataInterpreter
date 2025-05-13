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

    @GetMapping("/history/all")
    public List<LocationHistoryResponseDTO> getAll() {
        return service.getAllLocationHistory();
    }

    @GetMapping("/history")
    public List<LocationHistoryResponseDTO> get(@RequestParam String deviceId,
                                                @RequestParam String startDate,
                                                @RequestParam String endDate) {
        return service.getDeviceLocationHistory(new LocationHistoryRequestDTO(deviceId, startDate, endDate));
    }
}
