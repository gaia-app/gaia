package io.codeka.gaia.repository;

import io.codeka.gaia.bo.TerraformModule;
import io.codeka.gaia.repository.strategy.GitHubStrategy;
import io.codeka.gaia.repository.strategy.GitLabStrategy;
import io.codeka.gaia.repository.strategy.GitPlatformStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TerraformModuleGitRepository {

    List<GitPlatformStrategy> gitPlatformStrategies;

    @Autowired
    public TerraformModuleGitRepository() {
        gitPlatformStrategies = List.of(new GitHubStrategy(), new GitLabStrategy());
    }

    /**
     * Returns the url of the README file
     *
     * @param module module contains the README file
     * @return the url of the README file
     */
    public Optional<String> getReadme(TerraformModule module) {
        Optional<GitPlatformStrategy> strategy = gitPlatformStrategies.stream()
                .filter(s -> s.matches(module.getGitRepositoryUrl()))
                .findFirst();

        if (strategy.isEmpty()) {
            return Optional.empty();
        }
        var url = strategy.get().getRawUrl(module.getGitRepositoryUrl(), module.getGitBranch(), module.getDirectory());
        return Optional.of(url + "/README.md");
    }

}
