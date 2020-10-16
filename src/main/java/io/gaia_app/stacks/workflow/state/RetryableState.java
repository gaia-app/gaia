package io.gaia_app.stacks.workflow.state;

import io.gaia_app.stacks.workflow.JobWorkflow;
import io.gaia_app.stacks.workflow.JobWorkflow;

interface RetryableState extends JobState {
    @Override
    default void retry(JobWorkflow jobWorkflow) {
        jobWorkflow.getJob().reset();
        jobWorkflow.setState(new NotStartedState());
    }
}
