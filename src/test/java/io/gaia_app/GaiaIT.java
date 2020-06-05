package io.gaia_app;

import io.gaia_app.test.MongoContainer;
import io.gaia_app.test.MongoContainer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Testcontainers
class GaiaIT {

	@Container
	private static MongoContainer mongoContainer = new MongoContainer();

	@Autowired
	private Gaia gaia;

	@Test
	void contextLoads() {
		assertNotNull(gaia);
	}

}
