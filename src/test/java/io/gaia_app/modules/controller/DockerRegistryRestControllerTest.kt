package io.gaia_app.modules.controller;

import io.gaia_app.modules.api.DockerRegistryApi
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class DockerRegistryRestControllerTest {

    @Mock
    lateinit var api: DockerRegistryApi

    lateinit var controller: DockerRegistryRestController

    @BeforeEach
    fun setup() {
        controller = DockerRegistryRestController(api)
    }

    @Test
    fun `listRepositoriesByName() should call the api`() {
        // when
        controller.listRepositoriesByName("han")

        // then
        verify(api, times(1)).findRepositoriesByName("han")
    }

    @Test
    fun `listTagsByName() should call the api with name and owner`() {
        // when
        controller.listTagsByName("han", "solo", "spices")

        // then
        verify(api).findTagsByName("spices", "solo/han")
    }

    @Test
    fun `listTagsByName() should call the api with name and default owner`() {
        // when
        controller.listTagsByName("chewie", null, "blasters")

        // then
        verify(api).findTagsByName("blasters", "library/chewie")
    }

}
