package io.gaia_app.runner;

import io.gaia_app.credentials.CredentialsService;
import io.gaia_app.stacks.bo.*;
import io.gaia_app.stacks.repository.JobRepository;
import io.gaia_app.stacks.repository.StackRepository;
import io.gaia_app.stacks.repository.StepRepository;
import io.gaia_app.stacks.workflow.JobWorkflow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Controller for the operations that are called by the runner only
 */
@RestController
@RequestMapping("/api/runner")
public class RunnerController {

    @Autowired
    private StackRepository stackRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private StepRepository stepRepository;

    @Autowired
    private CredentialsService credentialsService;

    @Autowired
    private RunnerCommandBuilder runnerCommandBuilder;

    @GetMapping(value = "/stacks/{id}.tfvars", produces = "text/plain")
    public String tfvars(@PathVariable String id){
        var stack = stackRepository.findById(id).orElseThrow();
        return stack.tfvars();
    }

    /**
     * Gets the first job step that can be run by the runner (one in "pending" state)
     */
    @GetMapping("/steps/request")
    public Map<String, Object> findFirstRunnableJob() {
        var job = this.jobRepository.findFirstByStatusEqualsOrStatusEquals(JobStatus.PLAN_PENDING, JobStatus.APPLY_PENDING)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        var stack = this.stackRepository.findById(job.getStackId()).orElseThrow();

        // get the workflow
        var workflow = new JobWorkflow(job);
        // start the workflow
        var step = workflow.startWorkflow();

        // saving the job
        this.jobRepository.save(job);
        this.stepRepository.save(step);

        var script = "";
        // generate the script
        if(job.getType() == JobType.RUN){
            if(step.getType() == StepType.PLAN) {
                script = runnerCommandBuilder.buildPlanScript(job, stack, stack.getModule());
            }
            else {
                script = runnerCommandBuilder.buildApplyScript(job, stack, stack.getModule());
            }
        }
        else { // destroy
            if(step.getType() == StepType.PLAN) {
                script = runnerCommandBuilder.buildPlanDestroyScript(job, stack, stack.getModule());
            }
            else {
                script = runnerCommandBuilder.buildDestroyScript(job, stack, stack.getModule());
            }
        }

        List<String> env = Collections.emptyList();
        if(stack.getCredentialsId() != null){
            var credentials = this.credentialsService.load(stack.getCredentialsId()).orElse(null);
            if(credentials != null){
                env = credentials.toEnv();
            }
        }

        return Map.of(
            "step", step,
            "script", script,
            "env", env,
            "image", job.getTerraformImage().image());
    }

    /**
     * Updates the step logs
     */
    @PutMapping("/steps/{stepId}/logs")
    public void updateLogs(@PathVariable String stepId, @RequestBody String logs) {
        var step = this.stepRepository.findById(stepId).orElseThrow();
        step.getLogs().add(logs);
        this.stepRepository.save(step);
    }

    /**
     * Updates the step status
     */
    @PutMapping("/steps/{stepId}/status")
    public void updateStepStatus(@PathVariable String stepId, @RequestBody int status) {
        var step = this.stepRepository.findById(stepId).orElseThrow();

        // reload the job to check workflow status
        var job = this.jobRepository.findById(step.getJobId()).orElseThrow();

        // rebuild the workflow
        var workflow = new JobWorkflow(job);
        workflow.setCurrentStep(step);
        workflow.next(status);

        // save the job & step to update their status
        this.stepRepository.save(step);
        this.jobRepository.save(job);
    }

    /**
     * Updates the job state
     */
    @PutMapping("/steps/{stepId}/start")
    public void startStep(@PathVariable String stepId) {
        // getting step
        var step = this.stepRepository.findById(stepId).orElseThrow();

        // reload the job to check workflow status
        var job = this.jobRepository.findById(step.getJobId()).orElseThrow();

        // rebuild the workflow and start it
        var workflow = new JobWorkflow(job);
        workflow.startWorkflow();

        // save the job & step to update their status
        this.stepRepository.save(step);
        this.jobRepository.save(job);
    }
}
