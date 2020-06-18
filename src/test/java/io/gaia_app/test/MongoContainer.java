package io.gaia_app.test;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.apache.commons.io.IOUtils;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.GenericContainer;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * A helper class to start a mongodb container
 */
public class MongoContainer extends GenericContainer {

    private static final Logger LOG = LoggerFactory.getLogger(MongoContainer.class);
    private static final int MONGO_PORT = 27017;

    private MongoClient client;
    private MongoDatabase database;

    public MongoContainer() {
        super("mongo:4.0");
        setExposedPorts(List.of(MONGO_PORT));
    }

    public MongoClient getClient() {
        if (client == null) {
            client = MongoClients.create(connectionURL());
        }
        return client;
    }

    public String connectionURL(){
        return String.format("mongodb://%s:%d/gaia",this.getContainerIpAddress(), getMappedPort(MONGO_PORT));
    }

    public MongoDatabase getDatabase() {
        if (database == null) {
            database = getClient().getDatabase("gaia");
        }
        return database;
    }

    public void runScript(String resource){
        try (final FileInputStream fis = new FileInputStream(resource)) {
            var content = IOUtils.toString(fis, "UTF-8");

            var document = new Document("$eval", content);

            getDatabase().runCommand(document);
        } catch (IOException e) {
            LOG.warn("Unable to read file: {} skipped.", resource);
        }
    }

    public void emptyDatabase() {
        getDatabase().drop();
    }
}
