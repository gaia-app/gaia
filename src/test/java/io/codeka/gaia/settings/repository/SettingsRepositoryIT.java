package io.codeka.gaia.settings.repository;

import io.codeka.gaia.settings.bo.Settings;
import io.codeka.gaia.test.MongoContainer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Testcontainers
@TestPropertySource(properties = {"gaia.externalUrl=http://gaia.io", "gaia.dockerDaemonUrl=unix:///var/run/docker.sock"})
@DirtiesContext
class SettingsRepositoryIT {

    @Container
    private static MongoContainer mongoContainer = new MongoContainer();

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private SettingsRepository settingsRepository;

    @Autowired
    private Settings settings;

    @Test
    void itShouldSaveSettings(){
        // given

        // when
        settingsRepository.save();

        // then
        var saved = mongoTemplate.findAll(Settings.class).get(0);
        assertEquals("http://gaia.io", saved.getExternalUrl());
        assertEquals("unix:///var/run/docker.sock", saved.getDockerDaemonUrl());
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