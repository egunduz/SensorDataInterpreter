package com.demo.SensorDataInterpreter.util;

import com.demo.SensorDataInterpreter.dto.LocationHistoryResponseDTO;
import com.demo.SensorDataInterpreter.entity.SensorOperationalEntity;

/**
 * Utility class to convert SensorOperationalEntity to LocationHistoryResponseDTO.
 */
public class StatusChangeToLocationHistoryMapper {
    public static LocationHistoryResponseDTO map(SensorOperationalEntity.StatusChange statusChange) {
        var eventLocation = statusChange.getEventLocation();
        var geometry = eventLocation != null ? eventLocation.getGeometry() : null;
        var coordinates = geometry != null ? geometry.getCoordinates() : null;
        var type = geometry != null ? geometry.getType() : null;

        return new LocationHistoryResponseDTO(
                statusChange.getDeviceId(),
                DateTimeConverter.fromSeconds(statusChange.getEventTime()),
                new LocationHistoryResponseDTO.EventLocation(
                        new LocationHistoryResponseDTO.Geometry(
                                type,
                                coordinates
                        )
                )
        );
    }
}
