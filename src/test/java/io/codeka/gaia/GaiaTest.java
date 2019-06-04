package io.codeka.gaia;

import io.codeka.gaia.test.MongoContainer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
class GaiaTest {

	@Container
	private static MongoContainer mongoContainer = new MongoContainer();

	@Test
	void contextLoads() {}

}
