package io.gaia_app.stacks.workflow.state;

import io.gaia_app.stacks.workflow.JobWorkflow;

/**
 * Describes a job which plan has not been started yet
 */
public class PlanPendingState implements JobState {

    @Override
    public void start(JobWorkflow jobWorkflow) {
        var job = jobWorkflow.getJob();

        job.start();
        jobWorkflow.setState(new PlanStartedState());

        jobWorkflow.getCurrentStep().start();
    }
}
