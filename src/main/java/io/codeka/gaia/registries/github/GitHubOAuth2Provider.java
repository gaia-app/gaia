package io.codeka.gaia.registries.github;

import io.codeka.gaia.registries.RegistryOAuth2Provider;

public class GitHubOAuth2Provider implements RegistryOAuth2Provider {
    @Override
    public String getProvider() {
        return "github";
    }
}
