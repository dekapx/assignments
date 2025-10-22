package com.dekapx.weatherapp.service;

import com.dekapx.weatherapp.entity.SensorReading;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

public interface SensorService {
    List<SensorReading> findBySensorId(String sensorId);

    List<SensorReading> findAll();
}
