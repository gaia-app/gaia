package io.gaia_app.registries

import io.gaia_app.organizations.User

interface RegistryApi<T> {

    fun getRepositories(user: User) : List<T>

    /**
     * Gets a repository data for an authenticated user
     */
    fun getRepository(user: User, projectId: String): T

    /**
     * Gets a repository data for an anonymous user
     */
    fun getRepository(projectId: String): T

    fun getFileContent(user: User, projectId: String, filename: String): String

}
