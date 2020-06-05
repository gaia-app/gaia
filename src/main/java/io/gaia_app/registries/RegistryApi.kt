package io.gaia_app.registries

import io.gaia_app.teams.User

interface RegistryApi<T> {

    fun getRepositories(user: User) : List<T>

    fun getRepository(user: User, projectId: String): T

    fun getFileContent(user: User, projectId: String, filename: String): String

}
