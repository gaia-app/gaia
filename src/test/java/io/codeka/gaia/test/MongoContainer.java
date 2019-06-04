package io.codeka.gaia.test;

import org.testcontainers.containers.GenericContainer;

import java.util.List;

/**
 * A helper class to start a mongodb container
 */
public class MongoContainer extends GenericContainer {

    public MongoContainer(){
        super("mongo");
        setExposedPorts(List.of(27017));
    }

    @Override
    public void start() {
        super.start();
        var port = getMappedPort(27017);
        // register the port as property for spring
        System.setProperty("spring.data.mongodb.port", String.valueOf(port));
    }
}
