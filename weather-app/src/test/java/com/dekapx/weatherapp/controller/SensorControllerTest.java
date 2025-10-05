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
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SensorService sensorService;

    @Test
    void getInfo_shouldReturnApiInfo() throws Exception {
        mockMvc.perform(get("/api/v1/info"))
                .andExpect(status().isOk())
                .andExpect(content().string("Weather API v1.0"));
    }

    @Test
    void getAllReadings_shouldReturnAllSensorReadings() throws Exception {
        SensorReading sensorReading = buildSensorReading();
        List<SensorReading> mockReadings = List.of(sensorReading);
        when(sensorService.getAllReadings()).thenReturn(mockReadings);
        mockMvc.perform(get("/api/v1/sensor")
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
        mockMvc.perform(get("/api/v1/sensor/{sensorId}", SENSOR_ID)
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
        LocalDateTime start = LocalDateTime.of(2025, 10, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2025, 10, 5, 23, 59);
        when(sensorService.getAverageTemperatureByDateRange(start, end)).thenReturn(averageTemperature);
        mockMvc.perform(get("/api/v1/sensor/average")
                        .param("startTime", "2025-10-01T00:00")
                        .param("endTime", "2025-10-05T23:59")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("26.5"));
    }

    @Test
    void getAverageMetricForSensor_shouldReturnAverageMetric() throws Exception {
        double averageHumidity = 62.0;
        LocalDateTime start = LocalDateTime.of(2025, 10, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2025, 10, 5, 23, 59);
        when(sensorService.getAverageMetricForSensor(SENSOR_ID, "humidity", start, end)).thenReturn(averageHumidity);
        mockMvc.perform(get("/api/v1/sensor/{sensorId}/average", SENSOR_ID)
                        .param("metric", "humidity")
                        .param("startTime", "2025-10-01T00:00")
                        .param("endTime", "2025-10-05T23:59")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("62.0"));
    }

    @Test
    void registerReading_shouldCreateNewSensorReading() throws Exception {
        SensorReading sensorReading = buildSensorReading();
        SensorReading savedReading = buildSensorReading();

        when(sensorService.registerReading(sensorReading)).thenReturn(savedReading);

        String requestBody = toJson(sensorReading);

        mockMvc.perform(post("/api/v1/sensor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.sensorId").value(SENSOR_ID))
                .andExpect(jsonPath("$.temperature").value(25.5))
                .andExpect(jsonPath("$.humidity").value(60.0))
                .andExpect(jsonPath("$.windSpeed").value(15.0));
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
