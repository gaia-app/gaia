package io.gaia_app.stacks.workflow.state;

import io.gaia_app.stacks.bo.Job;
import io.gaia_app.stacks.bo.JobStatus;
import io.gaia_app.stacks.bo.StepStatus;
import io.gaia_app.stacks.bo.StepType;
import io.gaia_app.stacks.workflow.JobWorkflow;
import io.gaia_app.stacks.bo.Job;
import io.gaia_app.stacks.bo.JobStatus;
import io.gaia_app.stacks.bo.StepStatus;
import io.gaia_app.stacks.bo.StepType;
import io.gaia_app.stacks.workflow.JobWorkflow;
import org.junit.jupiter.api.Assertions;
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
class NotStartedStateTest {

    @Mock
    JobWorkflow jobWorkflow;
    private Job job;

    private NotStartedState state;

    @BeforeEach
    void setup() {
        job = new Job();
        job.setId("test_jobId");
        lenient().when(jobWorkflow.getJob()).thenReturn(job); // use lenient to prevent mockito from throwing exception for tests not needing this mock

        state = new NotStartedState();
    }

    @Test
    void plan_shouldStartTheJob() {
        // when
        state.plan(jobWorkflow);

        // then
        Assertions.assertEquals(JobStatus.PLAN_STARTED, job.getStatus());
        assertThat(job.getStartDateTime()).isNotNull().isEqualToIgnoringSeconds(LocalDateTime.now());
    }

    @Test
    void plan_shouldStartAPlanStep() {
        // when
        state.plan(jobWorkflow);

        // then
        assertThat(job.getSteps()).isNotEmpty().hasSize(1);
        var step = job.getSteps().get(0);
        assertNotNull(step.getId());
        assertEquals("test_jobId", step.getJobId());
        Assertions.assertEquals(StepType.PLAN, step.getType());
        Assertions.assertEquals(StepStatus.STARTED, step.getStatus());
        assertThat(step.getStartDateTime()).isNotNull().isEqualToIgnoringSeconds(LocalDateTime.now());
    }

    @Test
    void plan_shouldUpdateWorkflow() {
        // when
        state.plan(jobWorkflow);

        // then
        var step = job.getSteps().get(0);
        verify(jobWorkflow).setCurrentStep(step);
        verify(jobWorkflow).setState(any(PlanStartedState.class));
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
