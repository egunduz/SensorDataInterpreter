package com.demo.SensorDataInterpreter.util;

import org.junit.jupiter.api.Test;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class DateTimeConverterTest {

    @Test
    void whenValidInstantString_thenConvertToSeconds() {
        String isoDate = "2025-05-10T12:30:00Z";
        long expected = Instant.parse(isoDate).getEpochSecond();

        long result = DateTimeConverter.toSeconds(isoDate);

        assertEquals(expected, result);
    }

    @Test
    void whenValidLocalDateTimeString_thenConvertToSeconds() {
        String localDate = "2025-05-12T12:30:00";
        long result = DateTimeConverter.toSeconds(localDate);

        assertTrue(result > 0);
    }

    @Test
    void whenInvalidDateString_thenThrowException() {
        String invalidDate = "invalid-date";

        assertThrows(IllegalArgumentException.class, () ->
                DateTimeConverter.toSeconds(invalidDate));
    }

    @Test
    void whenNegativeSeconds_thenConvertToISOString() {
        long negativeSeconds = -1710931800;

        String result = DateTimeConverter.fromSeconds(negativeSeconds);

        assertNotNull(result);
        assertTrue(result.contains("T"));
        assertTrue(result.endsWith("Z"));
    }

    @Test
    void whenValidLocalDateTime_thenConvertToSeconds() {
        String localDateTime = "2025-05-11T12:30:00";

        long result = DateTimeConverter.fromLocalTime(localDateTime);

        assertTrue(result > 0);
    }

    @Test
    void whenInvalidLocalDateTime_thenThrowException() {
        String invalidDateTime = "2025-05-40T12:30:00";

        assertThrows(IllegalArgumentException.class, () ->
                DateTimeConverter.fromLocalTime(invalidDateTime));
    }
}