package io.codeka.gaia.registries

import io.codeka.gaia.registries.github.GithubRepository
import io.codeka.gaia.teams.User
import org.springframework.web.client.RestTemplate

interface RegistryApi<T> {

    fun getRepositories(user: User) : List<T>

    fun getRepository(user: User, projectId: String): T

    fun getFileContent(user: User, projectId: String, filename: String): String

}