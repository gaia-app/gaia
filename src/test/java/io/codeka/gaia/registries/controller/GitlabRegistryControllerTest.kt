package io.codeka.gaia.registries.controller

import io.codeka.gaia.hcl.HclParser
import io.codeka.gaia.modules.bo.TerraformModule
import io.codeka.gaia.modules.bo.Variable
import io.codeka.gaia.modules.repository.TerraformCLIRepository
import io.codeka.gaia.modules.repository.TerraformModuleRepository
import io.codeka.gaia.registries.RegistryApi
import io.codeka.gaia.registries.RegistryType
import io.codeka.gaia.registries.github.GithubRepository
import io.codeka.gaia.registries.gitlab.GitlabRepository
import io.codeka.gaia.teams.User
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class GitlabRegistryControllerTest{

    @Mock
    lateinit var gitlabRegistryApi: RegistryApi<GitlabRepository>

    @Mock
    lateinit var hclParser: HclParser

    @Mock
    lateinit var terraformCLIRepository: TerraformCLIRepository

    @Mock
    lateinit var moduleRepository: TerraformModuleRepository

    @InjectMocks
    lateinit var gitlabRegistryController: GitlabRegistryController

    @Test
    fun `getRepositories() should call the gitlab registry api`() {
        // given
        val john = User("john", null)
        // when
        gitlabRegistryController.getRepositories(john)
        // then
        verify(gitlabRegistryApi).getRepositories(john)
    }

    @Test
    fun `importRepository() should call the gitlab registry and create a module`() {
        // returns saved module as first arg
        whenever(moduleRepository.save(any(TerraformModule::class.java))).then { it.arguments[0] }

        whenever(terraformCLIRepository.listCLIVersion()).thenReturn(listOf("1.12.8", "1.12.7"))

        val user = User("juwit", null)

        val gitlabRepository = GitlabRepository("15689", "juwit/terraform-docker-mongo", "https://gitlab.com/juwit/terraform-docker-mongo")
        whenever(gitlabRegistryApi.getRepository(user, "15689")).thenReturn(gitlabRepository)

        val variablesFileContent = "mock file content"
        whenever(gitlabRegistryApi.getFileContent(user, "15689", "variables.tf")).thenReturn(variablesFileContent)
        whenever(hclParser.parseVariables(variablesFileContent)).thenReturn(listOf(Variable("dummy")))

        val module = gitlabRegistryController.importRepository("15689", user)

        verify(gitlabRegistryApi).getRepository(user, "15689")
        verify(gitlabRegistryApi).getFileContent(user, "15689", "variables.tf")

        verifyNoMoreInteractions(gitlabRegistryApi)

        assertThat(module.id).isNotBlank()

        assertThat(module.name).isEqualTo("juwit/terraform-docker-mongo")
        assertThat(module.gitRepositoryUrl).isEqualTo("https://gitlab.com/juwit/terraform-docker-mongo")
        assertThat(module.gitBranch).isEqualTo("master")

        assertThat(module.registryDetails.registryType).isEqualTo(RegistryType.GITLAB)
        assertThat(module.registryDetails.projectId).isEqualTo("15689")

        assertThat(module.cliVersion).isEqualTo("1.12.8")
        assertThat(module.createdBy).isEqualTo(user)

        assertThat(module.variables).containsExactly(Variable("dummy"))

    }
}