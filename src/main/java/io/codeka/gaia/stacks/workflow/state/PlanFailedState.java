package io.codeka.gaia.stacks.workflow.state;

import io.codeka.gaia.stacks.workflow.JobWorkflow;

/**
 * Describes a job which plan has been failed
 */
public class PlanFailedState implements JobState {
    @Override
    public void plan(JobWorkflow jobWorkflow) {
        throw new UnsupportedOperationException("Unable to start a plan after a plan failed");
    }

    @Override
    public void apply(JobWorkflow jobWorkflow) {
        throw new UnsupportedOperationException("Unable to start an apply after a plan failed");
    }

    @Override
    public void end(JobWorkflow jobWorkflow) {
        throw new UnsupportedOperationException("Unable to end a plan failed");
    }

    @Override
    public void fail(JobWorkflow jobWorkflow) {
        throw new UnsupportedOperationException("Unable to fail a plan failed");
    }
}
