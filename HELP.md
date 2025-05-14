# Sensor Data Interpreter Application

## Overview
The **Sensor Data Interpreter** is a Spring Boot application designed to process and interpret sensor data.

## Components
- **Databases**:
    - H2 Database: Used for storing operational data and metrics.
        - Database URL: `jdbc:h2:file:./data/sensorOperationalDB`
        - Username: `sa`, No password is set by default.
        - Tables/Entities:
          - `sensor_metics`: Stores operational metric like temperature, humidity, battery level, etc.
          - `metic_tresholds`: Stores thresholds for each metric to trigger alerts.
          - `metic_alerts`: Stores alerts triggered by the system.
          - `failed_messages`: Stores messages that failed during processing, for later retry.
        - For persistence, database is created in the `./data` directory relative to the project root.
        - The H2 console can be accessed at `http://localhost:8080/h2-console` (if using the default port).
    
    - Embedded MongoDB: Used for storing sensor statistical (like location) data.
        - Use MongoDB Compass or similar tools to manage the database:
            - Host: `localhost`
            - Port: `27017`
            - Database: `sensorStatisticalDB`
            - Conn URL: `mongodb://localhost:27017/sensorStatisticalDB`
            - Collection: `sensor_status_changes`

- **ActiveMQ**: Central message queue for asynchronous communication.
    - Configured to use the `vm://localhost` broker.
    - It is not persistent by default, but can be configured to be persistent.
  
- **Swagger UI**: API documentation and testing interface.
    - Accessed at `http://localhost:8080/swagger-ui` (if using the default port).
- **Prometheus**: App Metrics collection and monitoring.
    - Exposes metrics at `http://localhost:8080/actuator/prometheus` (if using the default port).

## Prerequisites
- Java 17 or higher
- Maven 3.8 or higher

## Configuration
The application uses `application.properties` for configuration management.