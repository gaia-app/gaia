package io.gaia_app.registries.github;

import io.gaia_app.registries.RegistryOAuth2Provider;

public class GitHubOAuth2Provider implements RegistryOAuth2Provider {
    @Override
    public String getProvider() {
        return "github";
    }
}
