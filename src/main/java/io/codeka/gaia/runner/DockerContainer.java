package io.codeka.gaia.runner;

import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.LogStream;
import com.spotify.docker.client.exceptions.DockerException;

import java.io.IOException;
import java.io.OutputStream;

public class DockerContainer {

    private final String id;

    private final HttpHijackWorkaround httpHijackWorkaround;

    private DockerClient dockerClient;

    private LogStream stream;

    DockerContainer(final String id, DockerClient dockerClient, HttpHijackWorkaround httpHijackWorkaround) {
        this.id = id;
        this.dockerClient = dockerClient;
        this.httpHijackWorkaround = httpHijackWorkaround;
    }

    private void ensureAttached() throws StackRunnerException {
        if (this.stream != null) {
            return;
        }

        try {
            this.stream = dockerClient
                    .attachContainer(this.getId(), DockerClient.AttachParameter.STREAM, DockerClient.AttachParameter.STDIN,
                            DockerClient.AttachParameter.STDOUT, DockerClient.AttachParameter.STDERR);
        } catch (DockerException e) {
            throw new StackRunnerException("unable to attach to the container", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new StackRunnerException("unable to attach to the container, the thread was interrupted", e);
        }
    }

    OutputStream getStdIn() throws StackRunnerException {
        try {
            this.ensureAttached();
            return httpHijackWorkaround.getOutputStream(this.stream, "unix:///var/apply/docker.sock");
        } catch (IOException e) {
            throw new StackRunnerException("unable to get container std in", e);
        }
    }

    void attach(final OutputStream stdOut, final OutputStream stdErr) throws StackRunnerException {
        try {
            this.ensureAttached();
            this.stream.attach(stdOut, stdErr, true);
        } catch (IOException e) {
            throw new StackRunnerException("unable to attach to container std out and std err", e);
        }
    }

    public String getId() {
        return this.id;
    }

}
