-- generate some sample data for sensor_readings table for testing purposes
-- sensor_id, temperature, humidity, wind_speed, timestamp
-- can have data for same sensor_id at different timestamps

INSERT INTO sensor_readings(sensor_id, temperature, humidity, wind_speed, "timestamp")
VALUES('Sensor-101', 25.5, 60.0, 15.0, '2025-10-01T22:11:41.400Z');
INSERT INTO sensor_readings(sensor_id, temperature, humidity, wind_speed, "timestamp")
VALUES('Sensor-102', 22.3, 55.0, 10.0, '2025-10-01T22:12:41.400Z');
INSERT INTO sensor_readings(sensor_id, temperature, humidity, wind_speed, "timestamp")
VALUES('Sensor-103', 28.7, 70.0, 20.0, '2025-10-01T22:13:41.400Z');
INSERT INTO sensor_readings(sensor_id, temperature, humidity, wind_speed, "timestamp")
VALUES('Sensor-101', 26.0, 62.0, 12.0, '2025-10-01T23:11:41.400Z');
INSERT INTO sensor_readings(sensor_id, temperature, humidity, wind_speed, "timestamp")
VALUES('Sensor-102', 23.5, 58.0, 11.0, '2025-10-01T23:12:41.400Z');
INSERT INTO sensor_readings(sensor_id, temperature, humidity, wind_speed, "timestamp")
VALUES('Sensor-103', 29.0, 72.0, 18.0, '2025-10-01T23:13:41.400Z');
INSERT INTO sensor_readings(sensor_id, temperature, humidity, wind_speed, "timestamp")
VALUES('Sensor-101', 24.8, 59.0, 14.0, '2025-10-02T00:11:41.400Z');
INSERT INTO sensor_readings(sensor_id, temperature, humidity, wind_speed, "timestamp")
VALUES('Sensor-102', 21.9, 54.0, 13.0, '2025-10-02T00:12:41.400Z');
INSERT INTO sensor_readings(sensor_id, temperature, humidity, wind_speed, "timestamp")
VALUES('Sensor-103', 27.5, 68.0, 16.0, '2025-10-02T00:13:41.400Z');
INSERT INTO sensor_readings(sensor_id, temperature, humidity, wind_speed, "timestamp")
VALUES('Sensor-101', 25.2, 61.0, 15.5, '2025-10-02T01:11:41.400Z');



