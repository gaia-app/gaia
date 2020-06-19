package io.gaia_app.runner;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.async.ResultCallback;
import com.github.dockerjava.api.command.CreateContainerCmd;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.exception.DockerException;
import com.github.dockerjava.api.model.*;
import io.gaia_app.settings.bo.Settings;
import io.gaia_app.stacks.workflow.JobWorkflow;
import org.apache.commons.io.output.WriterOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Closeable;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * Service to run docker container
 */
@Service
public class DockerRunner {

    private static final Logger LOG = LoggerFactory.getLogger(DockerRunner.class);

    private DockerClient dockerClient;

    private Settings settings;

    @Autowired
    public DockerRunner(DockerClient dockerClient, Settings settings) {
        this.dockerClient = dockerClient;
        this.settings = settings;
    }

    int runContainerForJob(JobWorkflow jobWorkflow, String script) {
        try {
            var env = new ArrayList<String>();
            env.add("TF_IN_AUTOMATION=true");
            env.addAll(settings.env());

            var job = jobWorkflow.getJob();

            // add credentials of the job, if any
            if (job.getCredentials() != null){
                env.addAll(job.getCredentials().toEnv());
            }

            // pulling the image
            dockerClient.pullImageCmd(job.getTerraformImage().image())
                .start()
                .awaitCompletion();

            // create a new container
            var createContainerCmd = dockerClient.createContainerCmd(job.getTerraformImage().image())
                // resetting entrypoint to empty
                .withEntrypoint(new String[]{})
                // using /bin/sh as command
                .withCmd("/bin/sh")
                // using env vars
                .withEnv(env)
                // open stdin
                .withTty(false)
                .withStdinOpen(true)
                // bind mount docker sock (to be able to use docker provider in terraform)
                .withHostConfig(HostConfig.newHostConfig().withBinds(new Bind("/var/run/docker.sock", new Volume("/var/run/docker.sock"))));

            var container = createContainerCmd.exec();
            var containerId = container.getId();

            // start the container
            dockerClient.startContainerCmd(containerId).exec();

            // attaching the outputs in a background thread
            var step = jobWorkflow.getCurrentStep();

            // write the content of the script to the container's std in
            try (
                var out = new PipedOutputStream();
                var in = new PipedInputStream(out);
                var containerLogsCallback = new ResultCallback.Adapter<Frame>(){
                    @Override
                    public void onNext(Frame frame) {
                        try {
                            step.getLogsWriter().write(new String(frame.getPayload()));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        super.onNext(frame);
                    }
                };
                ){
                dockerClient.attachContainerCmd(container.getId())
                    .withFollowStream(true)
                    .withStdOut(true)
                    .withStdErr(true)
                    .withStdIn(in)
                    .exec(containerLogsCallback);

                out.write((script + "\n").getBytes());
                out.flush();

                containerLogsCallback.awaitCompletion(1800, TimeUnit.SECONDS);
            }

            // wait for the container to exit
            var containerExit = dockerClient.waitContainerCmd(containerId)
                .start()
                // with a timeout of 30 minutes
                .awaitStatusCode(1800, TimeUnit.SECONDS);

            dockerClient.removeContainerCmd(containerId).exec();

            return containerExit;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            LOG.error("Interrupted Exception", e);
            return 99;
        } catch (DockerException | IOException e) {
            LOG.error("Exception when running job", e);
            return 99;
        }
    }

}
