package com.dekapx.weatherapp.controller;

import com.dekapx.weatherapp.entity.SensorReading;
import com.dekapx.weatherapp.service.SensorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static com.dekapx.weatherapp.common.SensorReadingTestData.SENSOR_ID;
import static com.dekapx.weatherapp.common.SensorReadingTestData.buildSensorReading;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    @Test
    void getInfo_shouldReturnApiInfo() throws Exception {
        mockMvc.perform(get(API_INFO_URI))
                .andExpect(status().isOk())
                .andExpect(content().string("Weather API v1.0"));
    }

    @Test
    void getAllReadings_shouldReturnAllSensorReadings() throws Exception {
        SensorReading sensorReading = buildSensorReading();
        List<SensorReading> mockReadings = List.of(sensorReading);
        when(sensorService.getAllReadings()).thenReturn(mockReadings);
        mockMvc.perform(get(SENSOR_API_URI)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].sensorId").value(SENSOR_ID))
                .andExpect(jsonPath("$[0].temperature").value(25.5))
                .andExpect(jsonPath("$[0].humidity").value(60.0))
                .andExpect(jsonPath("$[0].windSpeed").value(15.0));
    }

    @Test
    void getReadings_shouldReturnSensorReadingsBySensorId() throws Exception {
        SensorReading sensorReading = buildSensorReading();
        List<SensorReading> mockReadings = List.of(sensorReading);
        when(sensorService.getReadings(SENSOR_ID)).thenReturn(mockReadings);
        mockMvc.perform(get(SENSOR_BY_ID_API_URI, SENSOR_ID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].sensorId").value(SENSOR_ID))
                .andExpect(jsonPath("$[0].temperature").value(25.5))
                .andExpect(jsonPath("$[0].humidity").value(60.0))
                .andExpect(jsonPath("$[0].windSpeed").value(15.0));
    }

    @Test
    void getAverageTemperatureByDateRange_shouldReturnAverageTemperature() throws Exception {
        double averageTemperature = 26.5;
        when(sensorService.getAverageTemperatureByDateRange(getStartTime(), getEndTime())).thenReturn(averageTemperature);
        mockMvc.perform(get(SENSOR_AVERAGE_API_URI)
                        .param(START_TIME, "2025-10-01T00:00")
                        .param(END_TIME, "2025-10-05T23:59")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.averageTemperature").value(26.5));
    }

    @Test
    void getAverageMetricForSensor_shouldReturnAverageMetric() throws Exception {
        double averageHumidity = 62.0;
        when(sensorService.getAverageMetricForSensor(SENSOR_ID, HUMIDITY, getStartTime(), getEndTime())).thenReturn(averageHumidity);
        mockMvc.perform(get(SENSOR_BY_ID_AVERAGE_API_URI, SENSOR_ID)
                        .param(METRIC, HUMIDITY)
                        .param(START_TIME, "2025-10-01T00:00")
                        .param(END_TIME, "2025-10-05T23:59")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.average").value(62.0));
    }

    @Test
    void registerReading_shouldCreateNewSensorReading() throws Exception {
        SensorReading sensorReading = buildSensorReading();
        when(sensorService.registerReading(sensorReading)).thenReturn(sensorReading);

        mockMvc.perform(post(SENSOR_API_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(sensorReading)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.sensorId").value(SENSOR_ID))
                .andExpect(jsonPath("$.temperature").value(25.5))
                .andExpect(jsonPath("$.humidity").value(60.0))
                .andExpect(jsonPath("$.windSpeed").value(15.0));
    }

    private LocalDateTime getStartTime() {
        return LocalDateTime.of(2025, 10, 1, 0, 0);
    }

    private LocalDateTime getEndTime() {
        return LocalDateTime.of(2025, 10, 5, 23, 59);
    }

    private String toJson(SensorReading sensorReading) {
        return """
        {
            "sensorId": "%s",
            "temperature": %s,
            "humidity": %s,
            "windSpeed": %s,
            "timestamp": "%s"
        }
        """.formatted(
                sensorReading.getSensorId(),
                sensorReading.getTemperature(),
                sensorReading.getHumidity(),
                sensorReading.getWindSpeed(),
                sensorReading.getTimestamp().toString()
        );
    }
}
