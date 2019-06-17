package io.codeka.gaia.runner;

import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.messages.ContainerConfig;
import io.codeka.gaia.bo.*;
import io.codeka.gaia.repository.JobRepository;
import io.codeka.gaia.repository.StackRepository;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
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

    private int runContainerForJob(Job job, String script){
        try{
            // FIXME This is certainly no thread safe !
            var containerConfig = containerConfigBuilder
                    .env(settings.env())
                    .build();

            // pull the image
            dockerClient.pull("hashicorp/terraform:latest");

            System.out.println("Create container");
            var containerCreation = dockerClient.createContainer(containerConfig);
            var containerId = containerCreation.id();

            // attach stdin
            System.err.println("Attach container");
            var logStream = dockerClient.attachContainer(containerId, DockerClient.AttachParameter.STDIN, DockerClient.AttachParameter.STDOUT, DockerClient.AttachParameter.STDERR, DockerClient.AttachParameter.STREAM);
            var writable = httpHijackWorkaround.getOutputStream(logStream, "unix:///var/apply/docker.sock");

            System.err.println("Starting container");
            dockerClient.startContainer(containerId);

            final PipedInputStream stdout = new PipedInputStream();
            final PipedInputStream stderr = new PipedInputStream();
            final PipedOutputStream stdout_pipe = new PipedOutputStream(stdout);
            final PipedOutputStream stderr_pipe = new PipedOutputStream(stderr);

            // writing to System.err
            CompletableFuture.runAsync(() -> {
                try {
                    System.err.println("Copying to System.err");
                    System.err.println("Attaching stdout and stderr");
                    // apply another attachment for stdout and stderr (who knows why?)
                    dockerClient.attachContainer(containerId,
                            DockerClient.AttachParameter.LOGS, DockerClient.AttachParameter.STDOUT,
                            DockerClient.AttachParameter.STDERR, DockerClient.AttachParameter.STREAM)
                            .attach(stdout_pipe, stderr_pipe, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            CompletableFuture.runAsync(() -> {
                try {
                    IOUtils.copy(stdout, job.getLogsWriter(), Charset.defaultCharset());
                    System.err.println("Copy done !");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            CompletableFuture.runAsync(() -> {
                try {
                    IOUtils.copy(stderr, job.getLogsWriter(), Charset.defaultCharset());
                    System.err.println("Copy done !");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            System.err.println("Writing buffer");
            writable.write(ByteBuffer.wrap(script.getBytes()));
            writable.close();

            // wait for the container to exit
            System.err.println("Waiting container exit");
            var containerExit = dockerClient.waitContainer(containerCreation.id());

            dockerClient.removeContainer(containerCreation.id());

            return Math.toIntExact(containerExit.statusCode());
        } catch (Exception e) {
            e.printStackTrace();
            return 99;
        }
    }

    @Async
    public void apply(Job job, TerraformModule module, Stack stack) {
        this.jobs.put(job.getId(), job);
        job.start();

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
        job.start();

        var planScript = stackCommandBuilder.buildPlanScript(stack, module);

        var result = runContainerForJob(job, planScript);

        if(result == 0){
            // diff is empty
            job.end();
        }
        else if(result == 2){
            // there is a diff, set the status of the stack to : "TO_UPDATE"
            if(StackState.RUNNING != stack.getState()){
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
        return this.jobRepository.findById(jobId).get();
    }
}
