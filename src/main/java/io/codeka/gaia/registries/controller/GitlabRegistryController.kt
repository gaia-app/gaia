package io.codeka.gaia.registries.controller

import io.codeka.gaia.hcl.HclParser
import io.codeka.gaia.modules.bo.TerraformModule
import io.codeka.gaia.modules.repository.TerraformCLIRepository
import io.codeka.gaia.modules.repository.TerraformModuleRepository
import io.codeka.gaia.registries.RegistryApi
import io.codeka.gaia.registries.RegistryDetails
import io.codeka.gaia.registries.RegistryType
import io.codeka.gaia.registries.gitlab.GitlabRepository
import io.codeka.gaia.registries.service.RegistryService
import io.codeka.gaia.teams.User
import org.springframework.http.HttpStatus
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.*
import java.util.*

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

    @GetMapping("/repositories/{projectId}/import")
    @ResponseStatus(HttpStatus.CREATED)
    fun importRepository(@PathVariable projectId: String, user: User): TerraformModule {
        return registryService.importRepository(projectId, RegistryType.GITLAB, user)
    }
}