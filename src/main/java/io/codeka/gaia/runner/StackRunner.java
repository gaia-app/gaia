package io.codeka.gaia.runner;

import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.ContainerConfig;
import io.codeka.gaia.bo.*;
import io.codeka.gaia.modules.bo.TerraformModule;
import io.codeka.gaia.repository.JobRepository;
import io.codeka.gaia.repository.StackRepository;
import org.apache.commons.io.output.WriterOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Runs a module instance
 */
@Service
public class StackRunner {

    private DockerClient dockerClient;

    private ContainerConfig.Builder containerConfigBuilder;

    private Settings settings;

    private StackCommandBuilder stackCommandBuilder;

    private Map<String, Job> jobs = new HashMap<>();

    private StackRepository stackRepository;

    private HttpHijackWorkaround httpHijackWorkaround;

    private JobRepository jobRepository;

    @Autowired
    public StackRunner(DockerClient dockerClient, ContainerConfig.Builder containerConfigBuilder, Settings settings, StackCommandBuilder stackCommandBuilder, StackRepository stackRepository, HttpHijackWorkaround httpHijackWorkaround, JobRepository jobRepository) {
        this.dockerClient = dockerClient;
        this.containerConfigBuilder = containerConfigBuilder;
        this.settings = settings;
        this.stackCommandBuilder = stackCommandBuilder;
        this.stackRepository = stackRepository;
        this.httpHijackWorkaround = httpHijackWorkaround;
        this.jobRepository = jobRepository;
    }

    private int runContainerForJob(Job job, String script) {
        try{
            var env = new ArrayList<String>();
            env.add("TF_IN_AUTOMATION=true");
            env.addAll(settings.env());

            // FIXME This is certainly no thread safe !
            var containerConfig = containerConfigBuilder
                    .env(env)
                    .image("hashicorp/terraform:" + job.getCliVersion())
                    .build();

            // pull the image
            dockerClient.pull("hashicorp/terraform:" + job.getCliVersion());

            var containerCreation = dockerClient.createContainer(containerConfig);
            var containerId = containerCreation.id();

            var dockerContainer = new DockerContainer(containerId, dockerClient, httpHijackWorkaround);

            dockerClient.startContainer(containerId);

            // attaching the outputs in a background thread
            CompletableFuture.runAsync(() -> {
                try( var writerOutputStream = new WriterOutputStream(job.getLogsWriter(), Charset.defaultCharset()) ) {
                    // this code is blocking I/O !
                    dockerContainer.attach(writerOutputStream, writerOutputStream);
                } catch (IOException | StackRunnerException e) {
                    e.printStackTrace();
                }
            });

            // write the content of the script to the container's std in
            try( WritableByteChannel stdIn = Channels.newChannel(dockerContainer.getStdIn()) ){
                stdIn.write(ByteBuffer.wrap(script.getBytes()));
            }

            // wait for the container to exit
            var containerExit = dockerClient.waitContainer(containerCreation.id());

            dockerClient.removeContainer(containerCreation.id());

            return Math.toIntExact(containerExit.statusCode());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return 99;
        } catch (DockerException | IOException | StackRunnerException e) {
            return 99;
        }
    }

    @Async
    public void apply(Job job, TerraformModule module, Stack stack) {
        this.jobs.put(job.getId(), job);
        job.setCliVersion(module.getCliVersion());
        job.start(JobType.RUN);
        jobRepository.save(job);

        var applyScript = stackCommandBuilder.buildApplyScript(stack, module);

        var result = runContainerForJob(job, applyScript);

        if(result == 0){
            job.end();

            // update stack information
            stack.setState(StackState.RUNNING);
            stackRepository.save(stack);
        }
        else{
            job.fail();
        }

        // save job to database
        jobRepository.save(job);
        this.jobs.remove(job.getId());
    }

    /**
     * Runs a "plan job".
     * A plan job runs a 'terraform plan'
     * @param job
     * @param module
     * @param stack
     */
    @Async
    public void plan(Job job, TerraformModule module, Stack stack) {
        this.jobs.put(job.getId(), job);
        job.setCliVersion(module.getCliVersion());
        job.start(JobType.PREVIEW);
        jobRepository.save(job);

        var planScript = stackCommandBuilder.buildPlanScript(stack, module);

        var result = runContainerForJob(job, planScript);

        if(result == 0){
            // diff is empty
            job.end();
        }
        else if(result == 2){
            // there is a diff, set the status of the stack to : "TO_UPDATE"
            if(StackState.NEW != stack.getState()){
                stack.setState(StackState.TO_UPDATE);
                stackRepository.save(stack);
            }
            job.end();
        }
        else{
            // error
            job.fail();
        }

        // save job to database
        jobRepository.save(job);
        this.jobs.remove(job.getId());
    }

    public Job getJob(String jobId) {
        if (this.jobs.containsKey(jobId)) {
            // try in memory
            return this.jobs.get(jobId);
        }
        // or find in repository
        return this.jobRepository.findById(jobId).orElseThrow(() -> new RuntimeException("job not found"));
    }

    /**
     * Runs a "stop job".
     * A stop job runs a 'terraform destroy'
     * @param job
     * @param module
     * @param stack
     */
    @Async
    public void stop(Job job, TerraformModule module, Stack stack) {
        this.jobs.put(job.getId(), job);
        job.setCliVersion(module.getCliVersion());
        job.start(JobType.STOP);
        jobRepository.save(job);

        var destroyScript = stackCommandBuilder.buildDestroyScript(stack, module);

        var result = runContainerForJob(job, destroyScript);

        if(result == 0){
            job.end();
            // update state
            stack.setState(StackState.STOPPED);
            stackRepository.save(stack);
        } else{
            // error
            job.fail();
        }

        // save job to database
        jobRepository.save(job);
        this.jobs.remove(job.getId());
    }

}
