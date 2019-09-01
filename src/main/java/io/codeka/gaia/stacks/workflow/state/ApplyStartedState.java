package io.codeka.gaia.stacks.workflow.state;

import io.codeka.gaia.stacks.bo.JobStatus;
import io.codeka.gaia.stacks.workflow.JobWorkflow;

/**
 * Describes a job which apply has been started
 */
public class ApplyStartedState implements JobState {
    @Override
    public void plan(JobWorkflow jobWorkflow) {
        throw new UnsupportedOperationException("Unable to start a plan after an apply started");
    }

    @Override
    public void apply(JobWorkflow jobWorkflow) {
        throw new UnsupportedOperationException("Unable to start an apply after an apply started");
    }

    @Override
    public void end(JobWorkflow jobWorkflow) {
        jobWorkflow.getCurrentStep().end();
        jobWorkflow.getJob().end(JobStatus.APPLY_FINISHED);
        jobWorkflow.setState(new ApplyFinishedState());
    }

    @Override
    public void fail(JobWorkflow jobWorkflow) {
        jobWorkflow.getCurrentStep().fail();
        jobWorkflow.getJob().end(JobStatus.APPLY_FAILED);
        jobWorkflow.setState(new ApplyFailedState());
    }

    @Override
    public void retry(JobWorkflow jobWorkflow) {
        throw new UnsupportedOperationException("Unable to retry a job after an apply started");
    }
}
