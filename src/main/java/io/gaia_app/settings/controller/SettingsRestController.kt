package io.gaia_app.settings.controller

import io.gaia_app.settings.bo.Settings
import io.gaia_app.settings.repository.SettingsRepository
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.*

/**
 * The controller for the settings management
 */
@RestController
@RequestMapping("/api/settings")
@Secured("ROLE_ADMIN")
class SettingsRestController(
        private val settings: Settings,
        private val settingsRepository: SettingsRepository) {

    @GetMapping
    fun getSettings() = settings

    @PutMapping
    fun saveSettings(@RequestBody settings: Settings) {
        // update global settings bean
        this.settings.externalUrl = settings.externalUrl
        this.settings.envVars = settings.envVars
        // saving the data
        settingsRepository.save()
    }

}
