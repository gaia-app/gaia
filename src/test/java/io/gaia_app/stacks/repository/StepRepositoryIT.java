package io.gaia_app.stacks.repository;

import io.gaia_app.stacks.bo.Step;
import io.gaia_app.stacks.bo.StepStatus;
import io.gaia_app.stacks.bo.StepType;
import io.gaia_app.test.SharedMongoContainerTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataMongoTest
class StepRepositoryIT extends SharedMongoContainerTest {

    @Autowired
    StepRepository stepRepository;

    @Test
    void jobShouldBeSavedWithLogs() {
        var step = new Step(StepType.PLAN, "42");
        step.setId("12");
        step.setLogs(List.of("some logs"));
        step.start();
        step.end();

        stepRepository.save(step);

        var saved = stepRepository.findById("12");

        assertTrue(saved.isPresent());
        Assertions.assertEquals("12", saved.get().getId());
        Assertions.assertEquals("42", saved.get().getJobId());
        Assertions.assertEquals(StepStatus.FINISHED, saved.get().getStatus());
        Assertions.assertEquals(List.of("some logs"), saved.get().getLogs());
    }

}
