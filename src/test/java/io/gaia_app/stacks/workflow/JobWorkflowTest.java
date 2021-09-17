package io.gaia_app.stacks.workflow;

import io.gaia_app.stacks.bo.Job;
import io.gaia_app.stacks.bo.JobStatus;
import io.gaia_app.stacks.workflow.state.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
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
        Assertions.assertEquals(job, jobWorkflow.getJob());
    }

    @Test
    void jobWorkflow_shouldSetDefaultState() {
        // then
        assertThat(jobWorkflow.getState()).isInstanceOf(NotStartedState.class);
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
    void start_shouldStartState() {
        // given
        jobWorkflow.setState(jobState);

        // when
        jobWorkflow.start();

        // then
        verify(jobState).start(jobWorkflow);
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
    void end_shouldEndState_whenPlanIsSuccessful() {
        // given
        job.setStatus(JobStatus.PLAN_STARTED);
        jobWorkflow.setState(jobState);

        // when
        jobWorkflow.end(0);

        // then
        verify(jobState).end(jobWorkflow);
    }

    @Test
    void end_shouldEndState_whenPlanIsSuccessfulWithChanges() {
        // given
        job.setStatus(JobStatus.PLAN_STARTED);
        jobWorkflow.setState(jobState);

        // when
        jobWorkflow.end(2);

        // then
        verify(jobState).end(jobWorkflow);
    }

    @Test
    void end_shouldFailState_whenPlanIsUnSuccessful() {
        // given
        job.setStatus(JobStatus.PLAN_STARTED);
        jobWorkflow.setState(jobState);

        // when
        jobWorkflow.end(99);

        // then
        verify(jobState).fail(jobWorkflow);
    }

    @Test
    void end_shouldEndState_whenApplyIsSuccessful() {
        // given
        job.setStatus(JobStatus.APPLY_STARTED);
        jobWorkflow.setState(jobState);

        // when
        jobWorkflow.end(0);

        // then
        verify(jobState).end(jobWorkflow);
    }

    @Test
    void end_shouldFailState_whenApplyIsUnSuccessful() {
        // given
        job.setStatus(JobStatus.APPLY_STARTED);
        jobWorkflow.setState(jobState);

        // when
        jobWorkflow.end(99);

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
    void evalInitialState_shouldReturnPlanPendingState() {
        // when
        var result = jobWorkflow.evalInitialState(JobStatus.PLAN_PENDING);

        // then
        assertThat(result).isInstanceOf(PlanPendingState.class);
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
    void evalInitialState_shouldReturnApplyPendingState() {
        // when
        var result = jobWorkflow.evalInitialState(JobStatus.APPLY_PENDING);

        // then
        assertThat(result).isInstanceOf(ApplyPendingState.class);
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
