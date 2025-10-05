package com.dekapx.weatherapp.repository;

import com.dekapx.weatherapp.entity.SensorReading;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static com.dekapx.weatherapp.common.SensorReadingTestData.SENSOR_ID;
import static com.dekapx.weatherapp.common.SensorReadingTestData.TEMPERATURE;
import static com.dekapx.weatherapp.common.SensorReadingTestData.buildSensorReading;
import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class SensorRepositoryTest {
    @Autowired
    private SensorRepository sensorRepository;

    @BeforeEach
    public void setup() {
        this.sensorRepository.save(buildSensorReading());
    }

    @AfterEach
    public void tearDown() {
        this.sensorRepository.deleteAll();
    }

    @Test
    public void shouldReturnSensorReadingForGivenSensorId() {
        List<SensorReading> sensorReadings = this.sensorRepository.findBySensorId(SENSOR_ID);
        assertThat(sensorReadings)
                .isNotEmpty()
                .hasSize(1)
                .hasAtLeastOneElementOfType(SensorReading.class)
                .extracting(SensorReading::getSensorId).contains(SENSOR_ID);
    }

    @Test
    public void shouldReturnAllSensorReadings() {
        List<SensorReading> sensorReadings = new ArrayList<>();
        this.sensorRepository.findAll().forEach(sensorReadings::add);
        assertThat(sensorReadings)
                .isNotEmpty()
                .hasSize(1)
                .hasAtLeastOneElementOfType(SensorReading.class)
                .extracting(SensorReading::getSensorId).contains(SENSOR_ID);
    }

    @Test
    public void shouldReturnAverageTemperatureByDateRange() {
        Double averageTemperature = this.sensorRepository.findAverageTemperatureByDateRange(
                now().minusDays(1),
                now().plusDays(1));
        assertThat(averageTemperature)
                .isNotNull()
                .isEqualTo(TEMPERATURE);
    }

    @Test
    public void shouldReturnSensorReadingsBySensorIdAndDateRange() {
        List<SensorReading> sensorReadings = this.sensorRepository.findBySensorIdAndDateRange(SENSOR_ID,
                now().minusDays(1),
                now().plusDays(1));
        assertThat(sensorReadings)
                .isNotEmpty()
                .hasSize(1)
                .hasAtLeastOneElementOfType(SensorReading.class)
                .extracting(SensorReading::getSensorId).contains(SENSOR_ID);
    }
}
