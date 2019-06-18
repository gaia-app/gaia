package io.codeka.gaia.controller;

import io.codeka.gaia.bo.Settings;
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

    @Autowired
    public SettingsController(Settings settings) {
        this.settings = settings;
    }

    @GetMapping("/settings")
    public String settings(Model model){
        model.addAttribute("settings", settings);

        return "settings";
    }

    @PutMapping("/settings")
    public void saveModule(@RequestBody Settings settings){
        // update global settings bean
        this.settings.setExternalUrl( settings.getExternalUrl() );
        this.settings.setEnvVars(settings.getEnvVars());
    }

}
