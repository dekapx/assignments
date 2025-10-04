package com.dekapx.weatherapp.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SensorReading {
    private String sensorId;
    private double temperature;
    private double humidity;
    private double windSpeed;
    private LocalDateTime timestamp;
}
