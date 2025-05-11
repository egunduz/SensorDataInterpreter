package com.demo.SensorDataInterpreter.processor;

import com.demo.SensorDataInterpreter.common.OperationResult;
import com.demo.SensorDataInterpreter.dto.SensorDataDTO;

public interface SensorDataProcessor {
    /**
     * Processes the given sensor data.
     *
     * @param sensorData the sensor data to process
     * @return the result of the operation
     */
    OperationResult process(SensorDataDTO sensorData);
}
