package com.dekapx.weatherapp.service;

import com.dekapx.weatherapp.entity.SensorReading;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public final class SensorServiceImpl implements SensorService {

    @Override
    public SensorReading getReading(final String sensorId) {
        return buildResponse(sensorId);
    }

    @Override
    public List<SensorReading> getAllReadings() {
        return List.of(buildResponse(null));
    }

    @Override
    public SensorReading registerReading(final SensorReading sensorReading) {
        return buildResponse(sensorReading.getSensorId());
    }

    private SensorReading buildResponse(String sensorId) {
        return SensorReading.builder()
                .sensorId(Optional.ofNullable(sensorId).orElse("Sensor-1"))
                .temperature(24.5)
                .humidity(60.0)
                .windSpeed(15.0)
                .timestamp(java.time.LocalDateTime.now())
                .build();
    }
}
