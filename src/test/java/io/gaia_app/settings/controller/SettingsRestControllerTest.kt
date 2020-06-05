package io.gaia_app.settings.controller

import io.gaia_app.settings.bo.Settings
import io.gaia_app.settings.bo.Settings.EnvVar
import io.gaia_app.settings.repository.SettingsRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class SettingsRestControllerTest {

    @Mock
    lateinit var settings: Settings

    @Mock
    lateinit var settingsRepository: SettingsRepository

    @InjectMocks
    lateinit var controller: SettingsRestController

    @Test
    fun `getSettings() should return settings`() {
        // when
        val result = controller.getSettings()

        // then
        assertThat(result)
            .isNotNull
            .isEqualTo(settings)
    }

    @Test
    fun `saveSettings() should register the settings`() {
        // given
        val editedSettings = Settings()
        editedSettings.externalUrl = "edited"
        val envVar = EnvVar()
        envVar.name = "test"
        envVar.value = "value"
        editedSettings.envVars = listOf(envVar)

        // when
        controller.saveSettings(editedSettings)

        // then
        verify(settings).externalUrl = editedSettings.externalUrl
        verify(settings).envVars = editedSettings.envVars
        verify(settingsRepository, times(1)).save()
        verifyNoMoreInteractions(settingsRepository)
    }

}
