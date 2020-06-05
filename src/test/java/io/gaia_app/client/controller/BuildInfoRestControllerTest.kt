package io.gaia_app.client.controller

import io.gaia_app.test.whenever
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.boot.info.BuildProperties
import org.springframework.boot.info.GitProperties

@ExtendWith(MockitoExtension::class)
class BuildInfoRestControllerTest {

    @Mock
    lateinit var buildProperties: BuildProperties

    @Mock
    lateinit var gitProperties: GitProperties

    @InjectMocks
    lateinit var controller: BuildInfoRestController

    @Test
    fun `infos() should return version`() {
        // when
        whenever(buildProperties.version).thenReturn("x.y.z")
        val result = controller.infos();

        // then
        assertThat(result)
            .isNotNull
            .containsEntry("version", "x.y.z")
    }

    @Test
    fun `infos() should return null version if not available`() {
        // when
        controller.buildProperties = null
        val result = controller.infos();

        // then
        assertThat(result)
            .isNotNull
            .containsEntry("version", null)
    }

    @Test
    fun `infos() should return commitId`() {
        // when
        whenever(gitProperties.shortCommitId).thenReturn("7d1d8a")
        val result = controller.infos();

        // then
        assertThat(result)
            .isNotNull
            .containsEntry("commitId", "7d1d8a")
    }

    @Test
    fun `infos() should return null commitId if not available`() {
        // when
        controller.gitProperties = null
        val result = controller.infos();

        // then
        assertThat(result)
            .isNotNull
            .containsEntry("commitId", null)
    }

    @Test
    fun `infos() should return nothing if no data`() {
        // given
        controller = BuildInfoRestController(null, null)

        // when
        val result = controller.infos();

        // then
        assertThat(result).isNull()
    }

}
