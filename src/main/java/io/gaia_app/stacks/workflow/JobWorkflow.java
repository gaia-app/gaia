package io.gaia_app.stacks.workflow;

import io.gaia_app.stacks.bo.*;
import io.gaia_app.stacks.workflow.state.*;
import io.gaia_app.stacks.workflow.state.*;

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
            case APPLY_PENDING:
                result = new PlanFinishedState();
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

    public Step startWorkflow() {
        if(this.job.getStatus() == JobStatus.PLAN_PENDING){
            this.state.plan(this);
        }
        else if(this.job.getStatus() == JobStatus.APPLY_PENDING) {
            this.state.apply(this);
        }
        return this.currentStep;
    }

    /**
     * Updates workflow to next status depending of the result code
     * @param stepResultCode
     */
    public void next(int stepResultCode) {
        if(this.job.getStatus() == JobStatus.PLAN_STARTED){
            this.managePlanResult(stepResultCode);
        }
        else if(this.job.getStatus() == JobStatus.APPLY_STARTED) {
            this.managerApplyResult(stepResultCode);
        }
    }

    private void managePlanResult(int result) {
        if (result == 0) {
            // diff is empty
            this.end();
        } else if (result == 2) {
            this.end();
        } else {
            // error
            this.fail();
        }
    }

    private void managerApplyResult(int result){
        if (result == 0) {
            this.end();
        } else {
            this.fail();
        }
    }
}
