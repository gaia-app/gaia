package io.codeka.gaia.registries.controller

import io.codeka.gaia.hcl.HclParser
import io.codeka.gaia.modules.bo.TerraformModule
import io.codeka.gaia.modules.bo.Variable
import io.codeka.gaia.modules.repository.TerraformCLIRepository
import io.codeka.gaia.modules.repository.TerraformModuleRepository
import io.codeka.gaia.registries.RegistryApi
import io.codeka.gaia.registries.RegistryType
import io.codeka.gaia.registries.github.GithubRepository
import io.codeka.gaia.teams.User
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.stubbing.OngoingStubbing
import org.mockito.stubbing.Stubber

@ExtendWith(MockitoExtension::class)
class GithubRegistryControllerTest{

    @Mock
    lateinit var githubRegistryApi: RegistryApi<GithubRepository>

    @Mock
    lateinit var hclParser: HclParser

    @Mock
    lateinit var terraformCLIRepository: TerraformCLIRepository

    @Mock
    lateinit var moduleRepository: TerraformModuleRepository

    @InjectMocks
    lateinit var githubRegistryController: GithubRegistryController

    @Test
    fun `getRepositories() should call the github registry api`() {
        // given
        val john = User("john", null)
        // when
        githubRegistryController.getRepositories(john)
        // then
        verify(githubRegistryApi).getRepositories(john)
    }

    @Test
    fun `importRepository() should call the github registry and create a module`() {
        // returns saved module as first arg
        whenever(moduleRepository.save(any(TerraformModule::class.java))).then { it.arguments[0] }

        whenever(terraformCLIRepository.listCLIVersion()).thenReturn(listOf("1.12.8", "1.12.7"))

        val user = User("juwit", null)

        val githubRepository = GithubRepository("juwit/terraform-docker-mongo", "https://github.com/juwit/terraform-docker-mongo")
        whenever(githubRegistryApi.getRepository(user, "juwit/terraform-docker-mongo")).thenReturn(githubRepository)

        val variablesFileContent = "mock file content"
        whenever(githubRegistryApi.getFileContent(user, "juwit/terraform-docker-mongo", "variables.tf")).thenReturn(variablesFileContent)
        whenever(hclParser.parseVariables(variablesFileContent)).thenReturn(listOf(Variable("dummy")))

        val module = githubRegistryController.importRepository("juwit", "terraform-docker-mongo", user)

        verify(githubRegistryApi).getRepository(user, "juwit/terraform-docker-mongo")
        verify(githubRegistryApi).getFileContent(user, "juwit/terraform-docker-mongo", "variables.tf")

        verifyNoMoreInteractions(githubRegistryApi)

        assertThat(module.id).isNotBlank()

        assertThat(module.name).isEqualTo("juwit/terraform-docker-mongo")
        assertThat(module.gitRepositoryUrl).isEqualTo("https://github.com/juwit/terraform-docker-mongo")
        assertThat(module.gitBranch).isEqualTo("master")

        assertThat(module.registryDetails.registryType).isEqualTo(RegistryType.GITHUB)
        assertThat(module.registryDetails.projectId).isEqualTo("juwit/terraform-docker-mongo")

        assertThat(module.cliVersion).isEqualTo("1.12.8")
        assertThat(module.moduleMetadata.createdBy).isEqualTo(user)

        assertThat(module.variables).containsExactly(Variable("dummy"))

    }
}

inline fun <T> whenever(methodCall: T): OngoingStubbing<T> {
    return Mockito.`when`(methodCall)!!
}