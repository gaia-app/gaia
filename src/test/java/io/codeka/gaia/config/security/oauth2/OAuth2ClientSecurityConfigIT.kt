package io.codeka.gaia.config.security.oauth2

import io.codeka.gaia.test.MongoContainer
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@DirtiesContext
@Testcontainers
class OAuth2ClientSecurityConfigIT {

    companion object {
        @Container
        val mongoContainer = MongoContainer()
    }

    @Nested
    @SpringBootTest
    inner class OAuth2ClientSecurityConfigNotLoadedTest {

        @Test
        fun `oauth2ClientSecurityConfig should not be instantiated`(
            @Autowired(required = false) oauth2ClientSecurityConfig: OAuth2ClientSecurityConfig?) {
            assertNull(oauth2ClientSecurityConfig)
        }

        @Test
        fun `oAuth2SuccessHandler should not be instantiated`(
            @Autowired(required = false) oAuth2SuccessHandler: OAuth2SuccessHandler?) {
            assertNull(oAuth2SuccessHandler)
        }

    }

    @Nested
    @SpringBootTest
    @ActiveProfiles("oauth2")
    inner class OAuth2ClientSecurityConfigLoadedTest {

        @Test
        fun `oauth2ClientSecurityConfig should be instantiated`(
            @Autowired(required = false) oauth2ClientSecurityConfig: OAuth2ClientSecurityConfig?) {
            assertNotNull(oauth2ClientSecurityConfig)
        }

        @Test
        fun `oAuth2SuccessHandler should be instantiated`(
            @Autowired(required = false) oAuth2SuccessHandler: OAuth2SuccessHandler?) {
            assertNotNull(oAuth2SuccessHandler)
        }

    }

    @Nested
    @SpringBootTest
    @AutoConfigureMockMvc
    @TestPropertySource(properties = [
        "spring.security.oauth2.client.registration.dummy.client-id=dummy-id",
        "spring.security.oauth2.client.registration.dummy.client-secret=dummy-secret",
        "spring.security.oauth2.client.registration.dummy.authorization-grant-type=authorization_code",
        "spring.security.oauth2.client.registration.dummy.redirect-uri={baseUrl}/auth/oauth2/{registrationId}/callback",
        "spring.security.oauth2.client.provider.dummy.authorization-uri=https://dummy.com/oauth/authorize",
        "spring.security.oauth2.client.provider.dummy.token-uri=https://dummy.com/oauth/token",
        "spring.security.oauth2.client.provider.dummy.user-info-uri=https://dummy.com/api/v4/user",
        "spring.security.oauth2.client.provider.dummy.user-name-attribute=username"])
    inner class OAuth2ClientSecurityTest {

        @Autowired
        lateinit var mockMvc: MockMvc

        @Test
        fun `security should allow oauth2 endpoint`() {
            mockMvc.perform(get("/auth/oauth2/dummy"))
                .andExpect(status().is3xxRedirection)
        }

        @Test
        fun `security should allow oauth2 callback`() {
            mockMvc.perform(get("/auth/oauth2/dummy/callback"))
                .andExpect(status().is3xxRedirection)
        }

    }

}
