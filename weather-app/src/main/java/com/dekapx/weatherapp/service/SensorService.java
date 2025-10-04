package com.dekapx.weatherapp.service;

import com.dekapx.weatherapp.entity.SensorReading;

import java.util.List;

public sealed interface SensorService permits SensorServiceImpl {
    SensorReading getReading(String sensorId);

    List<SensorReading> getAllReadings();

    SensorReading registerReading(SensorReading sensorReading);
}
