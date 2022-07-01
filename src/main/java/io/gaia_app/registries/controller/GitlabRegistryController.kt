package io.gaia_app.registries.controller

import io.gaia_app.modules.bo.TerraformModule
import io.gaia_app.registries.RegistryApi
import io.gaia_app.registries.RegistryType
import io.gaia_app.registries.gitlab.GitlabRepository
import io.gaia_app.registries.service.RegistryService
import io.gaia_app.organizations.User
import org.springframework.http.HttpStatus
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/registries/gitlab")
@Secured
class GitlabRegistryController(
        private val gitlabRegistryApi: RegistryApi<GitlabRepository>,
        private val registryService: RegistryService) {

    @GetMapping("/repositories")
    fun getRepositories(user: User): List<GitlabRepository> {
        return this.gitlabRegistryApi.getRepositories(user)
    }

    @PostMapping("/repositories/{projectId}/import")
    @ResponseStatus(HttpStatus.CREATED)
    fun importRepository(@PathVariable projectId: String, user: User): TerraformModule {
        return registryService.importRepository(projectId, RegistryType.GITLAB, user)
    }
}
