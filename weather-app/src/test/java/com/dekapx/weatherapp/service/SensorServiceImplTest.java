package com.dekapx.weatherapp.service;

import com.dekapx.weatherapp.entity.SensorReading;
import com.dekapx.weatherapp.exception.ResourceNotFoundException;
import com.dekapx.weatherapp.repository.SensorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static com.dekapx.weatherapp.common.SensorReadingTestData.HUMIDITY;
import static com.dekapx.weatherapp.common.SensorReadingTestData.SENSOR_ID;
import static com.dekapx.weatherapp.common.SensorReadingTestData.TEMPERATURE;
import static com.dekapx.weatherapp.common.SensorReadingTestData.WIND_SPEED;
import static com.dekapx.weatherapp.common.SensorReadingTestData.buildSensorReading;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SensorServiceImplTest {
    @Mock
    private SensorRepository sensorRepository;

    @InjectMocks
    private SensorServiceImpl sensorService;

    @Test
    void givenSensorId_shouldReturnSensorReading() {
        SensorReading reading = buildSensorReading();
        when(sensorRepository.findBySensorId(SENSOR_ID)).thenReturn(List.of(reading));

        List<SensorReading> sensorReadings = this.sensorService.getReadings(SENSOR_ID);

        assertThat(sensorReadings)
                .isNotNull()
                .hasSize(1)
                .first()
                .satisfies(o -> {
                    assertThat(o.getSensorId()).isEqualTo(SENSOR_ID);
                    assertThat(o.getTemperature()).isEqualTo(TEMPERATURE);
                    assertThat(o.getHumidity()).isEqualTo(HUMIDITY);
                    assertThat(o.getWindSpeed()).isEqualTo(WIND_SPEED);
                });
        verify(sensorRepository, times(1)).findBySensorId(SENSOR_ID);
    }

    @Test
    void givenSensorId_shouldThrowResourceNotFoundException() {
        when(sensorRepository.findBySensorId(SENSOR_ID)).thenReturn(null);

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            this.sensorService.getReadings(SENSOR_ID);
        });

        String expectedMessage = "Sensor with id [" + SENSOR_ID + "] not found";
        String actualMessage = exception.getMessage();

        assertThat(expectedMessage).isEqualTo(actualMessage);
        verify(sensorRepository, times(1)).findBySensorId(SENSOR_ID);
    }

    @Test
    void givenNothing_shouldReturnAllSensorReadings() {
        SensorReading reading = buildSensorReading();
        when(sensorRepository.findAll()).thenReturn(List.of(reading));

        List<SensorReading> sensorReadings = this.sensorService.getAllReadings();

        assertThat(sensorReadings)
                .isNotNull()
                .hasSize(1)
                .first()
                .satisfies(o -> {
                    assertThat(o.getSensorId()).isEqualTo(SENSOR_ID);
                    assertThat(o.getTemperature()).isEqualTo(TEMPERATURE);
                    assertThat(o.getHumidity()).isEqualTo(HUMIDITY);
                    assertThat(o.getWindSpeed()).isEqualTo(WIND_SPEED);
                });
        verify(sensorRepository, times(1)).findAll();
    }

    @Test
    void givenSensorReading_shouldRegisterAndReturnSensorReading() {
        SensorReading reading = buildSensorReading();
        when(sensorRepository.save(any(SensorReading.class))).thenReturn(reading);
        SensorReading sensorReading = this.sensorService.registerReading(reading);
        assertThat(sensorReading)
                .isNotNull()
                .satisfies(o -> {
                    assertThat(o.getSensorId()).isEqualTo(SENSOR_ID);
                    assertThat(o.getTemperature()).isEqualTo(TEMPERATURE);
                    assertThat(o.getHumidity()).isEqualTo(HUMIDITY);
                    assertThat(o.getWindSpeed()).isEqualTo(WIND_SPEED);
                    assertThat(o.getTimestamp()).isNotNull();
                });
        verify(sensorRepository, times(1)).save(any(SensorReading.class));
    }

    @Test
    void givenDateRange_shouldReturnAverageTemperature() {
        SensorReading reading = buildSensorReading();
        LocalDateTime startTime = reading.getTimestamp().minusHours(1);
        LocalDateTime endTime = reading.getTimestamp().plusHours(1);

        when(sensorRepository.findAverageTemperatureByDateRange(any(), any())).thenReturn(TEMPERATURE);
        Double averageTemperature = this.sensorService.getAverageTemperatureByDateRange(startTime, endTime);

        assertThat(averageTemperature)
                .isNotNull()
                .isEqualTo(TEMPERATURE);
        verify(sensorRepository, times(1)).findAverageTemperatureByDateRange(any(), any());
    }

    @Test
    void givenSensorIdAndMetricAndDateRange_shouldReturnAverageMetricForSensor() {
        String metric = "TEMPERATURE";
        SensorReading reading = buildSensorReading();
        LocalDateTime startTime = reading.getTimestamp().minusHours(1);
        LocalDateTime endTime = reading.getTimestamp().plusHours(1);

        when(sensorRepository.findBySensorIdAndDateRange(any(), any(), any()))
                .thenReturn(List.of(reading));
        Double averageMetric = this.sensorService.getAverageMetricForSensor(SENSOR_ID, metric, startTime, endTime);
        assertThat(averageMetric)
                .isNotNull()
                .isEqualTo(TEMPERATURE);
        verify(sensorRepository, times(1)).findBySensorIdAndDateRange(any(), any(), any());
    }

}
