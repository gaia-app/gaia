package io.gaia_app.client.controller

import io.gaia_app.organizations.User
import io.gaia_app.test.whenever
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import java.util.*

@ExtendWith(MockitoExtension::class)
class AuthenticationRestControllerTest {

    @Mock
    lateinit var oAuth2ClientProperties: OAuth2ClientProperties

    @InjectMocks
    lateinit var controller: AuthenticationRestController

    @Test
    fun `user() should return user`() {
        val user = User("ghost", null)
        assertThat(controller.user(user))
            .isNotNull
            .isEqualTo(user)
    }

    @Test
    fun `authorities should return roles`() {
        // given
        val authorities = listOf(SimpleGrantedAuthority("role_1"), SimpleGrantedAuthority("role_2"))
        val authentication = mock(Authentication::class.java)

        // when
        whenever(authentication.authorities).thenReturn(authorities)
        val result = controller.authorities(authentication)

        assertThat(result)
            .isNotNull
            .isNotEmpty
            .containsExactly("role_1", "role_2")
    }

    @Test
    fun `providers() should return oauth2 providers when configured`() {
        // given
        val oAuth2clients = HashMap<String, OAuth2ClientProperties.Registration>()
        oAuth2clients["client_1"] = OAuth2ClientProperties.Registration()
        oAuth2clients["client_2"] = OAuth2ClientProperties.Registration()
        oAuth2clients["client_3"] = OAuth2ClientProperties.Registration()

        // when
        controller.oAuth2ClientProperties = oAuth2ClientProperties
        whenever(oAuth2ClientProperties.registration).thenReturn(oAuth2clients)
        val providers = controller.providers()

        // then
        assertThat(providers)
            .isNotNull
            .isNotEmpty
            .containsExactlyInAnyOrder("client_1", "client_2", "client_3")
    }

    @Test
    fun `providers() should return empty list when oauth2 is not configured`() {
        // when
        val providers = controller.providers()

        // then
        assertThat(providers)
            .isNotNull
            .isEmpty()
    }

}
