package io.gaia_app.registries

enum class RegistryType(val repositoriesUrl: String,
                        val repositoryUrl: String,
                        val fileContentUrl: String,
                        val readmeUrl: String){

    GITHUB("https://api.github.com/user/repos?visibility=public&per_page=100",
            "https://api.github.com/repos/%s",
            "https://api.github.com/repos/%s/contents/%s",
            "https://api.github.com/repos/{id}/contents/README.md"),

    GITLAB("https://gitlab.com/api/v4/projects?visibility=public&owned=true",
            "https://gitlab.com/api/v4/projects/%s",
            "https://gitlab.com/api/v4/projects/%s/repository/files/%s?ref=master",
            "https://gitlab.com/api/v4/projects/{id}/repository/files/README.md?ref=master")

}

data class RegistryDetails(val registryType: RegistryType, val projectId: String)

data class RegistryFile(val content: String)

/**
 * Common class for source code repositories
 */
interface SourceRepository {
    // The id of the repository (used for API calls)
    val id: String
    // The fullName of the repository is used for display
    val fullName: String
    // The fullName of the repository is used for navigation and cloning
    val htmlUrl: String
}
