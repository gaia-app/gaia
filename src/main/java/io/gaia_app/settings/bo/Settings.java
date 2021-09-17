package io.gaia_app.settings.bo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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
    private String externalUrl;

    /**
     * Environment variables for the runner
     */
    private List<EnvVar> envVars = new ArrayList<>();

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

    /**
     * Merging two settings objets
     * @param saved the settings to merge
     */
    public void merge(Settings saved) {
        if (saved.externalUrl != null) {
            this.externalUrl = saved.externalUrl;
        }
        if (saved.envVars != null) {
            this.envVars.addAll(saved.envVars);
        }
    }

    public static class EnvVar{
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
