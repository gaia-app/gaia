package io.gaia_app.registries.controller

import io.gaia_app.registries.RegistryApi
import io.gaia_app.registries.RegistryType
import io.gaia_app.registries.github.GithubRepository
import io.gaia_app.registries.service.RegistryService
import io.gaia_app.organizations.User
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class GithubRegistryControllerTest {

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
