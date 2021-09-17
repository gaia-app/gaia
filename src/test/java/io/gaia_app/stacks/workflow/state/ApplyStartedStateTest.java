package io.gaia_app.stacks.workflow.state;

import io.gaia_app.stacks.bo.Job;
import io.gaia_app.stacks.bo.JobStatus;
import io.gaia_app.stacks.bo.Step;
import io.gaia_app.stacks.bo.StepStatus;
import io.gaia_app.stacks.workflow.JobWorkflow;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ApplyStartedStateTest {

    @Mock
    JobWorkflow jobWorkflow;
    private Job job;
    private Step step;

    private ApplyStartedState state;

    @BeforeEach
    void setup() {
        job = new Job();
        lenient().when(jobWorkflow.getJob()).thenReturn(job); // use lenient to prevent mockito from throwing exception for tests not needing this mock

        step = new Step();
        step.setStartDateTime(LocalDateTime.now());
        lenient().when(jobWorkflow.getCurrentStep()).thenReturn(step); // use lenient for same reason

        state = new ApplyStartedState();
    }

    @Test
    void end_shouldEndTheStep() {
        // when
        state.end(jobWorkflow);

        // then
        Assertions.assertEquals(StepStatus.FINISHED, step.getStatus());
        assertThat(step.getEndDateTime()).isNotNull().isEqualToIgnoringSeconds(LocalDateTime.now());
    }

    @Test
    void end_shouldEndTheJob() {
        // when
        state.end(jobWorkflow);

        // then
        Assertions.assertEquals(JobStatus.APPLY_FINISHED, job.getStatus());
        assertThat(job.getEndDateTime()).isNotNull().isEqualToIgnoringSeconds(LocalDateTime.now());
    }

    @Test
    void end_shouldUpdateWorkflow() {
        // when
        state.end(jobWorkflow);

        // then
        verify(jobWorkflow).setState(any(ApplyFinishedState.class));
    }

    @Test
    void fail_shouldFailTheStep() {
        // when
        state.fail(jobWorkflow);

        // then
        assertEquals(StepStatus.FAILED, step.getStatus());
        assertThat(step.getEndDateTime()).isNotNull().isEqualToIgnoringSeconds(LocalDateTime.now());
    }

    @Test
    void fail_shouldFailTheJob() {
        // when
        state.fail(jobWorkflow);

        // then
        assertEquals(JobStatus.APPLY_FAILED, job.getStatus());
        assertThat(job.getEndDateTime()).isNotNull().isEqualToIgnoringSeconds(LocalDateTime.now());
    }

    @Test
    void fail_shouldUpdateWorkflow() {
        // when
        state.fail(jobWorkflow);

        // then
        verify(jobWorkflow).setState(any(ApplyFailedState.class));
    }

    @Test
    void start_shouldNotBePossible() {
        assertThrows(UnsupportedOperationException.class, () -> state.start(jobWorkflow));
    }

    @Test
    void plan_shouldNotBePossible() {
        assertThrows(UnsupportedOperationException.class, () -> state.plan(jobWorkflow));
    }

    @Test
    void apply_shouldNotBePossible() {
        assertThrows(UnsupportedOperationException.class, () -> state.apply(jobWorkflow));
    }

    @Test
    void retry_shouldNotBePossible() {
        assertThrows(UnsupportedOperationException.class, () -> state.retry(jobWorkflow));
    }
}
