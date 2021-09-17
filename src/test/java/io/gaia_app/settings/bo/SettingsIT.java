package io.gaia_app.settings.bo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = Settings.class)
@EnableConfigurationProperties
@TestPropertySource(properties = {
        "gaia.externalUrl=http://172.17.0.1:8080",
        "gaia.envVars[0].name=test",
        "gaia.envVars[0].value=value"
})
class SettingsIT {

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

    @Test
    void envVars_shouldBeConfigurableViaProperty(){
        assertEquals("test", settings.getEnvVars().get(0).name);
        assertEquals("value", settings.getEnvVars().get(0).value);
    }

    @Test
    @DirtiesContext // this test changes the settings objet
    void settings_shouldBeMergedWithSavedSettings(){
        var savedSettings = new Settings();
        savedSettings.setExternalUrl("https://gaia.io");

        var testVar = new Settings.EnvVar();
        testVar.setName("name");
        testVar.setValue("value");

        var otherTestVar = new Settings.EnvVar();
        otherTestVar.setName("anotherName");
        otherTestVar.setValue("anotherValue");

        var existingEnvVar = this.settings.getEnvVars().get(0);

        savedSettings.setEnvVars(List.of(testVar, otherTestVar));

        settings.merge(savedSettings);

        assertEquals("https://gaia.io", settings.getExternalUrl());
        assertThat(settings.getEnvVars()).hasSize(3)
                .containsExactlyInAnyOrder(existingEnvVar, testVar, otherTestVar);
    }

}
