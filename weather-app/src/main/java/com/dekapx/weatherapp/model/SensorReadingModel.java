package com.dekapx.weatherapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class SensorReadingModel {
    private String sensorId;
    private double temperature;
    private double humidity;
    private double windSpeed;
    private LocalDateTime timestamp;
}
