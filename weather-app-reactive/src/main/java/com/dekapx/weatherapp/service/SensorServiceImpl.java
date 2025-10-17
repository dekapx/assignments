package com.dekapx.weatherapp.service;

import com.dekapx.weatherapp.entity.SensorReading;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class SensorServiceImpl implements SensorService {

    @Override
    public SensorReading getReadings(String sensorId) {
        SensorReading sensorReading = buildSensorReading(sensorId);
        return sensorReading;
    }

    @Override
    public List<SensorReading> getAllReadings() {
        return List.of(buildSensorReading("sensor-123"));
    }

    private static SensorReading buildSensorReading(String sensorId) {
        return SensorReading.builder()
                .sensorId(sensorId)
                .temperature(25.5)
                .humidity(60.0)
                .windSpeed(15.0)
                .timestamp(java.time.LocalDateTime.now())
                .build();
    }
}
