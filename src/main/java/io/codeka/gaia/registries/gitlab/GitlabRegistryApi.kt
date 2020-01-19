package io.codeka.gaia.registries.gitlab

import com.fasterxml.jackson.annotation.JsonAlias
import io.codeka.gaia.registries.AbstractRegistryApi
import io.codeka.gaia.registries.RegistryType
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class GitlabRegistryApi(restTemplate: RestTemplate): AbstractRegistryApi<GitlabRepository>(
        restTemplate,
        RegistryType.GITLAB,
        GitlabRepository::class.java,
        Array<GitlabRepository>::class.java)

data class GitlabRepository(
        @JsonAlias("id") val id: String,
        @JsonAlias("path_with_namespace") val fullName: String,
        @JsonAlias("web_url") val htmlUrl: String)