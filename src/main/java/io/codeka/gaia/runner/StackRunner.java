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

    @Async
    public void run(Job job, TerraformModule module, Stack stack) {
        this.jobs.put(job.getId(), job);
        job.start();

        try{
            // FIXME This is certainly no thread safe
            var containerConfig = containerConfigBuilder
                    .env(settings.env())
                    .build();

            System.out.println("Create container");
            var containerCreation = dockerClient.createContainer(containerConfig);
            var containerId = containerCreation.id();

            // attach stdin
            System.err.println("Attach container");
            var logStream = dockerClient.attachContainer(containerId, DockerClient.AttachParameter.STDIN, DockerClient.AttachParameter.STDOUT, DockerClient.AttachParameter.STDERR, DockerClient.AttachParameter.STREAM);
            var writable = httpHijackWorkaround.getOutputStream(logStream, "unix:///var/run/docker.sock");

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
                    // run another attachment for stdout and stderr (who knows why?)
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

            var commandScript = stackCommandBuilder.buildApplyScript(stack, module);

            System.err.println("Writing buffer");
            writable.write(ByteBuffer.wrap(commandScript.getBytes()));
            writable.close();

            // wait for the container to exit
            System.err.println("Waiting container exit");
            var containerExit = dockerClient.waitContainer(containerCreation.id());

            // no need to kill, the container should exit on its own
            // docker.killContainer(containerId);

            // get full logs to validate the output
            // String logs;
            // try (LogStream stream = dockerClient.logs(containerCreation.id(), DockerClient.LogsParam.stdout(), DockerClient.LogsParam.stderr())) {
            //     logs = stream.readFully();
            // }

            if(containerExit.statusCode() == 0){
                job.end();
                // delete container :)
                dockerClient.removeContainer(containerCreation.id());

                // update stack information
                stack.setState(StackState.RUNNING);
                stackRepository.save(stack);
            }
            else{
                job.fail();
            }

            // save job to database
            jobRepository.save(job);

        } catch (Exception e) {
            job.fail();
            e.printStackTrace();
        }
    }

    public Job getJob(String jobId) {
        return this.jobs.get(jobId);
    }
}
