package io.codeka.gaia.controller;

import io.codeka.gaia.bo.Settings;
import io.codeka.gaia.repository.SettingsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class SettingsControllerTest {

    @Mock
    private Settings settings;

    @Mock
    private SettingsRepository settingsRepository;

    @InjectMocks
    private SettingsController settingsController;

    @Test
    void put_shouldSaveUpdatedSettings(){
        // given
        var editedSettings = new Settings();
        editedSettings.setExternalUrl("edited");

        var envVar = new Settings.EnvVar();
        envVar.setName("test");
        envVar.setValue("value");
        editedSettings.setEnvVars(List.of(envVar));

        // when
        settingsController.saveSettings(editedSettings);

        // then
        verify(settings).setExternalUrl("edited");
        verify(settings).setEnvVars(anyList());

        verify(settingsRepository).save();
    }

}