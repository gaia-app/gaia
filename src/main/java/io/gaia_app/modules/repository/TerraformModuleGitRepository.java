package io.gaia_app.modules.repository;

import io.gaia_app.modules.bo.TerraformModule;
import io.gaia_app.registries.RegistryRawContent;
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
     * Returns the content of the README file
     *
     * @param module module contains the README file
     * @return the content of the README file
     */
    public Optional<String> getReadme(TerraformModule module){
        var strategy = registryRawContents.stream()
                .filter(s -> s.matches(module))
                .findFirst();

        if(strategy.isEmpty()){
            return Optional.empty();
        }

        return strategy.get().getReadme(module);
    }

}
