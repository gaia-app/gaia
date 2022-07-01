package io.gaia_app.registries;

import io.gaia_app.organizations.OAuth2User;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

public interface RegistryOAuth2Provider {
    /**
     * Return the name of the provider for the current strategy
     */
    String getProvider();

    /**
     * Determines if the strategy is matching the provider
     */
    default boolean isAssignableFor(String provider) {
        return getProvider().equals(provider);
    }

    /**
     * Returns the data from the authorized client
     */
    default OAuth2User getOAuth2User(DefaultOAuth2User user, OAuth2AuthorizedClient client) {
        return new OAuth2User(
                client.getClientRegistration().getRegistrationId(),
                client.getAccessToken().getTokenValue(),
                user.getAttributes());
    }

    /**
     * Returns the http(s) url filled with oauth2 token
     */
    default String getOAuth2Url(String url, String token) {
        return url.replaceAll("^(http[s]?://)(.*)$", "$1oauth2:" + token + "@$2");
    }
}
