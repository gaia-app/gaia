package io.gaia_app.settings.repository;

import io.gaia_app.settings.bo.Settings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SettingsRepositoryTest {

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private MongoTemplate mongoTemplate;

    private Settings settings;

    private SettingsRepository settingsRepository;

    @BeforeEach
    void setUp() {
        settings = new Settings();
        settings.setExternalUrl("externalUrl");

        settingsRepository = new SettingsRepository(mongoTemplate, settings);
    }

    @Test
    void settings_shouldBeLoadedFromDatabase(){
        var savedSettings = new Settings();
        savedSettings.setExternalUrl("savedExternalUrl");

        when(mongoTemplate.findAll(Settings.class)).thenReturn(List.of(savedSettings));

        //when
        settingsRepository.postConstruct();

        //then
        assertEquals("savedExternalUrl", settings.getExternalUrl());

        verify(mongoTemplate).findAll(Settings.class);
        verifyNoMoreInteractions(mongoTemplate);
    }

    @Test
    void nothingShouldHappen_whenSettingsDoesntExistInDatabase(){
        //when
        settingsRepository.postConstruct();

        //then
        verify(mongoTemplate).findAll(Settings.class);
        verifyNoMoreInteractions(mongoTemplate);
    }

    @Test
    void save_shouldSaveSettingsInDatabase(){
        //when
        settingsRepository.save();

        //then
        verify(mongoTemplate).remove(Settings.class);
        verify(mongoTemplate).save(settings);
        verifyNoMoreInteractions(mongoTemplate);
    }

}
