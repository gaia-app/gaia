package io.codeka.gaia.config.security.oauth2;

import io.codeka.gaia.test.MongoContainer;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
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
    @SpringBootTest(properties = {
            "spring.security.oauth2.client.registration.test_oauth2_client.client-id=ID",
            "spring.security.oauth2.client.registration.test_oauth2_client.client-secret=SECRET",
            "spring.security.oauth2.client.registration.test_oauth2_client.authorization-grant-type=authorization_code",
            "spring.security.oauth2.client.registration.test_oauth2_client.redirect-uri=REDIRECT_URI",
            "spring.security.oauth2.client.provider.test_oauth2_client.authorization-uri=AUTHORIZATION_URI",
            "spring.security.oauth2.client.provider.test_oauth2_client.token-uri=TOKEN_URI",
            "spring.security.oauth2.client.provider.test_oauth2_client.user-info-uri=USER_INFO_URI",
    })
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