package io.codeka.gaia.stacks.repository;

import io.codeka.gaia.stacks.bo.Job;
import io.codeka.gaia.stacks.bo.JobStatus;
import io.codeka.gaia.teams.bo.User;
import io.codeka.gaia.teams.repository.UserRepository;
import io.codeka.gaia.test.MongoContainer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.annotation.DirtiesContext;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataMongoTest
@Testcontainers
@DirtiesContext
class JobRepositoryIT {

    @Autowired
    JobRepository jobRepository;

    @Autowired
    UserRepository userRepository;

    @Container
    private static MongoContainer mongo = new MongoContainer();

    @Test
    void jobShouldBeSavedWithLogs() throws IOException {
        var user = new User("test_it");
        userRepository.save(user);

        var job = new Job(user);
        job.setId("12");
        job.setStackId("42");

        job.getLogsWriter().write("some logs");
        job.start(null);
        job.end();

        jobRepository.save(job);

        var saved = jobRepository.findById("12");

        assertTrue(saved.isPresent());
        assertEquals("12", job.getId());
        assertEquals("42", job.getStackId());
        assertEquals(JobStatus.FINISHED, job.getStatus());
        assertEquals("some logs", job.getLogs());
        assertEquals(user, job.getUser());
    }

}
