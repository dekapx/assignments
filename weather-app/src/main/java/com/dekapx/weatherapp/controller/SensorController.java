package com.dekapx.weatherapp.controller;

import com.dekapx.weatherapp.entity.SensorReading;
import com.dekapx.weatherapp.service.SensorService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

import static com.dekapx.weatherapp.common.ResourceUrls.BASE_URL;
import static com.dekapx.weatherapp.common.ResourceUrls.INFO_URL;
import static com.dekapx.weatherapp.common.ResourceUrls.SENSOR_URL;

@Slf4j
@RestController
@RequestMapping(BASE_URL)
public class SensorController {
    private SensorService sensorService;

    @Autowired
    public SensorController(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Operation(summary = "Weather API Info")
    @GetMapping(INFO_URL)
    public String getInfo() {
        log.info("Weather API v1.0");
        return "Weather API v1.0";
    }

    @Operation(summary = "Get Sensor Reading by Sensor ID")
    @GetMapping(SENSOR_URL + "/{sensorId}")
    public ResponseEntity<SensorReading> getReading(@PathVariable String sensorId) {
        SensorReading sensorReading = this.sensorService.getReading(sensorId);
        return new ResponseEntity<>(sensorReading, HttpStatus.OK);
    }

    @Operation(summary = "Get All Sensor Readings")
    @GetMapping(SENSOR_URL)
    public ResponseEntity<List<SensorReading>> getAllReadings() {
        List<SensorReading> sensorReadings  = this.sensorService.getAllReadings();
        return new ResponseEntity<>(sensorReadings, HttpStatus.OK);
    }

    @Operation(summary = "Register Sensor Reading")
    @PostMapping(SENSOR_URL)
    public ResponseEntity<SensorReading> registerReading(@RequestBody SensorReading sensorReading) {
        return new ResponseEntity<>(this.sensorService.registerReading(sensorReading), HttpStatus.CREATED);
    }

    @Operation(summary = "Get Average Temperature within Date Range")
    @GetMapping(SENSOR_URL + "/average")
    public ResponseEntity<Double> getAverageTemperature(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        Double averageTemperature = sensorService.getAverageTemperatureByDateRange(startTime, endTime);
        return new ResponseEntity<>(averageTemperature, HttpStatus.OK);
    }

    @Operation(summary = "Get Average Metric for Sensor within Date Range")
    @GetMapping(SENSOR_URL + "/{sensorId}/average")
    public double getAverageMetricForSensor(
            @PathVariable String sensorId,
            @RequestParam String metric,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        List<SensorReading> readings = sensorService.getReadingsBySensorIdAndDateRange(sensorId, startTime, endTime);
        return calculateAverage(readings, metric);
    }

    private double calculateAverage(List<SensorReading> readings, String metric) {
        return readings.stream()
                .mapToDouble(r -> {
                    switch (metric.toLowerCase()) {
                        case "temperature": return r.getTemperature();
                        case "humidity": return r.getHumidity();
                        case "windspeed": return r.getWindSpeed();
                        default: throw new IllegalArgumentException("Invalid metric: " + metric);
                    }
                })
                .average()
                .orElse(Double.NaN);
    }
}
