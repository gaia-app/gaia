package io.codeka.gaia.runner;

import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.ContainerConfig;
import io.codeka.gaia.settings.bo.Settings;
import io.codeka.gaia.stacks.workflow.JobWorkflow;
import org.apache.commons.io.output.WriterOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

/**
 * Service to run docker container
 */
@Service
public class DockerRunner {

    private static final Logger LOG = LoggerFactory.getLogger(DockerRunner.class);

    private DockerClient dockerClient;
    private ContainerConfig.Builder containerConfigBuilder;
    private HttpHijackWorkaround httpHijackWorkaround;
    private Settings settings;

    @Autowired
    public DockerRunner(DockerClient dockerClient, ContainerConfig.Builder containerConfigBuilder,
                        HttpHijackWorkaround httpHijackWorkaround, Settings settings) {
        this.dockerClient = dockerClient;
        this.containerConfigBuilder = containerConfigBuilder;
        this.httpHijackWorkaround = httpHijackWorkaround;
        this.settings = settings;
    }

    int runContainerForJob(JobWorkflow jobWorkflow, String script) {
        try {
            var env = new ArrayList<String>();
            env.add("TF_IN_AUTOMATION=true");
            env.addAll(settings.env());

            var job = jobWorkflow.getJob();

            // FIXME This is certainly no thread safe !
            var containerConfig = containerConfigBuilder
                    .env(env)
                    .image(job.getTerraformImage().image())
                    .build();

            // pull the image
            dockerClient.pull(containerConfig.image());

            var containerCreation = dockerClient.createContainer(containerConfig);
            var containerId = containerCreation.id();

            var dockerContainer = new DockerContainer(containerId, dockerClient, httpHijackWorkaround);

            dockerClient.startContainer(containerId);

            // attaching the outputs in a background thread
            var step = jobWorkflow.getCurrentStep();
            CompletableFuture.runAsync(() -> {
                try (var writerOutputStream = new WriterOutputStream(step.getLogsWriter(), Charset.defaultCharset())) {
                    // this code is blocking I/O !
                    dockerContainer.attach(writerOutputStream, writerOutputStream);
                } catch (IOException | StackRunnerException e) {
                    LOG.error("Unable to attach logs of container", e);
                }
            });

            // write the content of the script to the container's std in
            try (WritableByteChannel stdIn = Channels.newChannel(dockerContainer.getStdIn())) {
                stdIn.write(ByteBuffer.wrap(script.getBytes()));
            }

            // wait for the container to exit
            var containerExit = dockerClient.waitContainer(containerCreation.id());

            dockerClient.removeContainer(containerCreation.id());

            return Math.toIntExact(containerExit.statusCode());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            LOG.error("Interrupted Exception", e);
            return 99;
        } catch (DockerException | IOException | StackRunnerException e) {
            LOG.error("Exception when running job", e);
            return 99;
        }
    }

}
