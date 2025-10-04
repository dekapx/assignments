package com.dekapx.weatherapp.repository;

import com.dekapx.weatherapp.entity.SensorReading;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class SensorRepositoryTest {
    private static final String SENSOR_ID = "Sensor-1";
    private static final double TEMPERATURE = 25.5;
    private static final double HUMIDITY = 60.0;
    private static final double WIND_SPEED = 15.0;

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
        SensorReading optionalSensorReading = sensorRepository.findBySensorId(SENSOR_ID);
        assertThat(optionalSensorReading)
                .isNotNull()
                .satisfies(o -> {
                    assertThat(o.getSensorId()).isEqualTo(SENSOR_ID);
                    assertThat(o.getTemperature()).isEqualTo(TEMPERATURE);
                    assertThat(o.getHumidity()).isEqualTo(HUMIDITY);
                    assertThat(o.getWindSpeed()).isEqualTo(WIND_SPEED);
                    assertThat(o.getTimestamp()).isNotNull();
                });
    }

    @Test
    public void shouldReturnAllSensorReadings() {
        List<SensorReading> sensorReadings = new ArrayList<>();
        this.sensorRepository.findAll().forEach(sensorReadings::add);
        assertThat(sensorReadings)
                .isNotEmpty()
                .hasSize(1)
                .hasAtLeastOneElementOfType(SensorReading.class)
                .filteredOn(c -> c.getId().equals(1L))
                .extracting(SensorReading::getSensorId).contains(SENSOR_ID);
    }

    private SensorReading buildSensorReading() {
        return SensorReading.builder()
                .sensorId(SENSOR_ID)
                .temperature(TEMPERATURE)
                .humidity(HUMIDITY)
                .windSpeed(WIND_SPEED)
                .timestamp(now())
                .build();
    }
}
