package io.gaia_app.stacks.workflow.state;

import io.gaia_app.stacks.bo.*;
import io.gaia_app.stacks.workflow.JobWorkflow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
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
    void retry_shouldResetJob() {
        // when
        state.retry(jobWorkflow);

        // then
        assertEquals(JobStatus.PLAN_PENDING, job.getStatus());
        assertNull(job.getStartDateTime());
        assertNull(job.getEndDateTime());
    }

    @Test
    void retry_shouldUpdateWorkflow() {
        // when
        state.retry(jobWorkflow);

        // then
        verify(jobWorkflow).setState(any(PlanPendingState.class));
    }

    @Test
    void retry_shouldCreateAPlanStep() {
        // when
        state.retry(jobWorkflow);

        // then
        assertThat(job.getSteps()).isNotEmpty().hasSize(1);
        var step = job.getSteps().get(0);
        assertNotNull(step.getId());
        assertEquals(StepType.PLAN, step.getType());
        assertEquals(StepStatus.PENDING, step.getStatus());
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
    void end_shouldNotBePossible() {
        assertThrows(UnsupportedOperationException.class, () -> state.end(jobWorkflow));
    }

    @Test
    void fail_shouldNotBePossible() {
        assertThrows(UnsupportedOperationException.class, () -> state.fail(jobWorkflow));
    }
}
