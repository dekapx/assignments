package com.dekapx.weatherapp.controller;

import com.dekapx.weatherapp.entity.SensorReading;
import com.dekapx.weatherapp.model.SensorReadingModel;
import com.dekapx.weatherapp.service.SensorService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class SensorController {
    private SensorService sensorService;
    private ModelMapper modelMapper;

    @Autowired
    public SensorController(SensorService sensorService, ModelMapper modelMapper) {
        this.sensorService = sensorService;
        this.modelMapper = modelMapper;
    }

    @Operation(summary = "Get Sensor Reading by Sensor ID")
    @GetMapping(SENSOR_URL + "/{sensorId}")
    public ResponseEntity<SensorReadingModel> getReading(String sensorId) {
        log.info("Fetching sensor reading for sensorId: {}", sensorId);
        SensorReadingModel model = mapToModel(this.sensorService.getReading(sensorId));
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @Operation(summary = "Get All Sensor Readings")
    @GetMapping(SENSOR_URL)
    public ResponseEntity<List<SensorReadingModel>> getAllReadings() {
        log.info("Fetching all sensor readings...");
        List<SensorReadingModel> models = this.sensorService.getAllReadings()
                .stream()
                .map(this::mapToModel)
                .toList();
        return new ResponseEntity<>(models, HttpStatus.OK);
    }

    @Operation(summary = "Register Sensor Reading")
    @PostMapping(SENSOR_URL)
    public ResponseEntity<SensorReadingModel> registerReading(@RequestBody SensorReadingModel model) {
        log.info("Register sensor reading for Sensor ID : [{}]", model.getSensorId());
        SensorReading sensorReading = this.sensorService.registerReading(mapToEntity(model));
        return new ResponseEntity<>(mapToModel(sensorReading), HttpStatus.CREATED);
    }

    @Operation(summary = "Weather API Info")
    @GetMapping(INFO_URL)
    public String getInfo() {
        log.info("Weather API v1.0");
        return "Weather API v1.0";
    }

    private SensorReading mapToEntity(SensorReadingModel sensorReadingModel) {
        return this.modelMapper.map(sensorReadingModel, SensorReading.class);
    }

    private SensorReadingModel mapToModel(SensorReading sensorReading) {
        return this.modelMapper.map(sensorReading, SensorReadingModel.class);
    }

}
