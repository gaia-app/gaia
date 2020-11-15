package io.gaia_app.stacks.workflow.state;

import io.gaia_app.stacks.bo.Step;
import io.gaia_app.stacks.bo.StepType;
import io.gaia_app.stacks.workflow.JobWorkflow;

abstract class RetryableState implements JobState {
    @Override
    public void retry(JobWorkflow jobWorkflow) {
        var job = jobWorkflow.getJob();

        jobWorkflow.getJob().reset();
        jobWorkflow.setState(new PlanPendingState());

        // creating the PLAN step
        var step = new Step(StepType.PLAN, job.getId());
        job.getSteps().add(step);
    }
}
