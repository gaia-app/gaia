package io.codeka.gaia.registries.config;

import io.codeka.gaia.registries.RegistryOAuth2Provider;
import io.codeka.gaia.registries.github.GitHubOAuth2Provider;
import io.codeka.gaia.registries.gitlab.GitLabOAuth2Provider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Config for registry management
 */
@Configuration
public class RegistryConfig {

    @Bean
    List<RegistryOAuth2Provider> registryOAuth2Providers() {
        return List.of(new GitHubOAuth2Provider(), new GitLabOAuth2Provider());
    }
}
