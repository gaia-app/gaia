package io.gaia_app.registries.service

import io.gaia_app.hcl.HclParser
import io.gaia_app.modules.bo.*
import io.gaia_app.modules.repository.TerraformModuleRepository
import io.gaia_app.registries.RegistryApi
import io.gaia_app.registries.RegistryDetails
import io.gaia_app.registries.RegistryType
import io.gaia_app.registries.SourceRepository
import io.gaia_app.organizations.User
import org.springframework.stereotype.Service
import java.util.*

interface RegistryService {
    fun importRepository(projectId: String, registryType: RegistryType, user: User): TerraformModule

    /**
     * import variables definition from a repository, by reading the variables.tf file and parsing it
     */
    fun importVariables(projectId: String, registryType: RegistryType, user: User): List<Variable>

    /**
     * import outputs definition from a repository, by reading the outputs.tf file and parsing it
     */
    fun importOutputs(projectId: String, registryType: RegistryType, user: User): List<Output>
}

@Service
class RegistryServiceImpl(
        private val hclParser: HclParser,
        private val moduleRepository: TerraformModuleRepository,
        private val registryApis: Map<RegistryType, RegistryApi<out SourceRepository>>
) : RegistryService {

    override fun importRepository(projectId: String, registryType: RegistryType, user: User): TerraformModule {
        val module = TerraformModule()
        module.id = UUID.randomUUID().toString()

        val codeRepository = registryApis[registryType]?.getRepository(user, projectId)!!

        module.gitRepositoryUrl = codeRepository.htmlUrl
        module.gitBranch = "master"
        module.name = codeRepository.fullName
        module.terraformImage = TerraformImage.defaultInstance()

        module.registryDetails = RegistryDetails(registryType, codeRepository.id)
        module.moduleMetadata = ModuleMetadata(createdBy = user)

        // get variables
        module.variables = this.importVariables(projectId, registryType, user)

        // get outputs
        module.outputs = this.importOutputs(projectId, registryType, user)

        // find main provider
        val mainFile = registryApis[registryType]?.getFileContent(user, projectId, "main.tf")!!
        module.mainProvider = hclParser.parseProvider(mainFile)

        // saving module !
        return moduleRepository.save(module)
    }

    override fun importVariables(projectId: String, registryType: RegistryType, user: User): List<Variable> {
        val variablesFile = registryApis[registryType]?.getFileContent(user, projectId, "variables.tf")!!
        return hclParser.parseVariables(variablesFile)
    }

    override fun importOutputs(projectId: String, registryType: RegistryType, user: User): List<Output> {
        val outputsFile = registryApis[registryType]?.getFileContent(user, projectId, "outputs.tf")!!
        return hclParser.parseOutputs(outputsFile)
    }
}
