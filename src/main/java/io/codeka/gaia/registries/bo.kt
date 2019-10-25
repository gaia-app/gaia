package io.codeka.gaia.registries

enum class RegistryType(val readmeUrl: String){
    GITHUB("https://api.github.com/repos/{id}/contents/README.md?ref=master"),
    GITLAB("https://gitlab.com/api/v4/projects/{id}/repository/files/README.md?ref=master")
}

data class RegistryDetails(val registryType: RegistryType, val projectId: String)

data class RegistryFile(val content: String)