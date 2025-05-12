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
            - Database: `sensorOperationalDB`
            - Conn URL: `mongodb://localhost:27017/sensorOperationalDB`
            - Collection: `sensor_status_changes`

- **ActiveMQ**: Central message queue for asynchronous communication.
    - Configured to use the `vm://localhost` broker.
    - It in not persistent by default, but can be configured to be persistent.
  
- **Swagger UI**: API documentation and testing interface.
    - Accessed at `http://localhost:8080/swagger-ui` (if using the default port).
- **Prometheus**: App Metrics collection and monitoring.
    - Exposes metrics at `http://localhost:8080/actuator/prometheus` (if using the default port).

## Prerequisites
- Java 17 or higher
- Maven 3.8 or higher
- MongoDB installed locally (if not using embedded MongoDB)

## Configuration
The application uses the following configuration properties:

### `application.properties`
```ini
# Application Name
spring.application.name=SensorDataInterpreter

# ActiveMQ Configuration
spring.activemq.broker-url=vm://localhost?broker.persistent=true
spring.activemq.packages.trust-all=true
sensor.queue.name=central-message-queue

# H2 Database Configuration
spring.datasource.url=jdbc:h2:file:./data/sensorOperationalDB
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=**********
spring.datasource.password=**********
spring.jpa.hibernate.ddl-auto=update
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect

# MongoDB Configuration
spring.data.mongodb.port=27017
spring.data.mongodb.database=sensorOperationalDB
spring.data.mongodb.host=localhost
de.flapdoodle.mongodb.embedded.version=6.0.5

# Swagger UI
springdoc.swagger-ui.path=/swagger-ui

# Metrics
management.endpoints.web.exposure.include=actuator,health,metrics,prometheus