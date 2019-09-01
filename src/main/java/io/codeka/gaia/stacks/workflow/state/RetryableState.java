package io.codeka.gaia.stacks.workflow.state;

import io.codeka.gaia.stacks.workflow.JobWorkflow;

abstract class RetryableState {
    public final void retry(JobWorkflow jobWorkflow) {
        jobWorkflow.getJob().reset();
        jobWorkflow.setState(new NotStartedState());
        jobWorkflow.plan();
    }
}
