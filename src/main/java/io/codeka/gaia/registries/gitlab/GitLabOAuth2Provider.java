package io.codeka.gaia.registries.gitlab;

import io.codeka.gaia.registries.RegistryOAuth2Provider;

public class GitLabOAuth2Provider implements RegistryOAuth2Provider {
    @Override
    public String getProvider() {
        return "gitlab";
    }
}
