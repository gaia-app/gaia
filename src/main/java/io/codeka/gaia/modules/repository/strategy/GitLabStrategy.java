package io.codeka.gaia.modules.repository.strategy;

import java.util.regex.Pattern;

public class GitLabStrategy extends GitPlatformStrategy {

    @Override
    protected final Pattern getPattern() {
        return Pattern.compile("^(http[s]?://[www.]?gitlab.*).git$");
    }

    @Override
    protected String getTemplateRawUrl() {
        return "{0}/raw/{1}";
    }

}
