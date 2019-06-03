package io.codeka.gaia.runner;

import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.LogStream;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.ContainerConfig;
import io.codeka.gaia.bo.Job;
import io.codeka.gaia.bo.Stack;
import io.codeka.gaia.bo.TerraformModule;
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

    private ContainerConfig containerConfig;

    private Map<String, Job> jobs = new HashMap<>();

    @Autowired
    public StackRunner(DockerClient dockerClient, ContainerConfig containerConfig) {
        this.dockerClient = dockerClient;
        this.containerConfig = containerConfig;
    }

    @Async
    public void run(Job job, TerraformModule module, Stack stack) {
        this.jobs.put(job.getId(), job);
        job.start();

        try{
            System.err.println("Create container");
            var containerCreation = dockerClient.createContainer(containerConfig);
            var containerId = containerCreation.id();

            // attach stdin
            System.err.println("Attach container");
            var logStream = dockerClient.attachContainer(containerId, DockerClient.AttachParameter.STDIN, DockerClient.AttachParameter.STDOUT, DockerClient.AttachParameter.STDERR, DockerClient.AttachParameter.STREAM);
            var writable = HttpHijackWorkaround.getOutputStream(logStream, "unix:///var/run/docker.sock");

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

            var backendGenCommand = String.format("echo \"terraform {\n" +
                    "    backend \\\"http\\\" {\n" +
                    "\t\taddress=\\\"http://172.17.0.1:8080/api/state/%s\\\"\n" +
                    "\t}\n" +
                    "}\n\" > backend.tf", stack.getId());

            var varFormatString = " -var \"%s=%s\" ";
            var variablesBuilder = new StringBuilder();

            module.getVariables().forEach(terraformVariable -> {

                var name = terraformVariable.getName();
                String value = terraformVariable.getDefaultValue();
                // try getting the value from the stack
                if(stack.getVariableValues().containsKey(name)){
                    value = stack.getVariableValues().get(name);
                }
                variablesBuilder.append(String.format(varFormatString, name, value));
            });

            stack.getVariableValues().forEach((s, s2) -> {
                variablesBuilder.append(String.format(varFormatString, s, s2));
            });

            var applyCommand = String.format("terraform apply --auto-approve %s", variablesBuilder.toString());

            System.out.println(applyCommand);

            var commands = new String[]{
                    "set -ex",
                    String.format("git clone %s module", module.getGitRepositoryUrl()),
                    "cd module",
                    "echo 'generating backend configuration'",
                    backendGenCommand,
                    "cat backend.tf",
                    "terraform version",
                    "terraform init",
                    applyCommand
            };

            // generate a backend.tf config !

            var commandScript = String.join("\n", commands);

            System.err.println("Writing buffer");
            writable.write(ByteBuffer.wrap(commandScript.getBytes()));
            writable.close();

            // wait for the container to exit
            System.err.println("Waiting container exit");
            var containerExit = dockerClient.waitContainer(containerCreation.id());

            // no need to kill, the container should exit on its own
            // docker.killContainer(containerId);

            // get full logs to validate the output
            String logs;
            try (LogStream stream = dockerClient.logs(containerCreation.id(), DockerClient.LogsParam.stdout(), DockerClient.LogsParam.stderr())) {
                logs = stream.readFully();
            }

            if(containerExit.statusCode() == 0){
                job.end();
                // delete container :)
                dockerClient.removeContainer(containerCreation.id());
            }
            else{
                job.fail();
            }


        } catch (Exception e) {
            job.fail();
            e.printStackTrace();
        }
    }

    public Job getJob(String jobId) {
        return this.jobs.get(jobId);
    }
}
