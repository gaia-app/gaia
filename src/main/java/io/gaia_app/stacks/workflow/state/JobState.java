package io.gaia_app.stacks.workflow.state;

import io.gaia_app.stacks.workflow.JobWorkflow;

/**
 * Describe the state of job and its possible actions
 */
public interface JobState {

    default void start(JobWorkflow jobWorkflow){
        throw new UnsupportedOperationException();
    }

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
