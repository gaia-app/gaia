package io.gaia_app.test;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.BindMode;
import org.testcontainers.containers.GenericContainer;

import java.io.IOException;

/**
 * A helper class to start a mongodb container
 */
public class MongoContainer extends GenericContainer {

    private static final Logger LOG = LoggerFactory.getLogger(MongoContainer.class);
    private static final int MONGO_PORT = 27017;
    public static final String GAIA_SCRIPTS_DIRECTORY = "/gaia_scripts/";

    private MongoClient client;
    private MongoDatabase database;

    public MongoContainer() {
        super("mongo:4.4");
        this.addExposedPort(MONGO_PORT);
        withClasspathResourceMapping("db", GAIA_SCRIPTS_DIRECTORY, BindMode.READ_ONLY);
    }

    public MongoClient getClient() {
        if (client == null) {
            client = MongoClients.create(connectionURL());
        }
        return client;
    }

    public String connectionURL(){
        return String.format("mongodb://%s:%d/gaia",this.getHost(), getFirstMappedPort());
    }

    public MongoDatabase getDatabase() {
        if (database == null) {
            database = getClient().getDatabase("gaia");
        }
        return database;
    }

    public void runScript(String resource){
        try {
            var result = this.execInContainer("mongo", GAIA_SCRIPTS_DIRECTORY+resource);
            if(result.getExitCode() != 0){
                LOG.error("Script execution raised an error : {}", result.getStdout());
                throw new RuntimeException("Script execution raised an error : " + result.getStdout());
            }
        } catch (IOException | InterruptedException e) {
            LOG.warn("Unable to execute script: {} skipped.", resource);
            throw new RuntimeException("Unable to execute script : " + resource, e);

        }
    }

    public void emptyDatabase() {
        getDatabase().drop();
    }
}
