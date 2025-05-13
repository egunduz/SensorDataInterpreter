package com.demo.SensorDataInterpreter.service;

import com.demo.SensorDataInterpreter.common.OperationResult;
import com.demo.SensorDataInterpreter.dto.LocationHistoryRequestDTO;
import com.demo.SensorDataInterpreter.dto.LocationHistoryResponseDTO;
import com.demo.SensorDataInterpreter.dto.SensorDataDTO;
import com.demo.SensorDataInterpreter.entity.SensorStatusChangeEntity;
import com.demo.SensorDataInterpreter.repository.SensorStatusChangeRepository;
import com.demo.SensorDataInterpreter.testutil.SensorDataTestFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StatisticalDataServiceTest {

    private StatisticalDataService statisticalDataService;
    private SensorStatusChangeRepository mockRepository;

    @BeforeEach
    void setUp() {
        // Mock the repository
        mockRepository = Mockito.mock(SensorStatusChangeRepository.class);
        // Initialize the service with the mocked repository
        statisticalDataService = new StatisticalDataService(mockRepository);
    }

    @Test
    void testProcess() {
        // Generate mock SensorDataDTO
        SensorDataDTO mockSensorData = SensorDataTestFactory.generateRandomSensorData();

        // Call the process method
        OperationResult result = statisticalDataService.process(mockSensorData);

        // Assert that the operation result is successful
        assertTrue(result.isSuccess());
    }

    @Test
    void testGetDeviceLocationHistory() {
        // Mock repository response
        String deviceId = "device-1";
        long from = Instant.now().minusSeconds(3600).getEpochSecond();
        long to = Instant.now().getEpochSecond();
        List<SensorStatusChangeEntity> mockEntities = List.of(new SensorStatusChangeEntity());
        when(mockRepository.findLocationHistory(deviceId, from, to)).thenReturn(mockEntities);

        // Create a request DTO
        LocationHistoryRequestDTO requestDTO = new LocationHistoryRequestDTO();
        requestDTO.setDeviceId(deviceId);
        requestDTO.setFrom(String.valueOf(Instant.ofEpochSecond(from)));
        requestDTO.setTo(String.valueOf(Instant.ofEpochSecond(to)));

        // Call the method
        List<LocationHistoryResponseDTO> response = statisticalDataService.getDeviceLocationHistory(requestDTO);

        // Verify repository interaction
        verify(mockRepository, times(1)).findLocationHistory(deviceId, from, to);

        // Assert the response
        assertNotNull(response);
    }

    @Test
    void testGetAllLocationHistory() {
        // Mock repository response
        List<SensorStatusChangeEntity> mockEntities = List.of(new SensorStatusChangeEntity());
        when(mockRepository.findAll()).thenReturn(mockEntities);

        // Call the method
        List<LocationHistoryResponseDTO> response = statisticalDataService.getAllLocationHistory();

        // Verify repository interaction
        verify(mockRepository, times(1)).findAll();

        // Assert the response
        assertNotNull(response);
    }
}