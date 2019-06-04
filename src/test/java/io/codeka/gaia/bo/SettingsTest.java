package io.codeka.gaia.bo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = Settings.class)
@EnableConfigurationProperties
@TestPropertySource(properties = "gaia.externalUrl=http://172.17.0.1:8080")
class SettingsTest {

    @Autowired
    private Settings settings;

    @Test
    void settingsShouldNotBeNull(){
        assertNotNull(settings);
    }

    @Test
    void externalUrl_shouldBeConfigurableViaProperty(){
        assertEquals("http://172.17.0.1:8080", settings.getExternalUrl());
    }

}