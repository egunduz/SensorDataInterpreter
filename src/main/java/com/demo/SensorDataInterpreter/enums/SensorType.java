package com.demo.SensorDataInterpreter.enums;

public enum SensorType {
    TEMPERATURE("Temperature"),
    PRESSURE("AirPressure"),
    HUMIDITY("Humidity"),
    LIGHT("Light"),
    BATTERY("Battery");

    private final String type;

    SensorType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
