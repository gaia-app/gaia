package io.codeka.gaia.registries.controller

import io.codeka.gaia.hcl.HclParser
import io.codeka.gaia.modules.bo.TerraformModule
import io.codeka.gaia.modules.bo.Variable
import io.codeka.gaia.modules.repository.TerraformCLIRepository
import io.codeka.gaia.modules.repository.TerraformModuleRepository
import io.codeka.gaia.registries.RegistryApi
import io.codeka.gaia.registries.RegistryType
import io.codeka.gaia.registries.github.GithubRepository
import io.codeka.gaia.registries.service.RegistryService
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
    lateinit var registryService: RegistryService

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
        // given
        val user = User("juwit", null)

        // when
        githubRegistryController.importRepository("juwit", "terraform-docker-mongo", user)

        // then
        verify(registryService).importRepository("juwit/terraform-docker-mongo", RegistryType.GITHUB, user)

        verifyNoMoreInteractions(registryService)
    }
}

inline fun <T> whenever(methodCall: T): OngoingStubbing<T> {
    return Mockito.`when`(methodCall)!!
}