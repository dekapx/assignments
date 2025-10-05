package com.dekapx.weatherapp.common;

public interface ResourceUrls {
    String BASE_URL = "/api/v1";
    String INFO_URL = "/info";
    String SENSOR_URL = "/sensor";
    String SENSOR_BY_ID_URL = SENSOR_URL + "/{sensorId}";
    String AVERAGE_TEMPERATURE_URL = SENSOR_URL + "/average";
    String AVERAGE_METRIC_FOR_SENSOR_URL = SENSOR_URL + "/{sensorId}/average";
}
