package io.codeka.gaia.registries.controller

import io.codeka.gaia.modules.bo.TerraformModule
import io.codeka.gaia.registries.RegistryApi
import io.codeka.gaia.registries.RegistryType
import io.codeka.gaia.registries.github.GithubRepository
import io.codeka.gaia.registries.service.RegistryService
import io.codeka.gaia.teams.User
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
