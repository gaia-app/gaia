package io.gaia_app.modules.repository;

import io.gaia_app.modules.bo.TerraformModule;
import io.gaia_app.registries.RegistryRawContent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TerraformModuleGitRepositoryTest {

    @Mock
    RegistryRawContent registryRawContent;

    private TerraformModuleGitRepository repository;

    @BeforeEach
    void setup() {
        repository = new TerraformModuleGitRepository(List.of(registryRawContent));
    }

    @Test
    void getReadme_shouldReturnReadme() {
        // given
        var module = new TerraformModule();
        module.setGitRepositoryUrl("url");
        module.setGitBranch("branch");
        module.setDirectory("directory");

        // when
        when(registryRawContent.matches(module)).thenReturn(true);
        when(registryRawContent.getReadme(any())).thenReturn(Optional.of("README CONTENT"));
        var result = repository.getReadme(module);

        // then
        assertThat(result).isPresent().get().isEqualTo("README CONTENT");
        verify(registryRawContent).matches(module);
    }

    @Test
    void getReadme_shouldReturnNothingIfNoStrategyFound() {
        // given
        var module = new TerraformModule();
        when(registryRawContent.matches(module)).thenReturn(false);

        // when
        var result = repository.getReadme(module);

        // then
        assertThat(result).isEmpty();
    }
}
