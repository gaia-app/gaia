package io.codeka.gaia.registries.service

import io.codeka.gaia.hcl.HclParser
import io.codeka.gaia.modules.bo.TerraformModule
import io.codeka.gaia.modules.bo.Variable
import io.codeka.gaia.modules.repository.TerraformModuleRepository
import io.codeka.gaia.registries.RegistryApi
import io.codeka.gaia.registries.RegistryType
import io.codeka.gaia.registries.github.GithubRepository
import io.codeka.gaia.registries.gitlab.GitlabRepository
import io.codeka.gaia.teams.User
import io.codeka.gaia.test.whenever
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class RegistryServiceImplTest {

    @Mock
    lateinit var gitlabRegistryApi: RegistryApi<GitlabRepository>

    @Mock
    lateinit var githubRegistryApi: RegistryApi<GithubRepository>

    @Mock
    lateinit var hclParser: HclParser

    @Mock
    lateinit var moduleRepository: TerraformModuleRepository

    lateinit var registryService: RegistryServiceImpl

    @BeforeEach
    internal fun setUp() {
        registryService = RegistryServiceImpl(
                hclParser,
                moduleRepository,
                mapOf(Pair(RegistryType.GITHUB, githubRegistryApi),
                        Pair(RegistryType.GITLAB, gitlabRegistryApi)))
    }

    @Test
    fun `importRepository() should call the gitlab registry and create a module`() {
        // returns saved module as first arg
        whenever(moduleRepository.save(ArgumentMatchers.any(TerraformModule::class.java))).then { it.arguments[0] }

        val user = User("juwit", null)

        val gitlabRepository = GitlabRepository("15689", "juwit/terraform-docker-mongo", "https://gitlab.com/juwit/terraform-docker-mongo")
        whenever(gitlabRegistryApi.getRepository(user, "15689")).thenReturn(gitlabRepository)

        val variablesFileContent = "mock file content"
        whenever(gitlabRegistryApi.getFileContent(user, "15689", "variables.tf")).thenReturn(variablesFileContent)
        whenever(hclParser.parseVariables(variablesFileContent)).thenReturn(listOf(Variable("dummy")))

        whenever(gitlabRegistryApi.getFileContent(user, "15689", "main.tf")).thenReturn(variablesFileContent)
        whenever(hclParser.parseProvider(variablesFileContent)).thenReturn("docker")

        val module = registryService.importRepository("15689", RegistryType.GITLAB, user)

        verify(gitlabRegistryApi).getRepository(user, "15689")
        verify(gitlabRegistryApi).getFileContent(user, "15689", "variables.tf")

        verifyNoMoreInteractions(gitlabRegistryApi)

        assertThat(module.id).isNotBlank()

        assertThat(module.name).isEqualTo("juwit/terraform-docker-mongo")
        assertThat(module.gitRepositoryUrl).isEqualTo("https://gitlab.com/juwit/terraform-docker-mongo")
        assertThat(module.gitBranch).isEqualTo("master")

        assertThat(module.registryDetails.registryType).isEqualTo(RegistryType.GITLAB)
        assertThat(module.registryDetails.projectId).isEqualTo("15689")

        assertThat(module.terraformImage.tag).isEqualTo("latest")
        assertThat(module.moduleMetadata.createdBy).isEqualTo(user)

        assertThat(module.mainProvider).isEqualTo("docker")

        assertThat(module.variables).containsExactly(Variable("dummy"))
    }

    @Test
    fun `importRepository() should call the github registry and create a module`() {
        // returns saved module as first arg
        whenever(moduleRepository.save(ArgumentMatchers.any(TerraformModule::class.java))).then { it.arguments[0] }

        val user = User("juwit", null)

        val githubRepository = GithubRepository("juwit/terraform-docker-mongo", "https://github.com/juwit/terraform-docker-mongo")
        whenever(githubRegistryApi.getRepository(user, "juwit/terraform-docker-mongo")).thenReturn(githubRepository)

        val variablesFileContent = "mock file content"
        whenever(githubRegistryApi.getFileContent(user, "juwit/terraform-docker-mongo", "variables.tf")).thenReturn(variablesFileContent)
        whenever(hclParser.parseVariables(variablesFileContent)).thenReturn(listOf(Variable("dummy")))

        whenever(githubRegistryApi.getFileContent(user, "juwit/terraform-docker-mongo", "main.tf")).thenReturn(variablesFileContent)
        whenever(hclParser.parseProvider(variablesFileContent)).thenReturn("docker")

        val module = registryService.importRepository("juwit/terraform-docker-mongo", RegistryType.GITHUB, user)

        verify(githubRegistryApi).getRepository(user, "juwit/terraform-docker-mongo")
        verify(githubRegistryApi).getFileContent(user, "juwit/terraform-docker-mongo", "variables.tf")

        verifyNoMoreInteractions(githubRegistryApi)

        assertThat(module.id).isNotBlank()

        assertThat(module.name).isEqualTo("juwit/terraform-docker-mongo")
        assertThat(module.gitRepositoryUrl).isEqualTo("https://github.com/juwit/terraform-docker-mongo")
        assertThat(module.gitBranch).isEqualTo("master")

        assertThat(module.registryDetails.registryType).isEqualTo(RegistryType.GITHUB)
        assertThat(module.registryDetails.projectId).isEqualTo("juwit/terraform-docker-mongo")

        assertThat(module.terraformImage.tag).isEqualTo("latest")
        assertThat(module.moduleMetadata.createdBy).isEqualTo(user)

        assertThat(module.mainProvider).isEqualTo("docker")

        assertThat(module.variables).containsExactly(Variable("dummy"))
    }
}