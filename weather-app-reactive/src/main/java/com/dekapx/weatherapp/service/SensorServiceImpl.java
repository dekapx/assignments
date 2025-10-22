package com.dekapx.weatherapp.service;

import com.dekapx.weatherapp.entity.SensorReading;
import com.dekapx.weatherapp.repository.SensorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class SensorServiceImpl implements SensorService {
    private SensorRepository sensorRepository;

    @Autowired
    public SensorServiceImpl(final SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    @Override
    public List<SensorReading> findBySensorId(String sensorId) {
        log.info("Fetching sensor reading for sensorId: [{}]", sensorId);
        List<SensorReading> sensorReadings = this.sensorRepository.findBySensorId(sensorId);
        return sensorReadings;
    }

    @Override
    public List<SensorReading> findAll() {
        log.info("Finding all sensor readings");
        List<SensorReading> sensorReadings = new ArrayList<>();
        this.sensorRepository.findAll().forEach(sensorReadings::add);
        return sensorReadings;
    }
}
