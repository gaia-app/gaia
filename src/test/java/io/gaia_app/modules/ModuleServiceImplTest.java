package io.gaia_app.modules;

import io.gaia_app.modules.bo.TerraformModule;
import io.gaia_app.registries.RegistryType;
import io.gaia_app.registries.github.GithubRegistryApi;
import io.gaia_app.registries.github.GithubRepository;
import io.gaia_app.registries.gitlab.GitlabRegistryApi;
import io.gaia_app.registries.gitlab.GitlabRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ModuleServiceImplTest {

    @Mock
    private GitlabRegistryApi gitlabRegistryApi;

    @Mock
    private GithubRegistryApi githubRegistryApi;

    @InjectMocks
    private ModuleServiceImpl moduleService;

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
        when(githubRegistryApi.getRepository("juwit/terraform-docker-mongo")).thenReturn(new GithubRepository("juwit/terraform-docker-mongo", ""));

        var module = new TerraformModule();
        module.setGitRepositoryUrl(registryUrl);

        moduleService.updateRegistryDetails(module);

        assertThat(module.getRegistryDetails())
            .isNotNull()
            .satisfies(it -> {
                assertThat(it.getRegistryType()).isEqualTo(RegistryType.GITHUB);
                assertThat(it.getProjectId()).isEqualTo("juwit/terraform-docker-mongo");
            });
    }

    public static Stream<String> updateRegistryDetails_forGitlabModule() {
        return Stream.of(
            "https://gitlab.com/juwit/terraform-docker-mongo",
            "https://gitlab.com/juwit/terraform-docker-mongo/",
            "https://gitlab.com/juwit/terraform-docker-mongo.git",
            "git@gitlab.com:juwit/terraform-docker-mongo",
            "git@gitlab.com:juwit/terraform-docker-mongo.git"
        );
    }

    @ParameterizedTest
    @MethodSource
    void updateRegistryDetails_forGitlabModule(String registryUrl) {
        when(gitlabRegistryApi.getRepository("juwit/terraform-docker-mongo")).thenReturn(new GitlabRepository("16181047", "", ""));

        var module = new TerraformModule();
        module.setGitRepositoryUrl(registryUrl);

        moduleService.updateRegistryDetails(module);

        assertThat(module.getRegistryDetails())
            .isNotNull()
            .satisfies(it -> {
                assertThat(it.getRegistryType()).isEqualTo(RegistryType.GITLAB);
                assertThat(it.getProjectId()).isEqualTo("16181047");
            });
    }
}
