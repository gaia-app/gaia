package io.codeka.gaia.stacks.workflow.state;

import io.codeka.gaia.stacks.workflow.JobWorkflow;

/**
 * Describe the state of job and its possible actions
 */
public interface JobState {

    void plan(JobWorkflow jobWorkflow);

    void apply(JobWorkflow jobWorkflow);

    void end(JobWorkflow jobWorkflow);

    void fail(JobWorkflow jobWorkflow);

}
