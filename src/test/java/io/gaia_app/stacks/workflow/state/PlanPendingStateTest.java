package io.gaia_app.stacks.workflow.state;

import io.gaia_app.stacks.bo.Job;
import io.gaia_app.stacks.bo.JobStatus;
import io.gaia_app.stacks.bo.Step;
import io.gaia_app.stacks.bo.StepStatus;
import io.gaia_app.stacks.workflow.JobWorkflow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PlanPendingStateTest {

    @Mock
    JobWorkflow jobWorkflow;

    private Job job;

    private PlanPendingState state;

    private Step planStep;

    @BeforeEach
    void setup() {
        job = new Job();
        job.setStatus(JobStatus.APPLY_PENDING);
        job.setStartDateTime(LocalDateTime.now());
        job.setEndDateTime(LocalDateTime.now());

        planStep = new Step();
        job.getSteps().add(planStep);

        lenient().when(jobWorkflow.getJob()).thenReturn(job); // use lenient to prevent mockito from throwing exception for tests not needing this mock
        lenient().when(jobWorkflow.getCurrentStep()).thenReturn(planStep); // use lenient to prevent mockito from throwing exception for tests not needing this mock

        state = new PlanPendingState();
    }

    @Test
    void start_shouldStartTheStep(){
        state.start(jobWorkflow);

        assertThat(planStep.getStatus()).isEqualTo(StepStatus.STARTED);
        assertThat(planStep.getStartDateTime()).isNotNull().isEqualToIgnoringSeconds(LocalDateTime.now());
    }

    @Test
    void start_shouldUpdateWorkflow(){
        state.start(jobWorkflow);

        verify(jobWorkflow).setState(any(PlanStartedState.class));
    }

    @Test
    void start_shouldUpdateTheJob(){
        state.start(jobWorkflow);

        assertThat(job.getStatus()).isEqualTo(JobStatus.PLAN_STARTED);
        assertThat(job.getStartDateTime()).isNotNull().isEqualToIgnoringSeconds(LocalDateTime.now());
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
    void retry_shouldNotBePossible() {
        assertThrows(UnsupportedOperationException.class, () -> state.retry(jobWorkflow));
    }
}
