package io.codeka.gaia.registries.config;

import io.codeka.gaia.config.BeansConfig;
import io.codeka.gaia.registries.RegistryOAuth2Provider;
import io.codeka.gaia.registries.RegistryRawContent;
import io.codeka.gaia.registries.github.GitHubOAuth2Provider;
import io.codeka.gaia.registries.github.GitHubRawContent;
import io.codeka.gaia.registries.github.GithubRegistryApi;
import io.codeka.gaia.registries.gitlab.GitLabOAuth2Provider;
import io.codeka.gaia.registries.gitlab.GitLabRawContent;
import io.codeka.gaia.registries.gitlab.GitlabRegistryApi;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {RegistryConfig.class, BeansConfig.class, GitHubRawContent.class, GitLabRawContent.class, GithubRegistryApi.class, GitlabRegistryApi.class})
class RegistryConfigIT {
    @Test
    void registryRawContents_shouldBeInstantiated(@Autowired(required = false) List<RegistryRawContent> registryRawContents) {
        assertThat(registryRawContents).isNotNull()
                .hasSize(2)
                .extracting("class")
                .contains(GitHubRawContent.class, GitLabRawContent.class);
    }

    @Test
    void registryOAuth2Providers_shouldBeInstantiated(@Autowired(required = false) List<RegistryOAuth2Provider> registryOAuth2Providers) {
        assertThat(registryOAuth2Providers).isNotNull()
                .hasSize(2)
                .extracting("class")
                .contains(GitHubOAuth2Provider.class, GitLabOAuth2Provider.class);
    }
}
