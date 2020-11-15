package io.gaia_app.stacks.workflow.state;

import io.gaia_app.stacks.bo.JobStatus;
import io.gaia_app.stacks.workflow.JobWorkflow;

public class ApplyPendingState implements JobState {

    @Override
    public void start(JobWorkflow jobWorkflow) {
        var job = jobWorkflow.getJob();

        job.proceed(JobStatus.APPLY_STARTED);
        jobWorkflow.setState(new ApplyStartedState());

        jobWorkflow.getCurrentStep().start();
    }
}
