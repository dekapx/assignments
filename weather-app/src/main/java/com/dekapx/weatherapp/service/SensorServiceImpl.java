package com.dekapx.weatherapp.service;

import com.dekapx.weatherapp.entity.SensorReading;
import io.swagger.v3.oas.annotations.servers.Server;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Server
@RequiredArgsConstructor
public final class SensorServiceImpl implements SensorService {

    @Override
    public SensorReading getReading(final String sensorId) {
        return null;
    }

    @Override
    public List<SensorReading> getAllReadings() {
        return List.of();
    }

    @Override
    public SensorReading registerReading(final SensorReading sensorReading) {
        return null;
    }
}
