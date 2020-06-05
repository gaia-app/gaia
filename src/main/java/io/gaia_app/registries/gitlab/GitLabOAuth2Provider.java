package io.gaia_app.registries.gitlab;

import io.gaia_app.registries.RegistryOAuth2Provider;

public class GitLabOAuth2Provider implements RegistryOAuth2Provider {
    @Override
    public String getProvider() {
        return "gitlab";
    }
}
