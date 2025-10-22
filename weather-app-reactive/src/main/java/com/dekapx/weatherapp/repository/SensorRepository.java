package com.dekapx.weatherapp.repository;

import com.dekapx.weatherapp.entity.SensorReading;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SensorRepository extends CrudRepository<SensorReading, Long> {
    List<SensorReading> findBySensorId(String sensorId);

    @Query("SELECT AVG(s.temperature) FROM SensorReading s WHERE s.timestamp BETWEEN :startTime AND :endTime")
    Double findAverageTemperatureByDateRange(
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);

    @Query("SELECT s FROM SensorReading s WHERE s.sensorId = :sensorId AND s.timestamp BETWEEN :startTime AND :endTime")
    List<SensorReading> findBySensorIdAndDateRange(
            @Param("sensorId") String sensorId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);
}

