package io.codeka.gaia.config.security.oauth2;

import io.codeka.gaia.registries.RegistryOAuth2Provider;
import io.codeka.gaia.teams.OAuth2User;
import io.codeka.gaia.teams.User;
import io.codeka.gaia.teams.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OAuth2SuccessHandlerTest {

    @Mock
    UserRepository userRepository;

    @Mock
    OAuth2AuthorizedClientService oAuth2AuthorizedClientService;

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    OAuth2AuthenticationToken authentication;

    @Mock
    HttpSession httpSession;

    private OAuth2SuccessHandler oAuth2SuccessHandler;
    private List<RegistryOAuth2Provider> registryOAuth2Providers;

    @BeforeEach
    void setup() {
        registryOAuth2Providers = new ArrayList<>();
        oAuth2SuccessHandler = new OAuth2SuccessHandler(userRepository, registryOAuth2Providers, oAuth2AuthorizedClientService);

        when(authentication.getName()).thenReturn("django");
        when(request.getSession()).thenReturn(httpSession);
    }

    @Test
    void onAuthenticationSuccess_shouldCreateUser_whenNotExists() throws IOException {
        // when
        when(userRepository.findById(anyString())).thenReturn(Optional.empty());
        oAuth2SuccessHandler.onAuthenticationSuccess(request, response, authentication);

        // then
        var captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(captor.capture());
        assertThat(captor.getValue()).isNotNull()
                .hasFieldOrPropertyWithValue("username", "django")
                .hasFieldOrPropertyWithValue("team", null);
    }

    @Test
    void onAuthenticationSuccess_shouldUpdateUser_whenExists() throws IOException {
        // given
        var user = new User("calvin", null);

        // when
        when(userRepository.findById(anyString())).thenReturn(Optional.of(user));
        oAuth2SuccessHandler.onAuthenticationSuccess(request, response, authentication);

        // then
        verify(userRepository).save(user);
    }

    @Test
    void onAuthenticationSuccess_shouldFillOAuth2User() throws IOException {
        // given
        var user = new User("frankie", null);
        var oauth2User = new OAuth2User("tarantino", "unchained", null);
        var client = mock(OAuth2AuthorizedClient.class);
        var registration = ClientRegistration
                .withRegistrationId("test_registration_id")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .clientId("test_client_id")
                .redirectUriTemplate("test_uri_template")
                .authorizationUri("test_authorization_uri")
                .tokenUri("test_token_uri")
                .build();
        var principal = mock(DefaultOAuth2User.class);
        var oauth2Provider = mock(RegistryOAuth2Provider.class);
        registryOAuth2Providers.add(oauth2Provider);

        // when
        when(userRepository.findById(anyString())).thenReturn(Optional.of(user));
        when(oAuth2AuthorizedClientService.loadAuthorizedClient(any(), anyString())).thenReturn(client);
        when(oauth2Provider.isAssignableFor(anyString())).thenReturn(true);
        when(oauth2Provider.getOAuth2User(principal, client)).thenReturn(oauth2User);
        when(client.getClientRegistration()).thenReturn(registration);
        when(authentication.getPrincipal()).thenReturn(principal);
        oAuth2SuccessHandler.onAuthenticationSuccess(request, response, authentication);

        // then
        assertThat(user).hasFieldOrPropertyWithValue("oAuth2User", oauth2User);
    }

    @Test
    void onAuthenticationSuccess_shouldRedirectToHomePage() throws IOException {
        // when
        when(userRepository.findById(anyString())).thenReturn(Optional.empty());
        oAuth2SuccessHandler.onAuthenticationSuccess(request, response, authentication);

        // then
        verify(response).sendRedirect("/");
    }

    @Test
    void onAuthenticationSuccess_shouldRedirectToAskedPage_whenSpecified() throws IOException {
        // given
        var savedRequest = mock(DefaultSavedRequest.class);

        // when
        when(userRepository.findById(anyString())).thenReturn(Optional.empty());
        when(httpSession.getAttribute("SPRING_SECURITY_SAVED_REQUEST")).thenReturn(savedRequest);
        when(savedRequest.getRequestURI()).thenReturn("/test_url");
        oAuth2SuccessHandler.onAuthenticationSuccess(request, response, authentication);

        // then
        verify(response).sendRedirect("/test_url");
    }
}