package io.codeka.gaia.registries.controller

import io.codeka.gaia.hcl.HclParser
import io.codeka.gaia.modules.bo.TerraformModule
import io.codeka.gaia.modules.bo.Variable
import io.codeka.gaia.modules.repository.TerraformCLIRepository
import io.codeka.gaia.modules.repository.TerraformModuleRepository
import io.codeka.gaia.registries.RegistryType
import io.codeka.gaia.registries.github.GitHubRawContent
import io.codeka.gaia.registries.github.GithubRegistryApi
import io.codeka.gaia.registries.github.GithubRepository
import io.codeka.gaia.teams.User
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.*
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class GithubRegistryControllerTest{

    @Mock
    lateinit var githubRegistryApi: GithubRegistryApi

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
        `when`(moduleRepository.save(any(TerraformModule::class.java))).then { it.arguments[0] }

        `when`(terraformCLIRepository.listCLIVersion()).thenReturn(listOf("1.12.8", "1.12.7"))

        val user = User("juwit", null)

        val githubRepository = GithubRepository("juwit/terraform-docker-mongo","https://github.com/juwit/terraform-docker-mongo")
        `when`(githubRegistryApi.getRepository(user, "juwit", "terraform-docker-mongo")).thenReturn(githubRepository)

        val variablesFileContent = "mock file content"
        `when`(githubRegistryApi.getFileContent(user, "juwit/terraform-docker-mongo", "variables.tf")).thenReturn(variablesFileContent)
        `when`(hclParser.parseVariables(variablesFileContent)).thenReturn(listOf(Variable("dummy")))

        val module = githubRegistryController.importRepository("juwit", "terraform-docker-mongo", user)

        verify(githubRegistryApi).getRepository(user, "juwit", "terraform-docker-mongo")
        verify(githubRegistryApi).getFileContent(user, "juwit/terraform-docker-mongo", "variables.tf")

        verifyNoMoreInteractions(githubRegistryApi)

        assertThat(module.id).isNotBlank()

        assertThat(module.name).isEqualTo("juwit/terraform-docker-mongo")
        assertThat(module.gitRepositoryUrl).isEqualTo("https://github.com/juwit/terraform-docker-mongo")
        assertThat(module.gitBranch).isEqualTo("master")

        assertThat(module.registryDetails.registryType).isEqualTo(RegistryType.GITHUB)
        assertThat(module.registryDetails.projectId).isEqualTo("juwit/terraform-docker-mongo")

        assertThat(module.cliVersion).isEqualTo("1.12.8")
        assertThat(module.createdBy).isEqualTo(user)

        assertThat(module.variables).containsExactly(Variable("dummy"))

    }
}