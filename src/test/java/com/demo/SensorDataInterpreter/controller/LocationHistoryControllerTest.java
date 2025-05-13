package com.demo.SensorDataInterpreter.controller;

import com.demo.SensorDataInterpreter.dto.LocationHistoryRequestDTO;
import com.demo.SensorDataInterpreter.dto.LocationHistoryResponseDTO;
import com.demo.SensorDataInterpreter.service.StatisticalDataService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LocationHistoryControllerTest {

    @Mock
    private StatisticalDataService service;

    @InjectMocks
    private LocationHistoryController controller;

    @Test
    void getAll_returnsEmptyListWhenNoLocationHistoryExists() {
        when(service.getAllLocationHistory()).thenReturn(Collections.emptyList());

        List<LocationHistoryResponseDTO> result = controller.getAll();

        assertTrue(result.isEmpty());
    }

    @Test
    void get_withValidParamsAndNoData_returnsEmptyList() {
        String deviceId = "device1";
        String startDate = "2025-05-01";
        String endDate = "2025-05-30";
        when(service.getDeviceLocationHistory(any(LocationHistoryRequestDTO.class))).thenReturn(Collections.emptyList());

        List<LocationHistoryResponseDTO> result = controller.get(deviceId, startDate, endDate);

        assertTrue(result.isEmpty());
    }
}