package com.dekapx.weatherapp.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class SensorRepositoryTest {
    @Autowired
    private SensorRepository sensorRepository;

    @BeforeEach
    public void setup() {
    }

    @AfterEach
    public void tearDown() {
    }

}
