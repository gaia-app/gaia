package io.gaia_app.config.security.oauth2

import io.gaia_app.registries.RegistryOAuth2Provider
import io.gaia_app.organizations.OAuth2User
import io.gaia_app.organizations.User
import io.gaia_app.organizations.repository.UserRepository
import io.gaia_app.test.whenever
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentCaptor.forClass
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.security.oauth2.client.registration.ClientRegistration
import org.springframework.security.oauth2.core.AuthorizationGrantType
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import java.util.*
import java.util.Optional.empty
import java.util.Optional.of
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@ExtendWith(MockitoExtension::class)
class OAuth2SuccessHandlerTest {

    @Mock
    lateinit var userRepository: UserRepository

    @Mock
    lateinit var oAuth2AuthorizedClientService: OAuth2AuthorizedClientService

    @Mock
    lateinit var request: HttpServletRequest

    @Mock
    lateinit var response: HttpServletResponse

    @Mock
    lateinit var authentication: OAuth2AuthenticationToken

    lateinit var oAuth2SuccessHandler: OAuth2SuccessHandler

    lateinit var registryOAuth2Providers: MutableList<RegistryOAuth2Provider>

    @BeforeEach
    fun setup() {
        registryOAuth2Providers = ArrayList()
        oAuth2SuccessHandler = OAuth2SuccessHandler(userRepository, registryOAuth2Providers, oAuth2AuthorizedClientService)
        whenever(authentication.name).thenReturn("django")
    }

    @Test
    fun `onAuthenticationSuccess() should create user when it does not exist`() {
        // when
        whenever(userRepository.findById(anyString())).thenReturn(empty())
        oAuth2SuccessHandler.onAuthenticationSuccess(request, response, authentication)

        // then
        val captor = forClass(User::class.java)
        verify(userRepository).save(captor.capture())
        assertThat(captor.value)
            .isNotNull
            .hasFieldOrPropertyWithValue("username", "django")
            .hasFieldOrPropertyWithValue("organization", null)
    }

    @Test
    fun `onAuthenticationSuccess() should update user when it exists`() {
        // given
        val user = User("calvin", null)

        // when
        whenever(userRepository.findById(anyString())).thenReturn(of(user))
        oAuth2SuccessHandler.onAuthenticationSuccess(request, response, authentication)

        // then
        verify(userRepository).save(user)
    }

    @Test
    fun `onAuthenticationSuccess() should fill oAuth2User`() {
        // given
        val user = User("frankie", null)
        val oauth2User = OAuth2User("tarantino", "unchained", null)
        val client = mock(OAuth2AuthorizedClient::class.java)
        val registration = ClientRegistration
            .withRegistrationId("test_registration_id")
            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
            .clientId("test_client_id")
            .redirectUriTemplate("test_uri_template")
            .authorizationUri("test_authorization_uri")
            .tokenUri("test_token_uri")
            .build()
        val principal = mock(DefaultOAuth2User::class.java)
        val oauth2Provider = mock(RegistryOAuth2Provider::class.java)
        registryOAuth2Providers.add(oauth2Provider)

        // when
        whenever(userRepository.findById(anyString())).thenReturn(of(user))
        whenever(oAuth2AuthorizedClientService.loadAuthorizedClient<OAuth2AuthorizedClient>(any(), anyString())).thenReturn(client)
        whenever(oauth2Provider.isAssignableFor(anyString())).thenReturn(true)
        whenever(oauth2Provider.getOAuth2User(principal, client)).thenReturn(oauth2User)
        whenever(client.clientRegistration).thenReturn(registration)
        whenever(authentication.principal).thenReturn(principal)
        oAuth2SuccessHandler.onAuthenticationSuccess(request, response, authentication)

        // then
        assertThat(user).hasFieldOrPropertyWithValue("oAuth2User", oauth2User)
    }

    @Test
    fun `onAuthenticationSuccess() should redirect to home page`() {
        // when
        whenever(userRepository.findById(anyString())).thenReturn(empty())
        oAuth2SuccessHandler.onAuthenticationSuccess(request, response, authentication)

        // then
        verify(response).sendRedirect("/")
    }

}
