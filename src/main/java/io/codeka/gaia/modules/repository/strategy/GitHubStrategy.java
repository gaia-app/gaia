package io.codeka.gaia.modules.repository.strategy;

import java.util.regex.Pattern;

public class GitHubStrategy extends GitPlatformStrategy {

    @Override
    protected final Pattern getPattern() {
        return Pattern.compile("^http[s]?://[www.]?github.com(.*).git$");
    }

    @Override
    protected String getTemplateRawUrl() {
        return "https://raw.githubusercontent.com{0}/{1}";
    }

}
