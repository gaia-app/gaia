package io.codeka.gaia.modules.controller;

import io.codeka.gaia.modules.repository.TerraformCLIRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TerraformCLIControllerTest {

    private TerraformCLIController controller;

    @Mock
    private TerraformCLIRepository repository;

    @BeforeEach
    void setup() {
        controller = new TerraformCLIController(repository);
    }

    @Test
    void listCLIVersions_shouldReturnVersions() {
        var versions = List.of("0.12.0", "0.11.0");
        // given
        when(repository.listCLIVersion()).thenReturn(versions);

        // when
        var result = controller.listCLIVersions();

        // then
        assertThat(result).isNotNull().isNotEmpty().isEqualTo(versions);
        verify(repository).listCLIVersion();
    }

}