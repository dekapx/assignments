package com.dekapx.weatherapp.repository;

import com.dekapx.weatherapp.entity.SensorReading;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SensorRepository extends CrudRepository<SensorReading, Long> {
    SensorReading findBySensorId(String sensorId);
}
