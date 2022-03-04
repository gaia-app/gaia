package io.gaia_app.modules;

import io.gaia_app.modules.bo.TerraformModule;
import io.gaia_app.registries.RegistryDetails;
import io.gaia_app.registries.RegistryType;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class ModuleServiceImpl implements ModuleService {

    /**
     * matches https://github.com/something(.git), and captures 'something'.
     * The first group (.+?) expands as few times as possible.
     * The second non-capturing group (?:) matches the end of the line or .git
     */
    private static final String GITHUB_HTTPS_REPOSITORY_URL_REGEX = "https://github\\.com/(.+?)(?:$|\\.git$)";

    @Override
    public void updateRegistryDetails(TerraformModule module) {
        var pattern = Pattern.compile(GITHUB_HTTPS_REPOSITORY_URL_REGEX);
        var matcher = pattern.matcher(module.getGitRepositoryUrl());

        if (matcher.matches()) {
            var projectId = matcher.group(1);
            module.setRegistryDetails(new RegistryDetails(RegistryType.GITHUB, projectId));
        }
    }
}
