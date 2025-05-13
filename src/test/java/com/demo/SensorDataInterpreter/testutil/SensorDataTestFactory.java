package com.demo.SensorDataInterpreter.testutil;

import com.demo.SensorDataInterpreter.dto.SensorDataDTO;
import com.demo.SensorDataInterpreter.dto.SensorEventGeometryDTO;
import com.demo.SensorDataInterpreter.dto.SensorEventLocationDTO;
import com.demo.SensorDataInterpreter.dto.SensorStatusChangeDTO;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * mock data generator for SensorDataDTO
 */
public class SensorDataTestFactory {

    private static final Random random = new Random();

    public static SensorDataDTO generateRandomSensorData() {
        SensorDataDTO sensor = new SensorDataDTO();
        sensor.setId(String.valueOf(random.nextInt(1000)));
        sensor.setType("HUD" + (20 + random.nextInt(10)));
        sensor.setTemperature(round(random.nextDouble() * 40, 2));
        sensor.setAirPressure(round(980 + random.nextDouble() * 40, 2));
        sensor.setHumidity(round(random.nextDouble() * 100, 2));
        sensor.setLightLevel(round(random.nextDouble() * 1000, 2));
        sensor.setBatteryCharge(round(random.nextDouble() * 100, 2));
        sensor.setBatteryVoltage(round(20 + random.nextDouble() * 10, 2));

        SensorStatusChangeDTO statusChange = new SensorStatusChangeDTO();
        statusChange.setDeviceId("device-" + random.nextInt(100));
        statusChange.setVehicleId("vehicle-" + random.nextInt(100));
        statusChange.setVehicleType("car");
        statusChange.setPropulsionType(List.of("electric"));
        statusChange.setEventType("status_update");
        statusChange.setEventTypeReason("normal_operation");
        statusChange.setEventTime(Instant.now().getEpochSecond());

        SensorEventGeometryDTO geometry = new SensorEventGeometryDTO();
        geometry.setType("Point");
        geometry.setCoordinates(List.of(
                round(random.nextDouble() * 180 - 90, 6),
                round(random.nextDouble() * 360 - 180, 6)
        ));

        SensorEventLocationDTO eventLocation = new SensorEventLocationDTO();
        eventLocation.setGeometry(geometry);

        statusChange.setEventLocation(eventLocation);

        sensor.setStatusChanges(Collections.singletonList(statusChange));
        return sensor;
    }

    private static double round(double value, int places) {
        double factor = Math.pow(10, places);
        return Math.round(value * factor) / factor;
    }
}

