package io.gaia_app.runner.config;


import io.gaia_app.settings.bo.Settings;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DockerConfigTest {

    @Test
    void dockerClient_shouldUseDockerDaemonUrlSetting() {
        var settings = new Settings();
        settings.setDockerDaemonUrl("tcp://test:2375");

        var dockerConfig = new DockerConfig();

        var dockerClientConfig = dockerConfig.dockerClientConfig(settings);

        assertEquals("tcp://test:2375", dockerClientConfig.getDockerHost().toString());
    }

}
