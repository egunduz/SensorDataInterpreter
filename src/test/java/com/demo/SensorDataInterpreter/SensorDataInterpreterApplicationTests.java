package com.demo.SensorDataInterpreter;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
// @Transactional not necessary for in memory db
class SensorDataInterpreterApplicationTests {

	@Test
	void contextLoads() {
	}

}
