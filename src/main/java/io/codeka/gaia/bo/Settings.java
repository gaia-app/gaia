package io.codeka.gaia.bo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

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

    public String getExternalUrl() {
        return externalUrl;
    }

    public void setExternalUrl(String externalUrl) {
        this.externalUrl = externalUrl;
    }
}
