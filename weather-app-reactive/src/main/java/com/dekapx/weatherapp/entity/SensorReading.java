package com.dekapx.weatherapp.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
//@Entity
//@Table(name = "sensor_readings")
@NoArgsConstructor
@AllArgsConstructor
public class SensorReading implements Serializable {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "ID")
    private Long id;

//    @Column(name = "SENSOR_ID", nullable = false)
    private String sensorId;

//    @Column(name = "TEMPERATURE", nullable = false)
    private double temperature;

//    @Column(name = "HUMIDITY", nullable = false)
    private double humidity;

//    @Column(name = "WIND_SPEED", nullable = false)
    private double windSpeed;

//    @Column(name = "TIMESTAMP", nullable = false)
    private LocalDateTime timestamp;
}
