package io.gaia_app.registries.service

import io.gaia_app.hcl.HclParser
import io.gaia_app.modules.bo.ModuleMetadata
import io.gaia_app.modules.bo.TerraformImage
import io.gaia_app.modules.bo.TerraformModule
import io.gaia_app.modules.repository.TerraformModuleRepository
import io.gaia_app.registries.RegistryApi
import io.gaia_app.registries.RegistryDetails
import io.gaia_app.registries.RegistryType
import io.gaia_app.registries.SourceRepository
import io.gaia_app.teams.User
import org.springframework.stereotype.Service
import java.util.*

interface RegistryService {
    fun importRepository(projectId: String, registryType: RegistryType, user: User): TerraformModule
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
        val variablesFile = registryApis[registryType]?.getFileContent(user, projectId, "variables.tf")!!
        module.variables = hclParser.parseVariables(variablesFile)

        // find main provider
        val mainFile = registryApis[registryType]?.getFileContent(user, projectId, "main.tf")!!
        module.mainProvider = hclParser.parseProvider(mainFile)

        // saving module !
        return moduleRepository.save(module)
    }

}
