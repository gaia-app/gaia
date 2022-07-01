package io.gaia_app.registries.controller

import io.gaia_app.modules.bo.TerraformModule
import io.gaia_app.registries.RegistryApi
import io.gaia_app.registries.RegistryType
import io.gaia_app.registries.github.GithubRepository
import io.gaia_app.registries.service.RegistryService
import io.gaia_app.organizations.User
import org.springframework.http.HttpStatus
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/registries/github")
@Secured
class GithubRegistryController(
        private val githubRegistryApi: RegistryApi<GithubRepository>,
        private val registryService: RegistryService) {

    @GetMapping("/repositories")
    fun getRepositories(user: User): List<GithubRepository> {
        return this.githubRegistryApi.getRepositories(user)
    }

    @PostMapping("/repositories/{owner}/{repo}/import")
    @ResponseStatus(HttpStatus.CREATED)
    fun importRepository(@PathVariable owner: String, @PathVariable repo: String, user: User): TerraformModule {
        return registryService.importRepository("$owner/$repo", RegistryType.GITHUB, user)
    }
}
