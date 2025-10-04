package com.dekapx.weatherapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.dekapx.weatherapp.common.ResourceUrls.BASE_URL;
import static com.dekapx.weatherapp.common.ResourceUrls.INFO_URL;

@Slf4j
@RestController
@RequestMapping(BASE_URL)
@RequiredArgsConstructor
public class SensorController {
    @Operation(summary = "Weather API Info")
    @GetMapping(INFO_URL)
    public String getInfo() {
        log.info("Weather API v1.0");
        return "Weather API v1.0";
    }
}
