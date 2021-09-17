package io.gaia_app.stacks.workflow.state;

import io.gaia_app.stacks.bo.JobStatus;
import io.gaia_app.stacks.bo.Step;
import io.gaia_app.stacks.bo.StepType;
import io.gaia_app.stacks.workflow.JobWorkflow;

/**
 * Describes a job which plan has been finished
 */
public class PlanFinishedState implements JobState {
    @Override
    public void apply(JobWorkflow jobWorkflow) {
        jobWorkflow.setState(new ApplyPendingState());
        var job = jobWorkflow.getJob();
        job.setStatus(JobStatus.APPLY_PENDING);

        var step = new Step(StepType.APPLY, job.getId());
        job.getSteps().add(step);
    }
}
