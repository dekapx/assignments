# Getting Started

### Reference URLs

- http://localhost:8081/weather-app/api/v1/info
- http://localhost:8081/weather-app/swagger-ui.html

```css
/api/v1/sensor/{sensorId}
/api/v1/sensor
```

```json
{
  "sensorId": "Sensor-101",
  "temperature": 25.5,
  "humidity": 60.0,
  "windSpeed": 15.0,
  "timestamp": "2025-10-05T22:11:41.400Z"
}
```

```json
- Date Range Example:
- start: 2025-10-01T22:11:41.400Z 
- end: 2025-10-02T00:13:41.400Z
```
```sql
CREATE TABLE sensor_readings (
    id SERIAL PRIMARY KEY,
    sensor_id VARCHAR(255) NOT NULL,
    temperature decimal NOT NULL,
    humidity decimal NOT NULL,
    wind_speed decimal NOT NULL,
    timestamp TIMESTAMP NOT NULL
);
```