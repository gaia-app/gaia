package io.codeka.gaia.stacks.workflow;

import io.codeka.gaia.stacks.bo.Job;
import io.codeka.gaia.stacks.bo.JobStatus;
import io.codeka.gaia.stacks.bo.Step;
import io.codeka.gaia.stacks.workflow.state.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class JobWorkflowTest {

    @Mock
    JobState jobState;

    private JobWorkflow jobWorkflow;
    private Job job;

    @BeforeEach
    void setup() {
        job = new Job();
        jobWorkflow = new JobWorkflow(job);
    }

    @Test
    void jobWorkflow_shouldSetJob() {
        // then
        assertEquals(job, jobWorkflow.getJob());
    }

    @Test
    void jobWorkflow_shouldSetDefaultState() {
        // then
        assertThat(jobWorkflow.getState()).isInstanceOf(NotStartedState.class);
    }

    @Test
    void jobWorkflow_shouldHaveCurrentStep() {
        // given
        var step = new Step();

        // when
        jobWorkflow.setCurrentStep(step);

        // then
        assertEquals(step, jobWorkflow.getCurrentStep());
    }

    @Test
    void plan_shouldPlanState() {
        // given
        jobWorkflow.setState(jobState);

        // when
        jobWorkflow.plan();

        // then
        verify(jobState).plan(jobWorkflow);
    }

    @Test
    void apply_shouldApplyState() {
        // given
        jobWorkflow.setState(jobState);

        // when
        jobWorkflow.apply();

        // then
        verify(jobState).apply(jobWorkflow);
    }

    @Test
    void end_shouldEndState() {
        // given
        jobWorkflow.setState(jobState);

        // when
        jobWorkflow.end();

        // then
        verify(jobState).end(jobWorkflow);
    }

    @Test
    void fail_shouldFailState() {
        // given
        jobWorkflow.setState(jobState);

        // when
        jobWorkflow.fail();

        // then
        verify(jobState).fail(jobWorkflow);
    }

    @Test
    void retry_shouldRetryState() {
        // given
        jobWorkflow.setState(jobState);

        // when
        jobWorkflow.retry();

        // then
        verify(jobState).retry(jobWorkflow);
    }

    @Test
    void evalInitialState_shouldReturnDefaultState() {
        // when
        var result = jobWorkflow.evalInitialState(null);

        // then
        assertThat(result).isInstanceOf(NotStartedState.class);
    }

    @Test
    void evalInitialState_shouldReturnPlanStartedState() {
        // when
        var result = jobWorkflow.evalInitialState(JobStatus.PLAN_STARTED);

        // then
        assertThat(result).isInstanceOf(PlanStartedState.class);
    }

    @Test
    void evalInitialState_shouldReturnPlanFinishedState() {
        // when
        var result = jobWorkflow.evalInitialState(JobStatus.PLAN_FINISHED);

        // then
        assertThat(result).isInstanceOf(PlanFinishedState.class);
    }

    @Test
    void evalInitialState_shouldReturnPlanFailedState() {
        // when
        var result = jobWorkflow.evalInitialState(JobStatus.PLAN_FAILED);

        // then
        assertThat(result).isInstanceOf(PlanFailedState.class);
    }

    @Test
    void evalInitialState_shouldReturnApplyStartedState() {
        // when
        var result = jobWorkflow.evalInitialState(JobStatus.APPLY_STARTED);

        // then
        assertThat(result).isInstanceOf(ApplyStartedState.class);
    }

    @Test
    void evalInitialState_shouldReturnApplyFinishedState() {
        // when
        var result = jobWorkflow.evalInitialState(JobStatus.APPLY_FINISHED);

        // then
        assertThat(result).isInstanceOf(ApplyFinishedState.class);
    }

    @Test
    void evalInitialState_shouldReturnApplyFailedState() {
        // when
        var result = jobWorkflow.evalInitialState(JobStatus.APPLY_FAILED);

        // then
        assertThat(result).isInstanceOf(ApplyFailedState.class);
    }
}
