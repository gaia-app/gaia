package io.codeka.gaia.registries.controller

import io.codeka.gaia.hcl.HclParser
import io.codeka.gaia.modules.bo.TerraformModule
import io.codeka.gaia.modules.repository.TerraformCLIRepository
import io.codeka.gaia.modules.repository.TerraformModuleRepository
import io.codeka.gaia.registries.RegistryApi
import io.codeka.gaia.registries.RegistryDetails
import io.codeka.gaia.registries.RegistryType
import io.codeka.gaia.registries.gitlab.GitlabRepository
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
        private val hclParser: HclParser,
        private val cliRepository: TerraformCLIRepository,
        private val moduleRepository: TerraformModuleRepository) {

    @GetMapping("/repositories")
    fun getRepositories(user: User): List<GitlabRepository> {
        return this.gitlabRegistryApi.getRepositories(user)
    }

    @GetMapping("/repositories/{projectId}/import")
    @ResponseStatus(HttpStatus.CREATED)
    fun importRepository(@PathVariable projectId: String, user: User): TerraformModule {
        val module = TerraformModule()
        module.id = UUID.randomUUID().toString()

        val gitlabRepository = gitlabRegistryApi.getRepository(user, projectId)

        module.gitRepositoryUrl = gitlabRepository.htmlUrl
        module.gitBranch = "master"
        module.name = gitlabRepository.fullName
        module.cliVersion = cliRepository.listCLIVersion().first()

        module.registryDetails = RegistryDetails(RegistryType.GITLAB, gitlabRepository.id)
        module.createdBy = user

        // get variables
        val variablesFile = gitlabRegistryApi.getFileContent(user, projectId, "variables.tf")
        module.variables = hclParser.parseVariables(variablesFile)

        // saving module !
        return moduleRepository.save(module)
    }
}