package io.codeka.gaia.registries.config

import io.codeka.gaia.registries.RegistryApi
import io.codeka.gaia.registries.RegistryOAuth2Provider
import io.codeka.gaia.registries.RegistryType
import io.codeka.gaia.registries.SourceRepository
import io.codeka.gaia.registries.github.GitHubOAuth2Provider
import io.codeka.gaia.registries.github.GithubRegistryApi
import io.codeka.gaia.registries.gitlab.GitLabOAuth2Provider
import io.codeka.gaia.registries.gitlab.GitlabRegistryApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Config for registry management
 */
@Configuration
open class RegistryConfig {

    @Bean
    open fun registryOAuth2Providers(): List<RegistryOAuth2Provider> {
        return listOf(GitHubOAuth2Provider(), GitLabOAuth2Provider())
    }

    @Bean
    open fun registryApis(
            githubRegistryApi: GithubRegistryApi,
            gitlabRegistryApi: GitlabRegistryApi): Map<RegistryType, RegistryApi<out SourceRepository>> {
        return mapOf(Pair(RegistryType.GITHUB, githubRegistryApi),
                Pair(RegistryType.GITLAB, gitlabRegistryApi))
    }
}