package com.dekapx.weatherapp.controller;

import com.dekapx.weatherapp.entity.SensorReading;
import com.dekapx.weatherapp.model.SensorReadingModel;
import com.dekapx.weatherapp.service.SensorService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static com.dekapx.weatherapp.common.ResourceUrls.BASE_URL;
import static com.dekapx.weatherapp.common.ResourceUrls.INFO_URL;
import static com.dekapx.weatherapp.common.ResourceUrls.SENSOR_BY_ID_URL;
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

    @GetMapping(SENSOR_BY_ID_URL)
    public Mono<SensorReadingModel> getReadings(@PathVariable String sensorId) {
        SensorReading sensorReading = this.sensorService.findById(sensorId);
        SensorReadingModel sensorReadingModel = mapToModel(sensorReading);
        return Mono.just(sensorReadingModel);
    }

    @Operation(summary = "Get All Sensor Readings")
    @GetMapping(SENSOR_URL)
    public Flux<SensorReadingModel> getAllReadings() {
        List<SensorReading> sensorReadings = this.sensorService.findAll();
        List<SensorReadingModel> sensorReadingModels = mapToModels(sensorReadings);
        return Flux.fromIterable(sensorReadingModels);
    }


    private List<SensorReadingModel> mapToModels(List<SensorReading> entities) {
        return entities.stream()
                .map(this::mapToModel)
                .toList();
    }

    private SensorReadingModel mapToModel(SensorReading entity) {
        return SensorReadingModel.builder()
                .sensorId(entity.getSensorId())
                .temperature(entity.getTemperature())
                .humidity(entity.getHumidity())
                .windSpeed(entity.getWindSpeed())
                .timestamp(entity.getTimestamp())
                .build();
    }

    private SensorReading mapToEntity(SensorReadingModel model) {
        return SensorReading.builder()
                .sensorId(model.getSensorId())
                .temperature(model.getTemperature())
                .humidity(model.getHumidity())
                .windSpeed(model.getWindSpeed())
                .timestamp(model.getTimestamp())
                .build();
    }
}
