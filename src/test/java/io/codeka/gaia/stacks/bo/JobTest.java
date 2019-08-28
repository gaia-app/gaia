package io.codeka.gaia.stacks.bo;

import io.codeka.gaia.teams.bo.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class JobTest {

    @Test
    void start_shouldSetStatusToPlanStarted() {
        var job = new Job();

        job.start();

        assertEquals(JobStatus.PLAN_STARTED, job.getStatus());
    }

    @Test
    void start_shouldSetStartDateTime() {
        var job = new Job();
        job.setStartDateTime(null);

        job.start();

        assertThat(job.getStartDateTime()).isNotNull().isEqualToIgnoringSeconds(LocalDateTime.now());
    }

    @Test
    void end_shouldSetStatus() {
        var job = new Job();
        job.setStartDateTime(LocalDateTime.now());

        job.end(JobStatus.PLAN_FINISHED);

        assertEquals(JobStatus.PLAN_FINISHED, job.getStatus());
    }

    @Test
    void end_shouldSetEndDateTime() {
        var job = new Job();
        job.setStartDateTime(LocalDateTime.now());
        job.setEndDateTime(null);

        job.end(JobStatus.PLAN_FINISHED);

        assertThat(job.getEndDateTime()).isNotNull().isEqualToIgnoringSeconds(LocalDateTime.now());
    }

    @Test
    void job_shouldSetId() {
        var job = new Job(null, null, null);

        assertThat(job.getId()).isNotBlank();
    }

    @Test
    void job_shouldSetType() {
        var job = new Job(JobType.RUN, null, null);

        assertThat(job.getType()).isNotNull().isEqualTo(JobType.RUN);
    }

    @Test
    void job_shouldSetStackId() {
        var job = new Job(null, "stackId_test", null);

        assertThat(job.getStackId()).isNotNull().isEqualTo("stackId_test");
    }

    @Test
    void job_shouldSetUser() {
        var user = new User("test");

        var job = new Job(null, null, user);

        assertThat(job.getUser()).isNotNull().isEqualTo(user);
    }

    @Test
    void getExecutionTime_shouldReturnSumOfStepExecutionTime() {
        // given
        var job = new Job();
        var step = new Step();
        step.setExecutionTime(10L);
        job.getSteps().add(step);
        step = new Step();
        step.setExecutionTime(20L);
        job.getSteps().add(step);
        job.getSteps().add(new Step());

        // when
        var result = job.getExecutionTime();

        // then
        assertEquals(30L, result);
    }

    @Test
    void getExecutionTime_shouldReturnNullIfNoSteps() {
        // given
        var job = new Job();

        // when
        var result = job.getExecutionTime();

        // then
        assertNull(result);
    }

    @Test
    void getExecutionTime_shouldReturnNullIfNoStepsWithExecutionTime() {
        // given
        var job = new Job();
        job.getSteps().add(new Step());
        job.getSteps().add(new Step());
        job.getSteps().add(new Step());

        // when
        var result = job.getExecutionTime();

        // then
        assertNull(result);
    }
}