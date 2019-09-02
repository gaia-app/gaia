package io.codeka.gaia.stacks.workflow.state;

import io.codeka.gaia.stacks.workflow.JobWorkflow;

/**
 * Describe the state of job and its possible actions
 */
public interface JobState {
    default void plan(JobWorkflow jobWorkflow) {
        throw new UnsupportedOperationException();
    }

    default void apply(JobWorkflow jobWorkflow) {
        throw new UnsupportedOperationException();
    }

    default void end(JobWorkflow jobWorkflow) {
        throw new UnsupportedOperationException();
    }

    default void fail(JobWorkflow jobWorkflow) {
        throw new UnsupportedOperationException();
    }

    default void retry(JobWorkflow jobWorkflow) {
        throw new UnsupportedOperationException();
    }
}
