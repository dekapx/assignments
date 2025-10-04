package com.dekapx.weatherapp.repository;

import com.dekapx.weatherapp.entity.SensorReading;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface SensorRepository extends CrudRepository<SensorReading, Long> {
    SensorReading findBySensorId(String sensorId);

    @Query("SELECT AVG(s.temperature) FROM SensorReading s WHERE s.timestamp BETWEEN :startTime AND :endTime")
    Double findAverageTemperatureByDateRange(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);
}
