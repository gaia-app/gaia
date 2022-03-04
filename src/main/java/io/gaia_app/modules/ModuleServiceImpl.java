package io.gaia_app.modules;

import io.gaia_app.modules.bo.TerraformModule;
import io.gaia_app.registries.RegistryDetails;
import io.gaia_app.registries.RegistryType;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

@Service
public class ModuleServiceImpl implements ModuleService {

    /**
     * matches https://github.com/something(.git), and captures 'something'.
     * The first group (.+?) expands as few times as possible.
     * The second non-capturing group (?:) matches the end of the line or .git
     */
    private static final Pattern GITHUB_HTTPS_REPOSITORY_URL_REGEX = Pattern.compile("https://github\\.com/(.+?)(?:$|\\.git$)");

    /**
     * matches git@github.com:something(.git), and captures 'something'.
     * The first group (.+?) expands as few times as possible.
     * The second non-capturing group (?:) matches the end of the line or .git
     */
    private static final Pattern GITHUB_SSH_REPOSITORY_URL_REGEX = Pattern.compile("git@github\\.com:(.+?)(?:$|\\.git$)");

    @Override
    public void updateRegistryDetails(TerraformModule module) {
        // find first pattern that matches, then computes the details
        Stream.of(GITHUB_HTTPS_REPOSITORY_URL_REGEX, GITHUB_SSH_REPOSITORY_URL_REGEX)
            .map(pattern -> pattern.matcher(module.getGitRepositoryUrl()))
            .filter(Matcher::matches)
            .findFirst()
            .ifPresent(it -> {
                var projectId = it.group(1);
                module.setRegistryDetails(new RegistryDetails(RegistryType.GITHUB, projectId));
            });
    }
}
