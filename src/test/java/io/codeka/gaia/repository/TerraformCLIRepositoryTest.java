package io.codeka.gaia.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TerraformCLIRepositoryTest {

    private TerraformCLIRepository repository;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    void setup() {
        repository = new TerraformCLIRepository("test_url", "0.11.3", restTemplate);
        when(restTemplate.exchange(eq("test_url"), eq(HttpMethod.GET), any(HttpEntity.class), eq(String.class)))
                .thenReturn(ResponseEntity.ok(getVersions()));
    }

    @Test
    void listCLIVersion_shouldReturnVersions() {
        // when
        var result = repository.listCLIVersion();

        // then
        assertThat(result).isNotNull().isNotEmpty().hasSize(19);
    }

    @Test
    void listCLIVersion_shouldIgnoreNonFinalVersions() {
        // when
        var result = repository.listCLIVersion();

        // then
        assertThat(result).noneMatch(s -> s.contains("rc") || s.contains("beta") || s.contains("alpha"));
    }

    @Test
    void listCLIVersion_shouldIgnoreVersionsLowerThanRequired() {
        // when
        var result = repository.listCLIVersion();

        // then
        assertThat(result)
                .doesNotContain("0.11.0", "0.11.1", "0.11.2")
                .contains("0.11.3");
    }

    private String getVersions() {
        return "<a href=\"/terraform/1.0.0/\">terraform_1.0.0</a>" +
                "<a href=\"/terraform/0.12.5/\">terraform_0.12.5</a>" +
                "<a href=\"/terraform/0.12.4/\">terraform_0.12.4</a>" +
                "<a href=\"/terraform/0.12.3/\">terraform_0.12.3</a>" +
                "<a href=\"/terraform/0.12.2/\">terraform_0.12.2</a>" +
                "<a href=\"/terraform/0.12.1/\">terraform_0.12.1</a>" +
                "<a href=\"/terraform/0.12.0/\">terraform_0.12.0</a>" +
                "<a href=\"/terraform/0.12.0-rc1/\">terraform_0.12.0-rc1</a>" +
                "<a href=\"/terraform/0.12.0-beta2/\">terraform_0.12.0-beta2</a>" +
                "<a href=\"/terraform/0.12.0-beta1/\">terraform_0.12.0-beta1</a>" +
                "<a href=\"/terraform/0.12.0-alpha4/\">terraform_0.12.0-alpha4</a>" +
                "<a href=\"/terraform/0.12.0-alpha3/\">terraform_0.12.0-alpha3</a>" +
                "<a href=\"/terraform/0.12.0-alpha2/\">terraform_0.12.0-alpha2</a>" +
                "<a href=\"/terraform/0.12.0-alpha1/\">terraform_0.12.0-alpha1</a>" +
                "<a href=\"/terraform/0.11.14/\">terraform_0.11.14</a>" +
                "<a href=\"/terraform/0.11.13/\">terraform_0.11.13</a>" +
                "<a href=\"/terraform/0.11.12/\">terraform_0.11.12</a>" +
                "<a href=\"/terraform/0.11.12-beta1/\">terraform_0.11.12-beta1</a>" +
                "<a href=\"/terraform/0.11.11/\">terraform_0.11.11</a>" +
                "<a href=\"/terraform/0.11.10/\">terraform_0.11.10</a>" +
                "<a href=\"/terraform/0.11.9/\">terraform_0.11.9</a>" +
                "<a href=\"/terraform/0.11.9-beta1/\">terraform_0.11.9-beta1</a>" +
                "<a href=\"/terraform/0.11.8/\">terraform_0.11.8</a>" +
                "<a href=\"/terraform/0.11.7/\">terraform_0.11.7</a>" +
                "<a href=\"/terraform/0.11.6/\">terraform_0.11.6</a>" +
                "<a href=\"/terraform/0.11.5/\">terraform_0.11.5</a>" +
                "<a href=\"/terraform/0.11.4/\">terraform_0.11.4</a>" +
                "<a href=\"/terraform/0.11.3/\">terraform_0.11.3</a>" +
                "<a href=\"/terraform/0.11.2/\">terraform_0.11.2</a>" +
                "<a href=\"/terraform/0.11.1/\">terraform_0.11.1</a>" +
                "<a href=\"/terraform/0.11.0/\">terraform_0.11.0</a>" +
                "<a href=\"/terraform/0.11.0-rc1/\">terraform_0.11.0-rc1</a>" +
                "<a href=\"/terraform/0.11.0-beta1/\">terraform_0.11.0-beta1</a>" +
                "<a href=\"/terraform/../\">terraform_..</a>";
    }

}
