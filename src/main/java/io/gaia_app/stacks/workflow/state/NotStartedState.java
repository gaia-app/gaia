package io.gaia_app.stacks.workflow.state;

import io.gaia_app.stacks.bo.*;
import io.gaia_app.stacks.workflow.JobWorkflow;
import io.gaia_app.stacks.bo.Step;
import io.gaia_app.stacks.bo.StepType;
import io.gaia_app.stacks.workflow.JobWorkflow;

/**
 * Describes a new job before any action
 */
public class NotStartedState implements JobState {
    @Override
    public void plan(JobWorkflow jobWorkflow) {
        var job = jobWorkflow.getJob();
        job.setStatus(JobStatus.PLAN_PENDING);

        // creating the PLAN step
        var step = new Step(StepType.PLAN, job.getId());
        job.getSteps().add(step);

        jobWorkflow.setState(new PlanPendingState());
    }
}
