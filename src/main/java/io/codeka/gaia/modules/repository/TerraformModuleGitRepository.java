package io.codeka.gaia.modules.repository;

import io.codeka.gaia.modules.bo.TerraformModule;
import io.codeka.gaia.registries.RegistryRawContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TerraformModuleGitRepository {

    private List<RegistryRawContent> registryRawContents;

    @Autowired
    public TerraformModuleGitRepository(List<RegistryRawContent> registryRawContents) {
        this.registryRawContents = registryRawContents;
    }

    /**
     * Returns the url of the README file
     *
     * @param module module contains the README file
     * @return the url of the README file
     */
    public Optional<String> getReadme(TerraformModule module) {
        var strategy = registryRawContents.stream()
                .filter(s -> s.matches(module.getGitRepositoryUrl()))
                .findFirst();

        if (strategy.isEmpty()) {
            return Optional.empty();
        }
        var url = strategy.get().getRawUrl(module.getGitRepositoryUrl(), module.getGitBranch(), module.getDirectory());
        return Optional.of(url + "/README.md");
    }
}
