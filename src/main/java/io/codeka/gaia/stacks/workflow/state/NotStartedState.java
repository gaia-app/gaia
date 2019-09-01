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

    @Override
    public void apply(JobWorkflow jobWorkflow) {
        throw new UnsupportedOperationException("Unable to start an apply of a job not even started");
    }

    @Override
    public void end(JobWorkflow jobWorkflow) {
        throw new UnsupportedOperationException("Unable to end the step of a job not even started");
    }

    @Override
    public void fail(JobWorkflow jobWorkflow) {
        throw new UnsupportedOperationException("Unable to fail the step of a job not even started");
    }

    @Override
    public void retry(JobWorkflow jobWorkflow) {
        throw new UnsupportedOperationException("Unable to retry a job not even started");
    }
}
