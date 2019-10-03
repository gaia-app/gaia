package io.codeka.gaia.registries.github;

import io.codeka.gaia.registries.RegistryRawContent;

import java.util.regex.Pattern;

public class GitHubRawContent extends RegistryRawContent {
    @Override
    protected final Pattern getPattern() {
        return Pattern.compile("^http[s]?://[www.]?github.com(.*).git$");
    }

    @Override
    protected String getTemplateRawUrl() {
        return "https://raw.githubusercontent.com{0}/{1}";
    }
}
