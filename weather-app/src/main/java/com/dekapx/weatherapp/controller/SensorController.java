package com.dekapx.weatherapp.controller;

import com.dekapx.weatherapp.model.SensorReadingModel;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.dekapx.weatherapp.common.ResourceUrls.BASE_URL;
import static com.dekapx.weatherapp.common.ResourceUrls.INFO_URL;
import static com.dekapx.weatherapp.common.ResourceUrls.SENSOR_URL;

@Slf4j
@RestController
@RequestMapping(BASE_URL)
@RequiredArgsConstructor
public class SensorController {
    @Operation(summary = "Get Sensor Reading by Sensor ID")
    @GetMapping(SENSOR_URL + "/{sensorId}")
    public SensorReadingModel getReading(String sensorId) {
        log.info("Fetching sensor reading for sensorId: {}", sensorId);
        return SensorReadingModel.builder()
                .sensorId(sensorId)
                .temperature(24.5)
                .humidity(60.0)
                .windSpeed(15.0)
                .timestamp(java.time.LocalDateTime.now())
                .build();
    }

    @Operation(summary = "Get All Sensor Readings")
    @GetMapping(SENSOR_URL)
    public List<SensorReadingModel> getAllReadings() {
        return List.of(SensorReadingModel.builder()
                .sensorId("Sensor-1")
                .temperature(24.5)
                .humidity(60.0)
                .windSpeed(15.0)
                .timestamp(java.time.LocalDateTime.now())
                .build());
    }

    @Operation(summary = "Register Sensor Reading")
    @PostMapping(SENSOR_URL)
    public SensorReadingModel registerReading(@RequestBody SensorReadingModel sensorReadingModel) {
        log.info("Received sensor reading: {}", sensorReadingModel);
        return SensorReadingModel.builder()
                .sensorId("Sensor-1")
                .temperature(24.5)
                .humidity(60.0)
                .windSpeed(15.0)
                .timestamp(java.time.LocalDateTime.now())
                .build();
    }

    @Operation(summary = "Weather API Info")
    @GetMapping(INFO_URL)
    public String getInfo() {
        log.info("Weather API v1.0");
        return "Weather API v1.0";
    }
}
