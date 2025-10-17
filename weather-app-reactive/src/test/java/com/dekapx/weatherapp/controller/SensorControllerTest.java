package com.dekapx.weatherapp.controller;

import com.dekapx.weatherapp.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(SensorController.class)
public class SensorControllerTest {
    private static final String API_INFO_URI = "/api/v1/info";
    private static final String SENSOR_API_URI = "/api/v1/sensor";
    private static final String SENSOR_BY_ID_API_URI = "/api/v1/sensor/{sensorId}";
    private static final String SENSOR_AVERAGE_API_URI = "/api/v1/sensor/average";
    private static final String SENSOR_BY_ID_AVERAGE_API_URI = "/api/v1/sensor/{sensorId}/average";

    private static final String HUMIDITY = "humidity";
    private static final String METRIC = "metric";
    private static final String START_TIME = "startTime";
    private static final String END_TIME = "endTime";

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SensorService sensorService;

}
