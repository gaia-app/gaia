package io.codeka.gaia.test;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.apache.commons.io.IOUtils;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.GenericContainer;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A helper class to start a mongodb container
 */
public class MongoContainer extends GenericContainer {

    private static final Logger LOG = LoggerFactory.getLogger(MongoContainer.class);
    private static final int MONGO_PORT = 27017;

    private final List<String> scripts = new ArrayList<>();
    private MongoClient client;
    private MongoDatabase database;

    public MongoContainer() {
        super("mongo:4.0");
        setExposedPorts(List.of(MONGO_PORT));
    }

    public MongoContainer withScript(String resource) {
        scripts.add(resource);
        return this;
    }

    @Override
    public void start() {
        super.start();
        var mappedPort = getMappedPort(MONGO_PORT);
        // register the port as property for spring
        System.setProperty("gaia.mongodb.uri", String.format("mongodb://localhost:%d/gaia", mappedPort));

        resetDatabase();
    }

    public MongoClient getClient() {
        if (client == null) {
            client = new MongoClient(this.getContainerIpAddress(), getMappedPort(MONGO_PORT));
        }
        return client;
    }

    public MongoDatabase getDatabase() {
        if (database == null) {
            database = getClient().getDatabase("gaia");
        }
        return database;
    }

    public void resetDatabase() {
        var nbScripts = scripts.stream()
                .map(script -> {
                    try (final FileInputStream fis = new FileInputStream(script)) {
                        return IOUtils.toString(fis, "UTF-8");
                    } catch (IOException e) {
                        LOG.warn("Unable to read file: {} skipped.", script);
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .map(content -> new Document("$eval", content))
                .map(getDatabase()::runCommand)
                .count();

        LOG.info("Number of scripts executed: {}", nbScripts);
    }

}
