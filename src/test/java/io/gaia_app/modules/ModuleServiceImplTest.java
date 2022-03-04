package io.gaia_app.modules;

import io.gaia_app.modules.bo.TerraformModule;
import io.gaia_app.registries.RegistryType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ModuleServiceImplTest {

    public static Stream<String> updateRegistryDetails_forGithubModule() {
        return Stream.of(
            "https://github.com/juwit/terraform-docker-mongo",
            "https://github.com/juwit/terraform-docker-mongo.git",
            "git@github.com:juwit/terraform-docker-mongo",
            "git@github.com:juwit/terraform-docker-mongo.git"
        );
    }

    @ParameterizedTest
    @MethodSource
    void updateRegistryDetails_forGithubModule(String registryUrl) {
        var module = new TerraformModule();
        module.setGitRepositoryUrl(registryUrl);

        var moduleService = new ModuleServiceImpl();
        moduleService.updateRegistryDetails(module);

        assertThat(module.getRegistryDetails())
            .isNotNull()
            .satisfies(it -> {
                assertThat(it.getRegistryType()).isEqualTo(RegistryType.GITHUB);
                assertThat(it.getProjectId()).isEqualTo("juwit/terraform-docker-mongo");
            });
    }
}
