package io.codeka.gaia.registries.gitlab

import com.fasterxml.jackson.annotation.JsonAlias
import io.codeka.gaia.registries.AbstractRegistryApi
import io.codeka.gaia.registries.RegistryType
import io.codeka.gaia.registries.SourceRepository
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class GitlabRegistryApi(restTemplate: RestTemplate): AbstractRegistryApi<GitlabRepository>(
        restTemplate,
        RegistryType.GITLAB,
        GitlabRepository::class.java,
        Array<GitlabRepository>::class.java)

/**
 * Gitlab source repository implementation
 */
data class GitlabRepository(
        @JsonAlias("id") override val id: String,
        @JsonAlias("path_with_namespace") override val fullName: String,
        @JsonAlias("web_url") override val htmlUrl: String): SourceRepository
