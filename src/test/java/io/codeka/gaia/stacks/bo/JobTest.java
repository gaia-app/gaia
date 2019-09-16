package io.codeka.gaia.stacks.bo;

import io.codeka.gaia.teams.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class JobTest {

    @Test
    void start_shouldSetStatusToPlanStarted() {
        // given
        var job = new Job();

        // when
        job.start();

        // then
        assertEquals(JobStatus.PLAN_STARTED, job.getStatus());
    }

    @Test
    void start_shouldSetStartDateTime() {
        // given
        var job = new Job();
        job.setStartDateTime(null);

        // when
        job.start();

        // then
        assertThat(job.getStartDateTime()).isNotNull().isEqualToIgnoringSeconds(LocalDateTime.now());
    }

    @Test
    void end_shouldSetStatus() {
        // given
        var job = new Job();
        job.setStartDateTime(LocalDateTime.now());

        // when
        job.end(JobStatus.PLAN_FINISHED);

        // then
        assertEquals(JobStatus.PLAN_FINISHED, job.getStatus());
    }

    @Test
    void end_shouldSetEndDateTime() {
        // given
        var job = new Job();
        job.setStartDateTime(LocalDateTime.now());
        job.setEndDateTime(null);

        // when
        job.end(JobStatus.PLAN_FINISHED);

        // then
        assertThat(job.getEndDateTime()).isNotNull().isEqualToIgnoringSeconds(LocalDateTime.now());
    }

    @Test
    void reset_shouldResetStatus() {
        // given
        var job = new Job();
        job.setStatus(JobStatus.PLAN_STARTED);

        // when
        job.reset();

        // then
        assertNull(job.getStatus());
    }

    @Test
    void reset_shouldResetStartDateTime() {
        // given
        var job = new Job();
        job.setStartDateTime(LocalDateTime.now());

        // when
        job.reset();

        // then
        assertNull(job.getStartDateTime());
    }

    @Test
    void reset_shouldResetEndDateTime() {
        // given
        var job = new Job();
        job.setEndDateTime(LocalDateTime.now());

        // when
        job.reset();

        // then
        assertNull(job.getEndDateTime());
    }

    @Test
    void reset_shouldResetSteps() {
        // given
        var job = new Job();
        job.getSteps().add(new Step());
        job.getSteps().add(new Step());

        // when
        job.reset();

        // then
        assertThat(job.getSteps()).isNotNull().isEmpty();
    }

    @Test
    void job_shouldSetId() {
        var job = new Job(null, null, null);

        assertThat(job.getId()).isNotBlank();
    }

    @Test
    void job_shouldSetType() {
        // when
        var job = new Job(JobType.RUN, null, null);

        // then
        assertThat(job.getType()).isNotNull().isEqualTo(JobType.RUN);
    }

    @Test
    void job_shouldSetStackId() {
        // when
        var job = new Job(null, "stackId_test", null);

        // then
        assertThat(job.getStackId()).isNotNull().isEqualTo("stackId_test");
    }

    @Test
    void job_shouldSetUser() {
        // given
        var user = new User("test", null);

        // when
        var job = new Job(null, null, user);

        // then
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