package io.codeka.gaia.stacks.workflow;

import io.codeka.gaia.stacks.bo.Job;
import io.codeka.gaia.stacks.bo.JobStatus;
import io.codeka.gaia.stacks.bo.Step;
import io.codeka.gaia.stacks.workflow.state.*;

import java.util.Objects;

/**
 * Manages the workflow of a job and its steps
 */
public class JobWorkflow {

    private Job job;
    private Step currentStep;
    private JobState state;

    public JobWorkflow(Job job) {
        this.job = Objects.requireNonNull(job, "the job must be not null");
        this.state = evalInitialState(job.getStatus());
    }

    public void plan() {
        this.state.plan(this);
    }

    public void apply() {
        this.state.apply(this);
    }

    public void end() {
        this.state.end(this);
    }

    public void fail() {
        this.state.fail(this);
    }

    public void retry() {
        this.state.retry(this);
    }

    public Job getJob() {
        return job;
    }

    public Step getCurrentStep() {
        return currentStep;
    }

    public void setCurrentStep(Step currentStep) {
        this.currentStep = currentStep;
    }

    public JobState getState() {
        return state;
    }

    public void setState(JobState state) {
        this.state = state;
    }

    /**
     * To enable to continue an interrupted workflow, we need to set the state at the right position.
     *
     * @param jobStatus status of the job
     * @return then correct state
     */
    JobState evalInitialState(JobStatus jobStatus) {
        JobState result = new NotStartedState();
        if (jobStatus == null) {
            return result;
        }
        switch (jobStatus) {
            case PLAN_STARTED:
                result = new PlanStartedState();
                break;
            case PLAN_FINISHED:
                result = new PlanFinishedState();
                break;
            case PLAN_FAILED:
                result = new PlanFailedState();
                break;
            case APPLY_STARTED:
                result = new ApplyStartedState();
                break;
            case APPLY_FINISHED:
                result = new ApplyFinishedState();
                break;
            case APPLY_FAILED:
                result = new ApplyFailedState();
                break;
        }
        return result;
    }
}
