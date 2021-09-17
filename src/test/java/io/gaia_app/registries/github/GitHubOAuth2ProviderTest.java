package io.gaia_app.registries.github;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GitHubOAuth2ProviderTest {

    private GitHubOAuth2Provider gitHubOAuth2Provider;

    @BeforeEach
    void setup() {
        gitHubOAuth2Provider = new GitHubOAuth2Provider();
    }

    @Test
    void getProvider_shouldReturnProvider() {
        assertNotNull(gitHubOAuth2Provider.getProvider());
    }

    @Test
    void isAssignableFor_shouldReturnTrue_forValidProvider() {
        assertTrue(gitHubOAuth2Provider.isAssignableFor("github"));
    }

    @Test
    void isAssignableFor_shouldReturnFalse_forInvalidProvider() {
        assertFalse(gitHubOAuth2Provider.isAssignableFor("gitlab"));
    }

    @Test
    void getOAuth2User_shouldReturnANewOAuthUser() {
        // given
        var attributes = new HashMap<String, Object>();
        var user = mock(DefaultOAuth2User.class);
        var client = mock(OAuth2AuthorizedClient.class);
        var registration = ClientRegistration
                .withRegistrationId("test_registration_id")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .clientId("test_client_id")
                .redirectUri("test_uri_template")
                .authorizationUri("test_authorization_uri")
                .tokenUri("test_token_uri")
                .build();
        var accessToken = mock(OAuth2AccessToken.class);

        // when
        when(user.getAttributes()).thenReturn(attributes);
        when(client.getClientRegistration()).thenReturn(registration);
        when(client.getAccessToken()).thenReturn(accessToken);
        when(accessToken.getTokenValue()).thenReturn("test_token");
        var result = gitHubOAuth2Provider.getOAuth2User(user, client);

        // then
        assertThat(result).isNotNull()
                .hasFieldOrPropertyWithValue("provider", "test_registration_id")
                .hasFieldOrPropertyWithValue("token", "test_token")
                .hasFieldOrPropertyWithValue("attributes", attributes);
    }

    @Test
    void getOAuth2Url_shouldReturnUrl_withToken() {
        // given
        var url = "https://github.com/CodeKaio/gaia.git";
        var token = "test_token";

        // when
        var result = gitHubOAuth2Provider.getOAuth2Url(url, token);

        // then
        assertThat(result).isNotNull().isEqualTo("https://oauth2:test_token@github.com/CodeKaio/gaia.git");
    }
}
