package io.codeka.gaia.stacks.workflow.state;

import io.codeka.gaia.stacks.bo.JobStatus;
import io.codeka.gaia.stacks.workflow.JobWorkflow;

/**
 * Describes a job which plan has been started
 */
public class PlanStartedState implements JobState {
    @Override
    public void end(JobWorkflow jobWorkflow) {
        jobWorkflow.getCurrentStep().end();
        jobWorkflow.getJob().end(JobStatus.PLAN_FINISHED);
        jobWorkflow.setState(new PlanFinishedState());
    }

    @Override
    public void fail(JobWorkflow jobWorkflow) {
        jobWorkflow.getCurrentStep().fail();
        jobWorkflow.getJob().end(JobStatus.PLAN_FAILED);
        jobWorkflow.setState(new PlanFailedState());
    }
}
