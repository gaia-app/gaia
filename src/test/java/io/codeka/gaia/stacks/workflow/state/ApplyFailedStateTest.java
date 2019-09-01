package io.codeka.gaia.stacks.workflow.state;

import io.codeka.gaia.stacks.bo.Job;
import io.codeka.gaia.stacks.bo.JobStatus;
import io.codeka.gaia.stacks.bo.Step;
import io.codeka.gaia.stacks.workflow.JobWorkflow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ApplyFailedStateTest {

    @Mock
    JobWorkflow jobWorkflow;
    private Job job;

    private ApplyFailedState state;

    @BeforeEach
    void setup() {
        job = new Job();
        job.setStatus(JobStatus.APPLY_FAILED);
        job.setStartDateTime(LocalDateTime.now());
        job.setEndDateTime(LocalDateTime.now());
        job.getSteps().add(new Step());
        lenient().when(jobWorkflow.getJob()).thenReturn(job); // use lenient to prevent mockito from throwing exception for tests not needing this mock

        state = new ApplyFailedState();
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
    void end_shouldNotBePossible() {
        assertThrows(UnsupportedOperationException.class, () -> state.end(jobWorkflow));
    }

    @Test
    void fail_shouldNotBePossible() {
        assertThrows(UnsupportedOperationException.class, () -> state.fail(jobWorkflow));
    }

    @Test
    void retry_shouldResetJob() {
        // when
        state.retry(jobWorkflow);

        // then
        assertNull(job.getStatus());
        assertNull(job.getStartDateTime());
        assertNull(job.getEndDateTime());
        assertThat(job.getSteps()).isNotNull().isEmpty();
    }

    @Test
    void retry_shouldUpdateWorkflow() {
        // when
        state.retry(jobWorkflow);

        // then
        verify(jobWorkflow).setState(any(NotStartedState.class));
        verify(jobWorkflow).plan();
    }
}
