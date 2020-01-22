package io.codeka.gaia.registries.controller

import io.codeka.gaia.hcl.HclParser
import io.codeka.gaia.modules.bo.ModuleMetadata
import io.codeka.gaia.modules.bo.TerraformModule
import io.codeka.gaia.modules.repository.TerraformCLIRepository
import io.codeka.gaia.modules.repository.TerraformModuleRepository
import io.codeka.gaia.registries.RegistryApi
import io.codeka.gaia.registries.RegistryDetails
import io.codeka.gaia.registries.RegistryType
import io.codeka.gaia.registries.github.GithubRepository
import io.codeka.gaia.teams.User
import org.springframework.http.HttpStatus
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/registries/github")
@Secured
class GithubRegistryController(
        private val githubRegistryApi: RegistryApi<GithubRepository>,
        private val hclParser: HclParser,
        private val cliRepository: TerraformCLIRepository,
        private val moduleRepository: TerraformModuleRepository) {

    @GetMapping("/repositories")
    fun getRepositories(user: User): List<GithubRepository> {
        return this.githubRegistryApi.getRepositories(user)
    }

    @GetMapping("/repositories/{owner}/{repo}/import")
    @ResponseStatus(HttpStatus.CREATED)
    fun importRepository(@PathVariable owner: String, @PathVariable repo: String, user: User): TerraformModule {
        val module = TerraformModule()
        module.id = UUID.randomUUID().toString()

        val githubRepository = githubRegistryApi.getRepository(user, "$owner/$repo")

        module.gitRepositoryUrl = githubRepository.htmlUrl
        module.gitBranch = "master"
        module.name = githubRepository.fullName
        module.cliVersion = cliRepository.listCLIVersion().first()

        module.registryDetails = RegistryDetails(RegistryType.GITHUB, githubRepository.fullName)
        module.moduleMetadata = ModuleMetadata(createdBy = user)

        // get variables
        val variablesFile = githubRegistryApi.getFileContent(user, "$owner/$repo", "variables.tf")
        module.variables = hclParser.parseVariables(variablesFile)

        // saving module !
        return moduleRepository.save(module)
    }
}