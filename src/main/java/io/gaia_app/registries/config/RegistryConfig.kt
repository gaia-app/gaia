package io.gaia_app.registries.config

import io.gaia_app.registries.RegistryApi
import io.gaia_app.registries.RegistryOAuth2Provider
import io.gaia_app.registries.RegistryType
import io.gaia_app.registries.SourceRepository
import io.gaia_app.registries.github.GitHubOAuth2Provider
import io.gaia_app.registries.github.GithubRegistryApi
import io.gaia_app.registries.gitlab.GitLabOAuth2Provider
import io.gaia_app.registries.gitlab.GitlabRegistryApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Config for registry management
 */
@Configuration
class RegistryConfig {

    @Bean
    fun registryOAuth2Providers(): List<RegistryOAuth2Provider> {
        return listOf(GitHubOAuth2Provider(), GitLabOAuth2Provider())
    }

    @Bean
    fun registryApis(
            githubRegistryApi: GithubRegistryApi,
            gitlabRegistryApi: GitlabRegistryApi): Map<RegistryType, RegistryApi<out SourceRepository>> {
        return mapOf(Pair(RegistryType.GITHUB, githubRegistryApi),
                Pair(RegistryType.GITLAB, gitlabRegistryApi))
    }
}
