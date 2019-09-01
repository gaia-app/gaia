package io.codeka.gaia.stacks.workflow.state;

import io.codeka.gaia.stacks.workflow.JobWorkflow;

/**
 * Describes a job which apply has been failed
 */
public class ApplyFailedState extends RetryableState implements JobState {
    @Override
    public void plan(JobWorkflow jobWorkflow) {
        throw new UnsupportedOperationException("Unable to start a plan after an apply failed");
    }

    @Override
    public void apply(JobWorkflow jobWorkflow) {
        throw new UnsupportedOperationException("Unable to start an apply after an apply failed");
    }

    @Override
    public void end(JobWorkflow jobWorkflow) {
        throw new UnsupportedOperationException("Unable to end an apply failed");
    }

    @Override
    public void fail(JobWorkflow jobWorkflow) {
        throw new UnsupportedOperationException("Unable to fail an apply failed");
    }
}
