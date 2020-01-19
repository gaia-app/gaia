package io.codeka.gaia.registries.github

import com.fasterxml.jackson.annotation.JsonAlias
import io.codeka.gaia.registries.AbstractRegistryApi
import io.codeka.gaia.registries.RegistryType
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class GithubRegistryApi(restTemplate: RestTemplate): AbstractRegistryApi<GithubRepository>(
        restTemplate,
        RegistryType.GITHUB,
        GithubRepository::class.java,
        Array<GithubRepository>::class.java)

data class GithubRepository(
        @JsonAlias("full_name") val fullName: String,
        @JsonAlias("html_url") val htmlUrl: String)
