package io.codeka.gaia.bo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
