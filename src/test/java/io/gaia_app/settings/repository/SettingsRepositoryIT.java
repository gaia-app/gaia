package io.gaia_app.settings.repository;

import io.gaia_app.settings.bo.Settings;
import io.gaia_app.test.SharedMongoContainerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestPropertySource(properties = {"gaia.externalUrl=http://gaia.io"})
class SettingsRepositoryIT extends SharedMongoContainerTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private SettingsRepository settingsRepository;

    @Test
    void itShouldSaveSettings(){
        // given

        // when
        settingsRepository.save();

        // then
        var saved = mongoTemplate.findAll(Settings.class).get(0);
        assertEquals("http://gaia.io", saved.getExternalUrl());
    }

    @Test
    void settings_shouldOnlyExistOnceInDatabase(){
        // given

        // when
        settingsRepository.save();
        settingsRepository.save();

        // then
        assertThat( mongoTemplate.findAll(Settings.class) ).hasSize(1);
    }

}
