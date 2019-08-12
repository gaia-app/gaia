package io.codeka.gaia.repository.strategy;

import org.apache.commons.lang.StringUtils;

import java.text.MessageFormat;
import java.util.regex.Pattern;

public abstract class GitPlatformStrategy {

    protected static final String DEFAULT_BRANCH = "master";

    /**
     * Returns the pattern to match the repository url.
     * The pattern should contains at least one group to extract the part of the repository to keep
     */
    protected abstract Pattern getPattern();

    /**
     * Returns the template corresponding to the raw url>
     * Can include parameters inside treated by {@link MessageFormat}.
     * {0} matches the part of the repository url extracted by the pattern
     * {1} matches the branch of the repository
     */
    protected abstract String getTemplateRawUrl();

    public boolean matches(String url) {
        return getPattern().matcher(url).matches();
    }

    public String getRawUrl(String url, String branch, String directory) {
        var gitBranch = branch == null ? DEFAULT_BRANCH : branch;
        var matcher = getPattern().matcher(url);
        if (matcher.matches()) {
            var result = MessageFormat.format(getTemplateRawUrl(), matcher.group(1), gitBranch);
            if (StringUtils.isNotBlank(directory)) {
                result += "/" + directory;
            }
            return result;
        }
        return StringUtils.EMPTY;
    }

}
