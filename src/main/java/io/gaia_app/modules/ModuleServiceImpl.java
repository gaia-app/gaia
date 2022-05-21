package io.gaia_app.modules;

import io.gaia_app.modules.bo.TerraformModule;
import io.gaia_app.registries.RegistryApi;
import io.gaia_app.registries.RegistryDetails;
import io.gaia_app.registries.RegistryType;
import io.gaia_app.registries.SourceRepository;
import io.gaia_app.registries.github.GithubRegistryApi;
import io.gaia_app.registries.gitlab.GitlabRegistryApi;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

@Service
public class ModuleServiceImpl implements ModuleService {

    /**
     * matches https://github.com/something(.git), and captures 'something'.
     * The first group (.+?) expands as few times as possible.
     * The second non-capturing group (?:) matches the end of the line ($) or .git$
     */
    private static final Pattern GITHUB_HTTPS_REPOSITORY_URL_REGEX = Pattern.compile("https://github\\.com/(.+?)(?:(?:$)|(?:\\.git$))");

    /**
     * matches git@github.com:something(.git), and captures 'something'.
     * The first group (.+?) expands as few times as possible.
     * The second non-capturing group (?:) matches the end of the line or .git
     */
    private static final Pattern GITHUB_SSH_REPOSITORY_URL_REGEX = Pattern.compile("git@github\\.com:(.+?)(?:(?:$)|(?:\\.git$))");

    /**
     * matches https://gitlab.com/something(.git), and captures 'something'.
     * The first group (.+?) expands as few times as possible.
     * The second non-capturing group (?:) matches the end of the line or .git
     */
    private static final Pattern GITLAB_HTTPS_REPOSITORY_URL_REGEX = Pattern.compile("https://gitlab\\.com/(.+?)(?:(?:/?$)|(?:\\.git$))");

    /**
     * matches git@gitlab.com:something(.git), and captures 'something'.
     * The first group (.+?) expands as few times as possible.
     * The second non-capturing group (?:) matches the end of the line or .git
     */
    private static final Pattern GITLAB_SSH_REPOSITORY_URL_REGEX = Pattern.compile("git@gitlab\\.com:(.+?)(?:(?:$)|(?:\\.git$))");

    private Map<RegistryType, RegistryApi<? extends SourceRepository>> registryApis;

    public ModuleServiceImpl(GithubRegistryApi githubRegistryApi, GitlabRegistryApi gitlabRegistryApiMap) {
        this.registryApis = Map.of(RegistryType.GITHUB, githubRegistryApi, RegistryType.GITLAB, gitlabRegistryApiMap);
    }

    @Override
    public void updateRegistryDetails(TerraformModule module) {
        RegistryType registryType;
        if (module.getGitRepositoryUrl().contains("github")) {
            registryType = RegistryType.GITHUB;
        } else if (module.getGitRepositoryUrl().contains("gitlab")) {
            registryType = RegistryType.GITLAB;
        } else {
            // could not match any registry type from the URL
            return;
        }
        // find first pattern that matches, then computes the details
        Stream.of(
                GITHUB_HTTPS_REPOSITORY_URL_REGEX,
                GITHUB_SSH_REPOSITORY_URL_REGEX,
                GITLAB_HTTPS_REPOSITORY_URL_REGEX,
                GITLAB_SSH_REPOSITORY_URL_REGEX)
            .map(pattern -> pattern.matcher(module.getGitRepositoryUrl()))
            .filter(Matcher::matches)
            .findFirst()
            .ifPresent(it -> {
                var projectName = it.group(1);
                // get project id from project name
                var repository = registryApis.get(registryType).getRepository(projectName);
                module.setRegistryDetails(new RegistryDetails(registryType, repository.getId()));
            });
    }
}
