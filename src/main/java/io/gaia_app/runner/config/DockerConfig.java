package io.gaia_app.runner.config;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.okhttp.OkHttpDockerCmdExecFactory;
import io.gaia_app.settings.bo.Settings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration of the docker transport
 */
@Configuration
public class DockerConfig {

    /**
     * builds the DockerClientConfig based on Gaia's settings
     * @param settings Gaia's settings
     * @return a docker client configuration
     */
    @Bean
    DockerClientConfig dockerClientConfig(Settings settings) {
        return DefaultDockerClientConfig.createDefaultConfigBuilder()
            .withDockerHost(settings.getDockerDaemonUrl())
            .build();
    }

    /**
     * builds the docker client
     * @param config the configuration (host/registries...)
     * @return
     */
    @Bean
    DockerClient client(DockerClientConfig config){
        return DockerClientImpl.getInstance(config)
            .withDockerCmdExecFactory(new OkHttpDockerCmdExecFactory());
    }

}
