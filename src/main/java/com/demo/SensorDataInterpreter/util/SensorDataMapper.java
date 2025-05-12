package com.demo.SensorDataInterpreter.util;

import com.demo.SensorDataInterpreter.dto.SensorDataDTO;
import com.demo.SensorDataInterpreter.entity.SensorMetricsEntity;
import com.demo.SensorDataInterpreter.entity.SensorStatusChangeEntity;
import com.demo.SensorDataInterpreter.entity.SensorStatusChangeEntity.EventLocation;
import com.demo.SensorDataInterpreter.entity.SensorStatusChangeEntity.Geometry;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Utility class for mapping SensorDataDTO to SensorStatisticalEntity and SensorOperationalEntity.
 */
public class SensorDataMapper {

    /**
     * Converts a SensorDataDTO to a SensorStatisticalEntity.
     *
     * @param dto the SensorDataDTO to convert
     * @return the converted SensorStatisticalEntity
     */
    public static SensorMetricsEntity toStatisticsEntity(SensorDataDTO dto) {
        SensorMetricsEntity entity = new SensorMetricsEntity();
        entity.setType(dto.getType());
        entity.setTemperature(dto.getTemperature());
        entity.setAirPressure(dto.getAirPressure());
        entity.setHumidity(dto.getHumidity());
        entity.setLightLevel(dto.getLightLevel());
        entity.setBatteryCharge(dto.getBatteryCharge());
        entity.setBatteryVoltage(dto.getBatteryVoltage());
        entity.setTimestamp(LocalDateTime.now());
        return entity;
    }

    /**
     * Converts Status changes to SensorStatusChangeEntity
     *
     * @param dto the SensorDataDTO to convert
     * @return a list of SensorStatusChangeEntity
     */
    public static List<SensorStatusChangeEntity> toStatisticalEntity(SensorDataDTO dto) {
        List<SensorStatusChangeEntity> entities = new ArrayList<>();
        if (dto.getStatusChanges() != null) {
            // map each status change to SensorStatusChangeEntity
            return dto.getStatusChanges().stream().map(statusDto -> {

                SensorStatusChangeEntity statusEntity = new SensorStatusChangeEntity();
                statusEntity.setDeviceId(statusDto.getDeviceId());
                statusEntity.setVehicleId(statusDto.getVehicleId());
                statusEntity.setVehicleType(statusDto.getVehicleType());
                statusEntity.setPropulsionType(statusDto.getPropulsionType());
                statusEntity.setEventType(statusDto.getEventType());
                statusEntity.setEventTypeReason(statusDto.getEventTypeReason());
                statusEntity.setEventTime(statusDto.getEventTime());

                // event location to entity
                var geoEntity = new Geometry();
                // null check for geometry info
                if (statusDto.getEventLocation() != null
                        && statusDto.getEventLocation().getGeometry() != null) {
                    var geoDto = statusDto.getEventLocation().getGeometry();
                    geoEntity.setType(geoDto.getType());
                    geoEntity.setCoordinates(geoDto.getCoordinates());
                }
                var loc = new EventLocation();
                loc.setGeometry(geoEntity);
                statusEntity.setEventLocation(loc);

                return statusEntity;
            }).collect(Collectors.toList());
        }
        else
            return Collections.emptyList();
    }
}
