package io.gaia_app.stacks.workflow;

import io.gaia_app.stacks.bo.Job;
import io.gaia_app.stacks.bo.JobStatus;
import io.gaia_app.stacks.bo.Step;
import io.gaia_app.stacks.workflow.state.*;

import java.util.Objects;

/**
 * Manages the workflow of a job and its steps
 */
public class JobWorkflow {

    private final Job job;
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

    public void retry() {
        this.state.retry(this);
    }

    public Job getJob() {
        return job;
    }

    public Step getCurrentStep() {
        // calculating current step depending on the state
        switch (this.job.getStatus()){
            case PLAN_PENDING:
            case PLAN_STARTED:
            case PLAN_FINISHED:
            case PLAN_FAILED:
                return this.job.getSteps().get(0);
            case APPLY_PENDING:
            case APPLY_STARTED:
            case APPLY_FAILED:
            case APPLY_FINISHED:
                return this.job.getSteps().get(1);
        }
        return null;
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
            case PLAN_PENDING:
                result = new PlanPendingState();
                break;
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
                result = new ApplyPendingState();
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

    public void start() {
        this.state.start(this);
    }

    /**
     * Updates workflow to next status depending of the result code
     * @param stepResultCode
     */
    public void end(int stepResultCode) {
        if(this.job.getStatus() == JobStatus.PLAN_STARTED){
            this.managePlanResult(stepResultCode);
        }
        else if(this.job.getStatus() == JobStatus.APPLY_STARTED) {
            this.managerApplyResult(stepResultCode);
        }
    }

    private void managePlanResult(int result) {
        if (result == 0 || result == 2) {
            // diff is empty
            this.state.end(this);
        } else {
            // error
            this.state.fail(this);
        }
    }

    private void managerApplyResult(int result){
        if (result == 0) {
            this.state.end(this);
        } else {
            this.state.fail(this);
        }
    }
}
