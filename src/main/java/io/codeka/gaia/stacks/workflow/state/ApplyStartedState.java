package io.codeka.gaia.stacks.workflow.state;

import io.codeka.gaia.stacks.bo.JobStatus;
import io.codeka.gaia.stacks.workflow.JobWorkflow;

/**
 * Describes a job which apply has been started
 */
public class ApplyStartedState implements JobState {
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
}
