package io.gaia_app;

import io.gaia_app.test.SharedMongoContainerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class GaiaIT extends SharedMongoContainerTest {

	@Autowired
	private Gaia gaia;

	@Test
	void contextLoads() {
		assertNotNull(gaia);
	}

}
