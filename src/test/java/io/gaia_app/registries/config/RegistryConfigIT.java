package io.gaia_app.registries.config;

import io.gaia_app.registries.RegistryOAuth2Provider;
import io.gaia_app.registries.RegistryRawContent;
import io.gaia_app.registries.github.GitHubOAuth2Provider;
import io.gaia_app.registries.github.GitHubRawContent;
import io.gaia_app.registries.github.GithubRegistryApi;
import io.gaia_app.registries.gitlab.GitLabOAuth2Provider;
import io.gaia_app.registries.gitlab.GitLabRawContent;
import io.gaia_app.registries.gitlab.GitlabRegistryApi;
import io.gaia_app.config.BeansConfig;
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
