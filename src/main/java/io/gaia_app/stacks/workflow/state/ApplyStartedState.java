package io.gaia_app.stacks.workflow.state;

import io.gaia_app.stacks.bo.JobStatus;
import io.gaia_app.stacks.workflow.JobWorkflow;

/**
 * Describes a job which apply has been started
 */
public class ApplyStartedState implements JobState {
    @Override
    public void end(JobWorkflow jobWorkflow) {
        jobWorkflow.getJob().end(JobStatus.APPLY_FINISHED);
        jobWorkflow.setState(new ApplyFinishedState());

        jobWorkflow.getCurrentStep().end();
    }

    @Override
    public void fail(JobWorkflow jobWorkflow) {
        jobWorkflow.getJob().end(JobStatus.APPLY_FAILED);
        jobWorkflow.setState(new ApplyFailedState());

        jobWorkflow.getCurrentStep().fail();
    }
}
