package com.dekapx.weatherapp.service;

import com.dekapx.weatherapp.common.MetricType;
import com.dekapx.weatherapp.entity.SensorReading;
import com.dekapx.weatherapp.exception.ResourceNotFoundException;
import com.dekapx.weatherapp.repository.SensorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.dekapx.weatherapp.config.CacheConfig.SENSOR_CACHE;

@Slf4j
@Service
@Transactional
public class SensorServiceImpl implements SensorService {
    private static Map<String, MetricType> metricMap = Map.of(
            "TEMPERATURE", MetricType.TEMPERATURE,
            "HUMIDITY", MetricType.HUMIDITY,
            "WIND_SPEED", MetricType.WIND_SPEED);

    private SensorRepository sensorRepository;

    @Autowired
    public SensorServiceImpl(final SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    @Override
    @Cacheable(value = SENSOR_CACHE, key = "#sensorId")
    public List<SensorReading> getReadings(final String sensorId) {
        log.info("Fetching sensor reading for sensorId: [{}]", sensorId);
        List<SensorReading> sensorReadings = this.sensorRepository.findBySensorId(sensorId);
        return Optional.ofNullable(sensorReadings)
                .filter(readings -> !readings.isEmpty())
                .orElseThrow(() -> new ResourceNotFoundException("Sensors with id [" + sensorId + "] not found"));
    }

    @Override
    public List<SensorReading> getAllReadings() {
        log.info("Fetching all sensor readings...");
        List<SensorReading> sensorReadings = new ArrayList<>();
        this.sensorRepository.findAll().forEach(sensorReadings::add);
        return sensorReadings;
    }

    @Override
    @CacheEvict(value = SENSOR_CACHE, key = "#sensorReading.sensorId")
    public SensorReading registerReading(final SensorReading sensorReading) {
        log.info("Register sensor reading for Sensor ID : [{}]", sensorReading.getSensorId());
        return sensorRepository.save(sensorReading);
    }

    @Override
    public Double getAverageTemperatureByDateRange(LocalDateTime startTime, LocalDateTime endTime) {
        log.info("Fetching average temperature between [{}] and [{}]", startTime, endTime);
        return sensorRepository.findAverageTemperatureByDateRange(startTime, endTime);
    }

    @Override
    public Double getAverageMetricForSensor(String sensorId, String metric, LocalDateTime startTime, LocalDateTime endTime) {
        log.info("Fetching sensor readings for sensorId: [{}] between [{}] and [{}]", sensorId, startTime, endTime);
        List<SensorReading> sensorReadings = this.sensorRepository.findBySensorIdAndDateRange(sensorId, startTime, endTime);
        Optional.ofNullable(sensorReadings)
                .filter(readings -> !readings.isEmpty())
                .orElseThrow(() -> new ResourceNotFoundException("Sensors with id [" + sensorId + "] not found"));
        return calculateAverage(sensorReadings, getMetricType(metric));
    }

    private MetricType getMetricType(String metric) {
        return Optional.ofNullable(metricMap.get(metric))
                .orElseThrow(() -> new IllegalArgumentException("Invalid metric: " + metric + ". Valid metrics are TEMPERATURE, HUMIDITY, WIND_SPEED"));
    }

    private double calculateAverage(List<SensorReading> readings, MetricType metricType) {
        return readings.stream()
                .mapToDouble(reading -> {
                    switch (metricType) {
                        case TEMPERATURE: return reading.getTemperature();
                        case HUMIDITY: return reading.getHumidity();
                        case WIND_SPEED: return reading.getWindSpeed();
                        default: throw new IllegalArgumentException("Invalid metric: " + metricType);
                    }
                })
                .average()
                .orElse(Double.NaN);
    }
}
