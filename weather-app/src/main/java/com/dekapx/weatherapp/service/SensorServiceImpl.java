package com.dekapx.weatherapp.service;

import com.dekapx.weatherapp.entity.SensorReading;
import com.dekapx.weatherapp.exception.ResourceNotFoundException;
import com.dekapx.weatherapp.repository.SensorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public final class SensorServiceImpl implements SensorService {
    private SensorRepository sensorRepository;

    @Autowired
    public SensorServiceImpl(final SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    @Override
    public SensorReading getReading(final String sensorId) {
        log.info("Fetching sensor reading for sensorId: [{}]", sensorId);
        return Optional.ofNullable(this.sensorRepository.findBySensorId(sensorId))
                .orElseThrow(() -> new ResourceNotFoundException("Sensor with id [" + sensorId + "] not found"));
    }

    @Override
    public List<SensorReading> getAllReadings() {
        log.info("Fetching all sensor readings...");
        List<SensorReading> sensorReadings = new ArrayList<>();
        this.sensorRepository.findAll().forEach(sensorReadings::add);
        return sensorReadings;
    }

    @Override
    public SensorReading registerReading(final SensorReading sensorReading) {
        log.info("Register sensor reading for Sensor ID : [{}]", sensorReading.getSensorId());
        return sensorRepository.save(sensorReading);
    }

    @Override
    public Double getAverageTemperatureByDateRange(LocalDateTime startTime, LocalDateTime endTime) {
        return sensorRepository.findAverageTemperatureByDateRange(startTime, endTime);
    }

    @Override
    public List<SensorReading> getReadingsBySensorIdAndDateRange(String sensorId, LocalDateTime startTime, LocalDateTime endTime) {
        log.info("Fetching sensor readings for sensorId: [{}] between [{}] and [{}]", sensorId, startTime, endTime);
        List<SensorReading> sensorReadings = this.sensorRepository.findBySensorIdAndDateRange(sensorId, startTime, endTime);
        return sensorReadings;
    }
}
