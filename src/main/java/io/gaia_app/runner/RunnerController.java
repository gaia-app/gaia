package io.gaia_app.runner;

import io.gaia_app.credentials.CredentialsService;
import io.gaia_app.settings.bo.Settings;
import io.gaia_app.stacks.bo.*;
import io.gaia_app.stacks.repository.JobRepository;
import io.gaia_app.stacks.repository.PlanRepository;
import io.gaia_app.stacks.repository.StackRepository;
import io.gaia_app.stacks.repository.StepRepository;
import io.gaia_app.stacks.workflow.JobWorkflow;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Controller for the operations that are called by the runner only
 */
@RestController
@RequestMapping("/api/runner")
public class RunnerController {

    private static final Logger LOG = Logger.getLogger("RunnerController");

    private final StackRepository stackRepository;

    private final JobRepository jobRepository;

    private final StepRepository stepRepository;

    private final CredentialsService credentialsService;

    private final RunnerCommandBuilder runnerCommandBuilder;

    private final PlanRepository planRepository;

    private final Settings settings;

    public RunnerController(StackRepository stackRepository,
                            JobRepository jobRepository,
                            StepRepository stepRepository,
                            CredentialsService credentialsService,
                            RunnerCommandBuilder runnerCommandBuilder,
                            PlanRepository planRepository,
                            Settings settings) {
        this.stackRepository = stackRepository;
        this.jobRepository = jobRepository;
        this.stepRepository = stepRepository;
        this.credentialsService = credentialsService;
        this.runnerCommandBuilder = runnerCommandBuilder;
        this.planRepository = planRepository;
        this.settings = settings;
    }

    @GetMapping(value = "/stacks/{id}.tfvars", produces = "text/plain")
    public String tfvars(@PathVariable String id){
        var stack = stackRepository.findById(id).orElseThrow();
        return stack.tfvars();
    }

    /**
     * Gets the first job step that can be run by the runner (one in "pending" state)
     */
    @GetMapping("/steps/request")
    public Map<String, Object> findFirstRunnableStep() {
        var step = this.stepRepository.findFirstByStatus(StepStatus.PENDING)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT));

        var job = this.jobRepository.findById(step.getJobId()).orElseThrow();
        var stack = this.stackRepository.findById(job.getStackId()).orElseThrow();

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

        var env = new ArrayList<>(settings.env());

        if(stack.getCredentialsId() != null){
            this.credentialsService.load(stack.getCredentialsId()).ifPresent(credentials -> env.addAll(credentials.toEnv()));
        }

        return Map.of(
            "id", step.getId(),
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
    @PutMapping("/steps/{stepId}/end")
    public void updateStepStatus(@PathVariable String stepId, @RequestBody int status) {
        // getting jobId
        var jobId = this.stepRepository.findById(stepId).orElseThrow().getJobId();

        // reload the job to check workflow status
        var job = this.jobRepository.findById(jobId).orElseThrow();

        // rebuild the workflow
        var workflow = new JobWorkflow(job);
        workflow.end(status);

        var stack = this.stackRepository.findById(job.getStackId()).orElseThrow();
        if(job.getStatus() == JobStatus.APPLY_FINISHED) {
            if(job.getType() == JobType.RUN){
                stack.setState(StackState.RUNNING);
            }
            else{
                stack.setState(StackState.STOPPED);
            }
        }
        this.stackRepository.save(stack);

        // save the job & step to update their status
        this.stepRepository.saveAll(job.getSteps());
        this.jobRepository.save(job);
    }

    /**
     * Updates the job state
     */
    @PutMapping("/steps/{stepId}/start")
    public void startStep(@PathVariable String stepId) {
        // getting jobId
        var jobId = this.stepRepository.findById(stepId).orElseThrow().getJobId();

        // reload the job to check workflow status
        var job = this.jobRepository.findById(jobId).orElseThrow();

        // rebuild the workflow and start it
        var workflow = new JobWorkflow(job);
        workflow.start();

        // save the job & step to update their status
        this.stepRepository.saveAll(job.getSteps());
        this.jobRepository.save(job);
    }

    @PostMapping(value = "/stacks/{stackId}/jobs/{jobId}/plan")
    public void uploadPlan(@PathVariable String stackId, @PathVariable String jobId, @RequestBody Plan plan){
        LOG.info("received plan from runner");

        // saving the plan
        planRepository.save(plan);

        var job = this.jobRepository.findById(jobId).orElseThrow();
        var planStep = job.getSteps().get(0);
        planStep.setPlan(plan);

        // updating the step with the link to the plan
        stepRepository.save(planStep);
    }
}
