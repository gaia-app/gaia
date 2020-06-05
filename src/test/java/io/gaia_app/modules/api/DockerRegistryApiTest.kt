package io.gaia_app.modules.api

import io.gaia_app.test.whenever
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.*
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate

@ExtendWith(MockitoExtension::class)
class DockerRegistryApiTest {

    lateinit var api: DockerRegistryApi

    @Mock
    lateinit var restTemplate: RestTemplate

    @BeforeEach
    fun setup() {
        api = DockerRegistryApi("test_url", restTemplate)
    }

    @Test
    fun `findRepositoriesByName() should return a list of repositories matching a name`() {
        // given
        val repositories = listOf(
                DockerRegistryRepository("solo/spices", "drugs"),
                DockerRegistryRepository("solo/a280cfe", "blaster rifles"))
        val response = ResponseEntity.ok(DockerRegistryResponse(repositories))

        // when
        whenever(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.GET),
                any<HttpEntity<Any>>(),
                eq(typeRef<DockerRegistryResponse<DockerRegistryRepository>>())))
                .thenReturn(response)
        val result = api.findRepositoriesByName("solo")

        // then
        assertThat(result).isNotNull.isNotEmpty.hasSize(2)
        verify(restTemplate, times(1)).exchange(
                eq("test_url/search/repositories?query=solo&page=1&page_size=10"),
                eq(HttpMethod.GET),
                any<HttpEntity<Any>>(),
                eq(typeRef<DockerRegistryResponse<DockerRegistryRepository>>()))
    }

    @Test
    fun `findRepositoriesByName() should return an empty list when no match`() {
        // given
        val response = ResponseEntity.ok<DockerRegistryResponse<DockerRegistryRepository>>(null)

        // when
        whenever(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.GET),
                any<HttpEntity<Any>>(),
                eq(typeRef<DockerRegistryResponse<DockerRegistryRepository>>())))
                .thenReturn(response)
        val result = api.findRepositoriesByName("solo")

        // then
        assertThat(result).isNotNull.isEmpty()
    }

    @Test
    fun `findRepositoriesByName() should return an empty list when response is not ok`() {
        // given
        val repositories = emptyList<DockerRegistryRepository>()
        val response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(DockerRegistryResponse(repositories))

        // when
        whenever(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.GET),
                any<HttpEntity<Any>>(),
                eq(typeRef<DockerRegistryResponse<DockerRegistryRepository>>())))
                .thenReturn(response)
        val result = api.findRepositoriesByName("solo")

        // then
        assertThat(result).isNotNull.isEmpty()
    }

    @Test
    fun `findTagsByName() should return a list of tags for a repository`() {
        // given
        val tags = listOf(
                DockerRegistryRepositoryTag("sw-4"),
                DockerRegistryRepositoryTag("sw-5"),
                DockerRegistryRepositoryTag("sw-6"))
        val response = ResponseEntity.ok(DockerRegistryResponse(tags))

        // when
        whenever(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.GET),
                any<HttpEntity<Any>>(),
                eq(typeRef<DockerRegistryResponse<DockerRegistryRepositoryTag>>())))
                .thenReturn(response)
        val result = api.findTagsByName("sw-5", "lucas/original-movies")

        // then
        assertThat(result).isNotNull.isNotEmpty.hasSize(3)
        verify(restTemplate, times(1)).exchange(
                eq("test_url/repositories/lucas/original-movies/tags?name=sw-5&page=1&page_size=10"),
                eq(HttpMethod.GET),
                any<HttpEntity<Any>>(),
                eq(typeRef<DockerRegistryResponse<DockerRegistryRepositoryTag>>()))
    }

    @Test
    fun `findTagsByName() should return an empty list when no match`() {
        // given
        val response = ResponseEntity.ok<DockerRegistryResponse<DockerRegistryRepositoryTag>>(null)

        // when
        whenever(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.GET),
                any<HttpEntity<Any>>(),
                eq(typeRef<DockerRegistryResponse<DockerRegistryRepositoryTag>>())))
                .thenReturn(response)
        val result = api.findTagsByName("sw-10", "lucas/original-movies")

        // then
        assertThat(result).isNotNull.isEmpty()
    }

    @Test
    fun `findTagsByName() should return an empty list when response is not ok`() {
        // given
        val tags = emptyList<DockerRegistryRepositoryTag>()
        val response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(DockerRegistryResponse(tags))

        // when
        whenever(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.GET),
                any<HttpEntity<Any>>(),
                eq(typeRef<DockerRegistryResponse<DockerRegistryRepositoryTag>>())))
                .thenReturn(response)
        val result = api.findTagsByName("sw-1", "lucas/original-movies")

        // then
        assertThat(result).isNotNull.isEmpty()
    }

}
