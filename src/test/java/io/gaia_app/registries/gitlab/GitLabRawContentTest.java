package io.gaia_app.registries.gitlab;

import io.gaia_app.registries.RegistryDetails;
import io.gaia_app.registries.RegistryFile;
import io.gaia_app.registries.RegistryType;
import io.gaia_app.modules.bo.TerraformModule;
import io.gaia_app.organizations.OAuth2User;
import io.gaia_app.organizations.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GitLabRawContentTest {

    @InjectMocks
    private GitLabRawContent gitLabRawContent;

    @Mock
    private RestTemplate restTemplate;

    @Test
    void matches_shouldReturnTrueForGitlabModules() {
        TerraformModule gitlabModule = new TerraformModule();
        gitlabModule.setRegistryDetails(new RegistryDetails(RegistryType.GITLAB, "123456"));
        assertTrue(gitLabRawContent.matches(gitlabModule));
    }

    @Test
    void matches_shouldReturnFalseForGithubModules() {
        TerraformModule githubModule = new TerraformModule();
        githubModule.setRegistryDetails(new RegistryDetails(RegistryType.GITHUB, "some/project"));
        assertFalse(gitLabRawContent.matches(githubModule));
    }

    @Test
    void getReadmeContent_shouldCallTheApi_andServeDecodedContent(){
        // given
        var module = new TerraformModule();
        module.setRegistryDetails(new RegistryDetails(RegistryType.GITLAB, "123"));

        var jack = new User("Jack", null);
        jack.setOAuth2User(new OAuth2User("GITLAB","TOKENSTRING", null));
        module.getModuleMetadata().setCreatedBy(jack);

        var requestCaptor = ArgumentCaptor.forClass(HttpEntity.class);

        var gitlabFile = new RegistryFile(Base64.getEncoder().encodeToString("# Module Readme".getBytes()));
        var response = new ResponseEntity<>(gitlabFile, HttpStatus.OK);

        when(restTemplate.exchange(
                eq("https://gitlab.com/api/v4/projects/123/repository/files/README.md?ref=master"),
                eq(HttpMethod.GET),
                requestCaptor.capture(),
                eq(RegistryFile.class))).thenReturn(response);

        // when
        var result = gitLabRawContent.getReadme(module);

        // then
        // content is served decoded
        assertThat(result).isPresent().contains("# Module Readme");
        // request is authenticated
        assertThat(requestCaptor.getValue().getHeaders()).containsEntry("Authorization", List.of("Bearer TOKENSTRING"));
    }

    @Test
    void getReadmeContent_withNoRegistryDetails_returnsEmptyContent(){
        // given
        var module = new TerraformModule();

        // when
        var result = gitLabRawContent.getReadme(module);

        // then
        assertThat(result).isEmpty();
    }

    @Test
    void getReadmeContent_shouldReturnEmpty_whenReadmeDoesntExists(){
        // given
        var module = new TerraformModule();
        module.setRegistryDetails(new RegistryDetails(RegistryType.GITLAB, "123"));

        var jack = new User("Jack", null);
        jack.setOAuth2User(new OAuth2User("GITLAB","TOKENSTRING", null));
        module.getModuleMetadata().setCreatedBy(jack);

        var requestCaptor = ArgumentCaptor.forClass(HttpEntity.class);

        var response = new ResponseEntity<RegistryFile>(HttpStatus.NOT_FOUND);

        when(restTemplate.exchange(
                eq("https://gitlab.com/api/v4/projects/123/repository/files/README.md?ref=master"),
                eq(HttpMethod.GET),
                requestCaptor.capture(),
                eq(RegistryFile.class))).thenReturn(response);

        // when
        var result = gitLabRawContent.getReadme(module);

        // then
        // content is empty
        assertThat(result).isEmpty();
        // request is authenticated
        assertThat(requestCaptor.getValue().getHeaders()).containsEntry("Authorization", List.of("Bearer TOKENSTRING"));
    }

}
