package io.codeka.gaia.runner.config;

import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.ContainerConfig;
import com.spotify.docker.client.messages.HostConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration of the docker client
 */
@Configuration
public class DockerConfig {

    /**
     * builds the docker client
     * @return
     */
    @Bean
    DockerClient client() throws DockerException, InterruptedException {
        var dockerClient = DefaultDockerClient
                .builder()
                .uri("unix:///var/run/docker.sock")
                .build();
        // also pull the image
        dockerClient.pull("hashicorp/terraform:0.11.14");
        return dockerClient;
    }

    @Bean
    ContainerConfig.Builder containerConfig(){
        return ContainerConfig.builder()
                .image("hashicorp/terraform:0.11.14")
                .hostConfig(HostConfig.builder().binds(HostConfig.Bind.builder().from("/var/run/docker.sock").to("/var/run/docker.sock").build()).build())
                // resetting entrypoint to empty
                .entrypoint()
                // and using a simple shell as command
                .cmd("/bin/sh")
                .attachStdin(true)
                .attachStdout(true)
                .attachStderr(true)
                .stdinOnce(true)
                .openStdin(true)
                .tty(false);
    }

}
