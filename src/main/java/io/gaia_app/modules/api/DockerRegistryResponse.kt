package io.gaia_app.modules.api

import com.fasterxml.jackson.annotation.JsonAlias

sealed class DockerRegistryResponseResult

data class DockerRegistryResponse<DockerHubResponseResult>(val results: List<DockerHubResponseResult>)

data class DockerRegistryRepository(
        @JsonAlias("repo_name") val name: String,
        @JsonAlias("short_description") val description: String) : DockerRegistryResponseResult()

data class DockerRegistryRepositoryTag(@JsonAlias("name") val name: String) : DockerRegistryResponseResult()
