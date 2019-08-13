package io.codeka.gaia.runner.config;

import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.messages.ContainerConfig;
import com.spotify.docker.client.messages.HostConfig;
import io.codeka.gaia.settings.bo.Settings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration of the docker client
 */
@Configuration
public class DockerConfig {

    /**
     * builds the docker client
     * @param settings the Gaia's settings
     * @return a docker client
     */
    @Bean
    DockerClient client(Settings settings) {
        return DefaultDockerClient
                .builder()
                .uri(settings.getDockerDaemonUrl())
                .build();
    }

    @Bean
    ContainerConfig.Builder containerConfig(){
        return ContainerConfig.builder()
                // bind mounting the docker sock (to be able to use docker provider in terraform)
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
