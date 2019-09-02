package io.codeka.gaia.stacks.workflow.state;

import io.codeka.gaia.stacks.workflow.JobWorkflow;

interface RetryableState extends JobState {
    @Override
    default void retry(JobWorkflow jobWorkflow) {
        jobWorkflow.getJob().reset();
        jobWorkflow.setState(new NotStartedState());
        jobWorkflow.plan();
    }
}
