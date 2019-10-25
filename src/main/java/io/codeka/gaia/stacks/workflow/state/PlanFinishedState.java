package io.codeka.gaia.stacks.workflow.state;

import io.codeka.gaia.stacks.bo.JobStatus;
import io.codeka.gaia.stacks.bo.Step;
import io.codeka.gaia.stacks.bo.StepType;
import io.codeka.gaia.stacks.workflow.JobWorkflow;

/**
 * Describes a job which plan has been finished
 */
public class PlanFinishedState implements JobState {
    @Override
    public void apply(JobWorkflow jobWorkflow) {
        var job = jobWorkflow.getJob();
        job.proceed(JobStatus.APPLY_STARTED);

        var step = new Step(StepType.APPLY, job.getId());
        job.getSteps().add(step);
        jobWorkflow.setCurrentStep(step);
        step.start();

        jobWorkflow.setState(new ApplyStartedState());
    }
}
