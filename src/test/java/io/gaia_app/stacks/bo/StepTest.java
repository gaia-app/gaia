package io.gaia_app.stacks.bo;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class StepTest {

    @Test
    void start_shouldSetStatusToStarted() {
        var step = new Step();

        step.start();

        assertEquals(StepStatus.STARTED, step.getStatus());
    }

    @Test
    void start_shouldSetStartDateTime() {
        var step = new Step();
        step.setStartDateTime(null);

        step.start();

        assertThat(step.getStartDateTime()).isNotNull().isEqualToIgnoringSeconds(LocalDateTime.now());
    }

    @Test
    void end_shouldSetStatusToFinished() {
        var step = new Step();
        step.setStartDateTime(LocalDateTime.now());

        step.end();

        assertEquals(StepStatus.FINISHED, step.getStatus());
    }

    @Test
    void end_shouldSetEndDateTime() {
        var step = new Step();
        step.setStartDateTime(LocalDateTime.now());
        step.setEndDateTime(null);
        step.setExecutionTime(null);

        step.end();
        var timer = Duration.between(step.getStartDateTime(), step.getEndDateTime()).toMillis();

        assertThat(step.getEndDateTime()).isNotNull().isEqualToIgnoringSeconds(LocalDateTime.now());
        assertThat(step.getExecutionTime()).isNotNull().isEqualTo(timer);
    }

    @Test
    void fail_shouldSetStatusToFinished() {
        var step = new Step();
        step.setStartDateTime(LocalDateTime.now());

        step.fail();

        assertEquals(StepStatus.FAILED, step.getStatus());
    }

    @Test
    void fail_shouldSetEndDateTime() {
        var step = new Step();
        step.setStartDateTime(LocalDateTime.now());
        step.setEndDateTime(null);
        step.setExecutionTime(null);

        step.fail();
        var timer = Duration.between(step.getStartDateTime(), step.getEndDateTime()).toMillis();

        assertThat(step.getEndDateTime()).isNotNull().isEqualToIgnoringSeconds(LocalDateTime.now());
        assertThat(step.getExecutionTime()).isNotNull().isEqualTo(timer);
    }

    @Test
    void step_shouldSetId() {
        var step = new Step(null, null);

        assertThat(step.getId()).isNotBlank();
    }

    @Test
    void step_shouldSetType() {
        var step = new Step(StepType.PLAN, null);

        assertThat(step.getType()).isNotNull().isEqualTo(StepType.PLAN);
    }

    @Test
    void step_shouldSetJobId() {
        var step = new Step(null, "jobId_test");

        assertThat(step.getJobId()).isNotNull().isEqualTo("jobId_test");
    }

}
