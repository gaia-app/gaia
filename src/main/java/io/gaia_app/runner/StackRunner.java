package io.gaia_app.runner;

import io.gaia_app.credentials.CredentialsService;
import io.gaia_app.modules.bo.TerraformModule;
import io.gaia_app.stacks.bo.Job;
import io.gaia_app.stacks.bo.JobType;
import io.gaia_app.stacks.bo.Stack;
import io.gaia_app.stacks.bo.StackState;
import io.gaia_app.stacks.repository.JobRepository;
import io.gaia_app.stacks.repository.StackRepository;
import io.gaia_app.stacks.repository.StepRepository;
import io.gaia_app.stacks.workflow.JobWorkflow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.Supplier;

/**
 * Runs a module instance
 */
@Service
public class StackRunner {

    private DockerRunner dockerRunner;
    private StackCommandBuilder stackCommandBuilder;
    private StackRepository stackRepository;
    private JobRepository jobRepository;
    private StepRepository stepRepository;
    private CredentialsService credentialsService;

    private Map<String, Job> jobs = new HashMap<>();

    @Autowired
    public StackRunner(DockerRunner dockerRunner, StackCommandBuilder stackCommandBuilder,
                       StackRepository stackRepository, JobRepository jobRepository, StepRepository stepRepository, CredentialsService credentialsService) {
        this.dockerRunner = dockerRunner;
        this.stackCommandBuilder = stackCommandBuilder;
        this.stackRepository = stackRepository;
        this.jobRepository = jobRepository;
        this.stepRepository = stepRepository;
        this.credentialsService = credentialsService;
    }

    private String managePlanScript(Job job, Stack stack, TerraformModule module) {
        if (JobType.RUN == job.getType()) {
            return stackCommandBuilder.buildPlanScript(job, stack, module);
        }
        return stackCommandBuilder.buildPlanDestroyScript(job, stack, module);
    }

    private void managePlanResult(Integer result, JobWorkflow jobWorkflow, Stack stack) {
        if (result == 0) {
            // diff is empty
            jobWorkflow.end();
        } else if (result == 2) {
            // there is a diff, set the status of the stack to : "TO_UPDATE"
            if (StackState.NEW != stack.getState() && jobWorkflow.getJob().getType() != JobType.DESTROY) {
                stack.setState(StackState.TO_UPDATE);
                stackRepository.save(stack);
            }
            jobWorkflow.end();
        } else {
            // error
            jobWorkflow.fail();
        }
    }

    private String manageApplyScript(Job job, Stack stack, TerraformModule module) {
        if (JobType.RUN == job.getType()) {
            return stackCommandBuilder.buildApplyScript(job, stack, module);
        }
        return stackCommandBuilder.buildDestroyScript(job, stack, module);
    }

    private void manageApplyResult(Integer result, JobWorkflow jobWorkflow, Stack stack) {
        if (result == 0) {
            jobWorkflow.end();

            // update stack information
            stack.setState(jobWorkflow.getJob().getType() == JobType.RUN ? StackState.RUNNING : StackState.STOPPED);
            stackRepository.save(stack);
        } else {
            jobWorkflow.fail();
        }
    }

    /**
     * @param jobWorkflow
     * @param jobActionFn function applying o the job
     * @param scriptFn    function allowing to get the right script to execute
     * @param resultFn    function treating the result ot the executed script
     */
    private void treatJob(JobWorkflow jobWorkflow, Consumer<JobWorkflow> jobActionFn,
                          Supplier<String> scriptFn, IntConsumer resultFn) {
        // execute the workflow of the job
        jobActionFn.accept(jobWorkflow);

        var job = jobWorkflow.getJob();
        this.jobs.put(job.getId(), job);
        stepRepository.saveAll(job.getSteps());
        jobRepository.save(job);

        // get the wanted script
        var script = scriptFn.get();

        var result = this.dockerRunner.runContainerForJob(jobWorkflow, script);

        // manage the result of the execution of the script
        resultFn.accept(result);

        // save job to database
        stepRepository.saveAll(job.getSteps());
        jobRepository.save(job);
        this.jobs.remove(job.getId());
    }

    @Async
    public void plan(JobWorkflow jobWorkflow, TerraformModule module, Stack stack) {
        if(stack.getCredentialsId() != null){
            jobWorkflow.getJob().setCredentials(this.credentialsService.findById(stack.getCredentialsId()).orElseThrow());
        }
        treatJob(
            jobWorkflow,
            JobWorkflow::plan,
            () -> managePlanScript(jobWorkflow.getJob(), stack, module),
            result -> managePlanResult(result, jobWorkflow, stack)
        );
    }

    @Async
    public void apply(JobWorkflow jobWorkflow, TerraformModule module, Stack stack) {
        if(stack.getCredentialsId() != null){
            jobWorkflow.getJob().setCredentials(this.credentialsService.findById(stack.getCredentialsId()).orElseThrow());
        }
        treatJob(
            jobWorkflow,
            JobWorkflow::apply,
            () -> manageApplyScript(jobWorkflow.getJob(), stack, module),
            result -> manageApplyResult(result, jobWorkflow, stack)
        );
    }

    @Async
    public void retry(JobWorkflow jobWorkflow, TerraformModule module, Stack stack) {
        if(stack.getCredentialsId() != null){
            jobWorkflow.getJob().setCredentials(this.credentialsService.findById(stack.getCredentialsId()).orElseThrow());
        }
        stepRepository.deleteByJobId(jobWorkflow.getJob().getId());
        treatJob(
            jobWorkflow,
            JobWorkflow::retry,
            () -> managePlanScript(jobWorkflow.getJob(), stack, module),
            result -> managePlanResult(result, jobWorkflow, stack)
        );
    }

    public Optional<Job> getJob(String jobId) {
        return Optional.ofNullable(this.jobs.get(jobId));
    }

}
