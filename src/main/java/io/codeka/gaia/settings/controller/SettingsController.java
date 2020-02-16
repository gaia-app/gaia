package io.codeka.gaia.settings.controller;

import io.codeka.gaia.settings.bo.Settings;
import io.codeka.gaia.settings.repository.SettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * The controller for the settings mgmt
 */
@Controller
@Secured("ROLE_ADMIN")
public class SettingsController {

    private Settings settings;

    private SettingsRepository settingsRepository;

    @Autowired
    public SettingsController(Settings settings, SettingsRepository settingsRepository) {
        this.settings = settings;
        this.settingsRepository = settingsRepository;
    }

//    @GetMapping("/settings")
    public String settings(Model model){
        model.addAttribute("settings", settings);

        return "settings";
    }

    @PutMapping("/settings")
    public void saveSettings(@RequestBody Settings settings){
        // update global settings bean
        this.settings.setExternalUrl( settings.getExternalUrl() );
        this.settings.setEnvVars(settings.getEnvVars());
        // saving the data
        this.settingsRepository.save();
    }

}
