package io.codeka.gaia.repository;

import io.codeka.gaia.bo.Job;
import io.codeka.gaia.bo.JobStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@Testcontainers()
class JobRepositoryTest {

    @Autowired
    JobRepository jobRepository;

    @Container
    private static MongoContainer mongo = new MongoContainer();

    @Test
    void jobShouldBeSavedWithLogs() throws IOException {
        var job = new Job();
        job.setId("12");
        job.setStackId("42");

        job.getLogsWriter().write("some logs");
        job.end();

        jobRepository.save(job);

        var saved = jobRepository.findById("12");

        assertTrue(saved.isPresent());
        assertEquals("12", job.getId());
        assertEquals("42", job.getStackId());
        assertEquals(JobStatus.FINISHED, job.getStatus());
        assertEquals("some logs", job.getLogs());
    }

    static class MongoContainer extends GenericContainer{
        MongoContainer(){
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

}