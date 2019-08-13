package io.codeka.gaia.modules.repository;

import io.codeka.gaia.modules.bo.TerraformModule;
import io.codeka.gaia.modules.repository.strategy.GitPlatformStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TerraformModuleGitRepositoryTest {

    private TerraformModuleGitRepository repository;

    @Mock
    private GitPlatformStrategy strategy;

    @BeforeEach
    void setup() {
        repository = new TerraformModuleGitRepository();
        repository.gitPlatformStrategies = List.of(strategy);
    }

    @Test
    void getReadme_shouldReturnReadme() {
        // given
        var module = new TerraformModule();
        module.setGitRepositoryUrl("url");
        module.setGitBranch("branch");
        module.setDirectory("directory");

        // when
        when(strategy.matches(anyString())).thenReturn(true);
        when(strategy.getRawUrl(anyString(), anyString(), anyString())).thenReturn("raw_url");
        var result = repository.getReadme(module);

        // then
        assertThat(result).isPresent().get().isEqualTo("raw_url/README.md");
        verify(strategy).matches("url");
        verify(strategy).getRawUrl("url", "branch", "directory");
    }

    @Test
    void getReadme_shouldReturnNothingIfNoStrategyFound() {
        // given
        var module = new TerraformModule();

        // when
        when(strategy.matches(any())).thenReturn(false);
        var result = repository.getReadme(module);

        // then
        assertThat(result).isEmpty();
    }

}