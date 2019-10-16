package io.codeka.gaia.registries.gitlab;

import io.codeka.gaia.registries.RegistryRawContent;

import java.util.regex.Pattern;

public class GitLabRawContent extends RegistryRawContent {
    @Override
    protected final Pattern getPattern() {
        return Pattern.compile("^(http[s]?://[www.]?gitlab.*).git$");
    }

    @Override
    protected String getTemplateRawUrl() {
        return "{0}/raw/{1}";
    }
}
