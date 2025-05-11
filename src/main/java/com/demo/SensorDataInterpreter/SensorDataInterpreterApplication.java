package com.demo.SensorDataInterpreter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SensorDataInterpreterApplication {

	public static void main(String[] args) {
		SpringApplication.run(SensorDataInterpreterApplication.class, args);
	}

}
