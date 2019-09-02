package io.codeka.gaia.stacks.workflow.state;

import io.codeka.gaia.stacks.bo.Step;
import io.codeka.gaia.stacks.bo.StepType;
import io.codeka.gaia.stacks.workflow.JobWorkflow;

/**
 * Describes a new job before any action
 */
public class NotStartedState implements JobState {
    @Override
    public void plan(JobWorkflow jobWorkflow) {
        var job = jobWorkflow.getJob();
        job.start();

        var step = new Step(StepType.PLAN, job.getId());
        job.getSteps().add(step);
        jobWorkflow.setCurrentStep(step);
        step.start();

        jobWorkflow.setState(new PlanStartedState());
    }
}
