package io.codeka.gaia.registries.config;

import io.codeka.gaia.registries.RegistryOAuth2Provider;
import io.codeka.gaia.registries.RegistryRawContent;
import io.codeka.gaia.registries.github.GitHubOAuth2Provider;
import io.codeka.gaia.registries.github.GitHubRawContent;
import io.codeka.gaia.registries.gitlab.GitLabOAuth2Provider;
import io.codeka.gaia.registries.gitlab.GitLabRawContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Config for registry management
 */
@Configuration
public class RegistryConfig {

    @Bean
    List<RegistryRawContent> registryRawContents(@Autowired RestTemplate restTemplate) {
        return List.of(new GitHubRawContent(restTemplate), new GitLabRawContent(restTemplate));
    }

    @Bean
    List<RegistryOAuth2Provider> registryOAuth2Providers() {
        return List.of(new GitHubOAuth2Provider(), new GitLabOAuth2Provider());
    }
}
