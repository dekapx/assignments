package com.dekapx.weatherapp.service;

import com.dekapx.weatherapp.entity.SensorReading;

import java.time.LocalDateTime;
import java.util.List;

public interface SensorService {
    List<SensorReading> getReadings(String sensorId);

    List<SensorReading> getAllReadings();

    SensorReading registerReading(SensorReading sensorReading);

    Double getAverageTemperatureByDateRange(LocalDateTime start, LocalDateTime end);

    Double getAverageMetricForSensor(String sensorId, String metric, LocalDateTime startTime, LocalDateTime endTime);
}
