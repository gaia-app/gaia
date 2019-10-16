package io.codeka.gaia.config.security.oauth2;

import io.codeka.gaia.test.MongoContainer;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@DirtiesContext
@Testcontainers
class OAuth2ClientSecurityConfigIT {

    @Container
    private static MongoContainer mongoContainer = new MongoContainer();

    @Nested
    @SpringBootTest
    class OAuth2ClientSecurityConfigNotLoadedTest {
        @Test
        void oauth2ClientSecurityConfig_shouldNotBeInstantiated(
                @Autowired(required = false) OAuth2ClientSecurityConfig oauth2ClientSecurityConfig) {
            assertNull(oauth2ClientSecurityConfig);
        }

        @Test
        void oAuth2SuccessHandler_shouldNotBeInstantiated(
                @Autowired(required = false) OAuth2SuccessHandler oAuth2SuccessHandler) {
            assertNull(oAuth2SuccessHandler);
        }
    }

    @Nested
    @SpringBootTest
    @ActiveProfiles("oauth2")
    class OAuth2ClientSecurityConfigLoadedTest {
        @Test
        void oauth2ClientSecurityConfig_shouldBeInstantiated(
                @Autowired(required = false) OAuth2ClientSecurityConfig oauth2ClientSecurityConfig) {
            assertNotNull(oauth2ClientSecurityConfig);
        }

        @Test
        void oAuth2SuccessHandler_shouldBeInstantiated(
                @Autowired(required = false) OAuth2SuccessHandler oAuth2SuccessHandler) {
            assertNotNull(oAuth2SuccessHandler);
        }
    }
}