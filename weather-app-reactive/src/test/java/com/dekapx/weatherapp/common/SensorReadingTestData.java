package com.dekapx.weatherapp.common;

import com.dekapx.weatherapp.entity.SensorReading;


public class SensorReadingTestData {
    public static final String SENSOR_ID = "Sensor-X";
    public static final double TEMPERATURE = 25.5;
    public static final double HUMIDITY = 60.0;
    public static final double WIND_SPEED = 15.0;

    public static SensorReading buildSensorReading() {
        return SensorReading.builder()
                .sensorId(SENSOR_ID)
                .temperature(25.5)
                .humidity(60.0)
                .windSpeed(15.0)
                .timestamp(java.time.LocalDateTime.now())
                .build();
    }
}
