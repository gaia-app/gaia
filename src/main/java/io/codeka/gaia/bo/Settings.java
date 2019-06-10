package io.codeka.gaia.bo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Gaia settings
 */
@ConfigurationProperties(prefix = "gaia")
@Component
public class Settings {

    /**
     * Gaia's external url (used to allow runners to speak to Gaia)
     */
    @Value("${:http://localhost:${server.port:8080}}")
    private String externalUrl;

    /**
     * Environment variables for the runner
     */
    private List<EnvVar> envVars = Collections.emptyList();

    /**
     * The docker daemon url used by Gaia to spawn its runners
     */
    @Value(("${:unix:///var/run/docker.sock}"))
    private String dockerDaemonUrl;

    public String getExternalUrl() {
        return externalUrl;
    }

    public void setExternalUrl(String externalUrl) {
        this.externalUrl = externalUrl;
    }

    public List<EnvVar> getEnvVars() {
        return envVars;
    }

    public void setEnvVars(List<EnvVar> envVars) {
        this.envVars = envVars;
    }

    public List<String> env() {
        return this.envVars.stream()
                .map(envVar -> envVar.name + "=" + envVar.value)
                .collect(Collectors.toList());
    }

    public String getDockerDaemonUrl() {
        return dockerDaemonUrl;
    }

    public void setDockerDaemonUrl(String dockerDaemonUrl) {
        this.dockerDaemonUrl = dockerDaemonUrl;
    }

    static class EnvVar{
        String name;
        String value;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
