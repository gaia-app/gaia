package io.gaia_app.test;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

public abstract class SharedMongoContainerTest {

    protected static MongoContainer mongo = new MongoContainer();

    static {
        mongo.start();
    }

    @DynamicPropertySource
    static void mongoProperties(DynamicPropertyRegistry registry) {
        registry.add("gaia.mongodb.uri", mongo::connectionURL);
    }

}
