package com.demo.SensorDataInterpreter.service;

import com.demo.SensorDataInterpreter.common.OperationResult;
import com.demo.SensorDataInterpreter.dto.MetricHealthResult;
import com.demo.SensorDataInterpreter.dto.SensorDataDTO;
import com.demo.SensorDataInterpreter.entity.MetricAlertEntity;
import com.demo.SensorDataInterpreter.entity.MetricThresholdEntity;
import com.demo.SensorDataInterpreter.entity.SensorMetricsEntity;
import com.demo.SensorDataInterpreter.enums.MetricThresholdType;
import com.demo.SensorDataInterpreter.enums.MetricType;
import com.demo.SensorDataInterpreter.repository.MetricThresholdRepository;
import com.demo.SensorDataInterpreter.repository.SensorMetricsRepository;
import com.demo.SensorDataInterpreter.testutil.SensorDataTestFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OperationalDataServiceTest {

    private OperationalDataService operationalDataService;
    private SensorMetricsRepository mockSensorMetricsRepository;
    private MetricThresholdRepository mockThresholdRepository;
    private MetricAlertService mockAlertService;

    @BeforeEach
    void setUp() {
        // Mock dependencies
        mockSensorMetricsRepository = Mockito.mock(SensorMetricsRepository.class);
        mockThresholdRepository = Mockito.mock(MetricThresholdRepository.class);
        mockAlertService = Mockito.mock(MetricAlertService.class);

        // Initialize the service with mocked dependencies
        operationalDataService = new OperationalDataService(
                mockSensorMetricsRepository,
                mockThresholdRepository,
                mockAlertService
        );
    }

    @Test
    void testProcess_whenBatteryChargeUnderLimits() {
        // Mock threshold entity for battery charge limits
        MetricThresholdEntity mockThreshold = new MetricThresholdEntity();
        mockThreshold.setMetric(MetricType.BATTERY_CHARGE);
        mockThreshold.setMinValue(10.0);
        mockThreshold.setMaxValue(100.0);
        when(mockThresholdRepository.findAll()).thenReturn(Collections.singletonList(mockThreshold));

        // Generate mock SensorDataDTO
        // with battery charge under limits
        SensorDataDTO mockSensorData = SensorDataTestFactory.generateRandomSensorData();
        mockSensorData.setBatteryCharge(0.0); // low value to trigger alert

        // Mock repository to return a recent record within the last 5 minutes
        SensorMetricsEntity oldMetric = new SensorMetricsEntity();
        oldMetric.setBatteryCharge(100.0);
        mockThreshold.setTimeWindowMinutes(5);
        oldMetric.setTimestamp(LocalDateTime.now().minusMinutes(1));
        when(mockSensorMetricsRepository.findLatest()).thenReturn(oldMetric);

        // Call the process method
        OperationResult result = operationalDataService.process(mockSensorData);

        // Verify interactions
        verify(mockSensorMetricsRepository, times(1)).findLatest();
        verify(mockThresholdRepository, times(1)).findAll();
        verify(mockAlertService, times(1)).save(any(MetricAlertEntity.class)); // Alert should be triggered
        verify(mockSensorMetricsRepository, times(1)).save(any(SensorMetricsEntity.class));

        // Assert the result
        assertTrue(result.isSuccess());
    }

    @Test
    void testProcess_whenVoltageChangeOverThreshold() {
        // Mock threshold entity for battery charge limits
        MetricThresholdEntity mockThreshold = new MetricThresholdEntity();
        mockThreshold.setMetric(MetricType.BATTERY_VOLTAGE);
        mockThreshold.setThresholdType(MetricThresholdType.PERCENTAGE);
        mockThreshold.setTimeWindowMinutes(5); //
        mockThreshold.setChangeThreshold(10.0); // 10% change
        when(mockThresholdRepository.findAll()).thenReturn(Collections.singletonList(mockThreshold));


        // Generate mock SensorDataDTO
        // with battery voltage under threshold
        SensorDataDTO mockSensorData = SensorDataTestFactory.generateRandomSensorData();
        mockSensorData.setBatteryVoltage(0.0); // low value to trigger alert

        // Mock repository so that change is over the threshold
        SensorMetricsEntity oldMetric = new SensorMetricsEntity();
        oldMetric.setBatteryVoltage(100.0);
        oldMetric.setTimestamp(LocalDateTime.now().minusMinutes(6));
        when(mockSensorMetricsRepository.findLatest()).thenReturn(oldMetric);

        // Call the process method
        OperationResult result = operationalDataService.process(mockSensorData);

        // Verify interactions
        verify(mockSensorMetricsRepository, times(1)).findLatest();
        verify(mockThresholdRepository, times(1)).findAll();
        verify(mockAlertService, times(1)).save(any(MetricAlertEntity.class)); // Alert should be triggered
        verify(mockSensorMetricsRepository, times(1)).save(any(SensorMetricsEntity.class));

        // Assert the result
        assertTrue(result.isSuccess());
    }

    @Test
    void testProcess_whenVoltageChangeOverThresholdButTimeWindowNot() {
        // Mock threshold entity for battery charge limits
        MetricThresholdEntity mockThreshold = new MetricThresholdEntity();
        mockThreshold.setMetric(MetricType.BATTERY_VOLTAGE);
        mockThreshold.setThresholdType(MetricThresholdType.PERCENTAGE);
        mockThreshold.setTimeWindowMinutes(10); // time window of 10 minutes
        mockThreshold.setChangeThreshold(10.0); // 10% change
        when(mockThresholdRepository.findAll()).thenReturn(Collections.singletonList(mockThreshold));


        // Generate mock SensorDataDTO
        // with battery voltage under threshold
        SensorDataDTO mockSensorData = SensorDataTestFactory.generateRandomSensorData();
        mockSensorData.setBatteryVoltage(0.0); // low value to trigger alert

        // Mock repository so that change is over the threshold
        SensorMetricsEntity oldMetric = new SensorMetricsEntity();
        oldMetric.setBatteryVoltage(100.0);
        oldMetric.setTimestamp(LocalDateTime.now().minusMinutes(6));
        when(mockSensorMetricsRepository.findLatest()).thenReturn(oldMetric);

        // Call the process method
        OperationResult result = operationalDataService.process(mockSensorData);

        // Verify interactions
        verify(mockSensorMetricsRepository, times(1)).findLatest();
        verify(mockThresholdRepository, times(1)).findAll();
        verify(mockAlertService, times(0)).save(any(MetricAlertEntity.class)); // Alert should NOT be triggered
        verify(mockSensorMetricsRepository, times(1)).save(any(SensorMetricsEntity.class));

        // Assert the result
        assertTrue(result.isSuccess());
    }

    @Test
    void testProcess_Failure() {
        // Generate mock SensorDataDTO
        SensorDataDTO mockSensorData = SensorDataTestFactory.generateRandomSensorData();

        // Simulate an exception in the repository
        when(mockSensorMetricsRepository.findLatest()).thenThrow(new RuntimeException("Database error"));

        // Call the process method
        OperationResult result = operationalDataService.process(mockSensorData);

        // Verify interactions
        verify(mockSensorMetricsRepository, times(1)).findLatest();
        verify(mockThresholdRepository, never()).findAll();
        verify(mockAlertService, never()).save(any(MetricAlertEntity.class));
        verify(mockSensorMetricsRepository, never()).save(any(SensorMetricsEntity.class));

        // Assert the result
        assertFalse(result.isSuccess());
        assertEquals("Operational data process failed: Database error", result.getMessage());
    }
}