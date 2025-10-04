package com.dekapx.weatherapp.service;

import com.dekapx.weatherapp.entity.SensorReading;

import java.util.List;

public interface SensorService {
    SensorReading getReading(String sensorId);

    List<SensorReading> getAllReadings();

    SensorReading registerReading(SensorReading sensorReading);
}
