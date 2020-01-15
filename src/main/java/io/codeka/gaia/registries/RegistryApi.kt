package io.codeka.gaia.registries

import io.codeka.gaia.registries.github.GithubRepository
import io.codeka.gaia.teams.User
import org.springframework.web.client.RestTemplate

interface RegistryApi<T> {

    fun getRepositories(user: User) : List<String>

    fun getRepository(user: User, owner: String, repo: String): T

    fun getFileContent(user: User, projectId: String, filename: String): String

}