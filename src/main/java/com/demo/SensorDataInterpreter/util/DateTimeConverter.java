package com.demo.SensorDataInterpreter.util;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Utility class for converting between ISO date strings and epoch seconds.
 */
public class DateTimeConverter {

    public static long toSeconds(String isoDateString) {
        try {
            return Instant.parse(isoDateString).getEpochSecond();
        } catch (DateTimeParseException e) {
            return fromLocalTime(isoDateString);
        }
    }

    public static String fromSeconds(long dateInSeconds) {
        try {
            return Instant.ofEpochSecond(dateInSeconds)
                    .atZone(ZoneOffset.UTC)
                    .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        } catch (DateTimeException e) {
            throw new IllegalArgumentException("Could not convert instant to ISO date: " + dateInSeconds, e);
        }
    }

    public static long fromLocalTime(String date) {
        try {
            // Convert to Instant (assuming system default timezone)
            return LocalDateTime.parse(date).atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();
        } catch (DateTimeException e) {
            throw new IllegalArgumentException("Invalid Date format: " + date, e);
        }
    }
}