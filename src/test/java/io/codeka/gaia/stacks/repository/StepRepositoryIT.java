package io.codeka.gaia.stacks.repository;

import io.codeka.gaia.stacks.bo.Step;
import io.codeka.gaia.stacks.bo.StepStatus;
import io.codeka.gaia.stacks.bo.StepType;
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
class StepRepositoryIT {

    @Autowired
    StepRepository stepRepository;

    @Container
    private static MongoContainer mongo = new MongoContainer();

    @Test
    void jobShouldBeSavedWithLogs() throws IOException {
        var step = new Step(StepType.PLAN, "42");
        step.setId("12");
        step.getLogsWriter().write("some logs");
        step.start();
        step.end();

        stepRepository.save(step);

        var saved = stepRepository.findById("12");

        assertTrue(saved.isPresent());
        assertEquals("12", saved.get().getId());
        assertEquals("42", saved.get().getJobId());
        assertEquals(StepStatus.FINISHED, saved.get().getStatus());
        assertEquals("some logs", saved.get().getLogs());
    }

}
