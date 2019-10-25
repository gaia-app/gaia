package io.codeka.gaia.registries.github;

import io.codeka.gaia.registries.RegistryDetails;
import io.codeka.gaia.registries.RegistryFile;
import io.codeka.gaia.registries.RegistryType;
import io.codeka.gaia.modules.bo.TerraformModule;
import io.codeka.gaia.teams.OAuth2User;
import io.codeka.gaia.teams.User;
import org.bson.internal.Base64;
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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GitHubRawContentTest {

    @InjectMocks
    private GitHubRawContent gitHubRawContent;

    @Mock
    private RestTemplate restTemplate;

    @Test
    void getPattern_shouldReturnPattern() {
        assertNotNull(gitHubRawContent.getPattern());
    }

    @Test
    void matches_shouldReturnTrueForValidUrl() {
        assertTrue(gitHubRawContent.matches("https://github.com/CodeKaio/gaia.git"));
    }

    @Test
    void matches_shouldReturnFalseForInvalidUrl() {
        assertFalse(gitHubRawContent.matches("https://gitlab.com/CodeKaio/gaia.git"));
    }

    @Test
    void getReadmeContent_shouldCallTheApi_andServeDecodedContent(){
        // given
        var module = new TerraformModule();
        module.setRegistryDetails(new RegistryDetails(RegistryType.GITLAB, "group/project"));

        var jack = new User("Jack", null);
        jack.setOAuth2User(new OAuth2User("GITHUB","TOKENSTRING", null));
        module.setCreatedBy(jack);

        var requestCaptor = ArgumentCaptor.forClass(HttpEntity.class);

        var githubFile = new RegistryFile(Base64.encode("# Module Readme".getBytes()));
        var response = new ResponseEntity<>(githubFile, HttpStatus.OK);

        when(restTemplate.exchange(
                eq("https://api.github.com/repos/{id}/contents/README.md?ref=master"),
                eq(HttpMethod.GET),
                requestCaptor.capture(),
                eq(RegistryFile.class),
                eq("group/project"))).thenReturn(response);

        // when
        var result = gitHubRawContent.getReadme(module);

        // then
        // content is served decoded
        assertThat(result).isPresent().contains("# Module Readme");
        // request is authenticated
        assertThat(requestCaptor.getValue().getHeaders()).containsEntry("Authorization", List.of("Bearer TOKENSTRING"));
    }

    @Test
    void getReadmeContent_shouldCallTheApiWithoutAuth_ifNoToken_andServeDecodedContent(){
        // given
        var module = new TerraformModule();
        module.setRegistryDetails(new RegistryDetails(RegistryType.GITLAB,  "group/project"));

        var jack = new User("Jack", null);
        module.setCreatedBy(jack);

        var requestCaptor = ArgumentCaptor.forClass(HttpEntity.class);

        var githubFile = new RegistryFile(Base64.encode("# Module Readme".getBytes()));
        var response = new ResponseEntity<>(githubFile, HttpStatus.OK);

        when(restTemplate.exchange(
                eq("https://api.github.com/repos/{id}/contents/README.md?ref=master"),
                eq(HttpMethod.GET),
                requestCaptor.capture(),
                eq(RegistryFile.class),
                eq("group/project"))).thenReturn(response);

        // when
        var result = gitHubRawContent.getReadme(module);

        // then
        // content is served decoded
        assertThat(result).isPresent().contains("# Module Readme");
        // request is authenticated
        assertThat(requestCaptor.getValue().getHeaders()).isEmpty();
    }

    @Test
    void getReadmeContent_shouldCallTheApiWithoutAuth_ifNoOwner_andServeDecodedContent(){
        // given
        var module = new TerraformModule();
        module.setRegistryDetails(new RegistryDetails(RegistryType.GITLAB,  "group/project"));

        var requestCaptor = ArgumentCaptor.forClass(HttpEntity.class);

        var githubFile = new RegistryFile(Base64.encode("# Module Readme".getBytes()));
        var response = new ResponseEntity<>(githubFile, HttpStatus.OK);

        when(restTemplate.exchange(
                eq(RegistryType.GITHUB.getReadmeUrl()),
                eq(HttpMethod.GET),
                requestCaptor.capture(),
                eq(RegistryFile.class),
                eq("group/project"))).thenReturn(response);

        // when
        var result = gitHubRawContent.getReadme(module);

        // then
        // content is served decoded
        assertThat(result).isPresent().contains("# Module Readme");
        // request is authenticated
        assertThat(requestCaptor.getValue().getHeaders()).isEmpty();
    }

    @Test
    void getReadmeContent_withNoRegistryDetails_returnsEmptyContent(){
        // given
        var module = new TerraformModule();

        // when
        var result = gitHubRawContent.getReadme(module);

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
        module.setCreatedBy(jack);

        var requestCaptor = ArgumentCaptor.forClass(HttpEntity.class);

        var response = new ResponseEntity<RegistryFile>(HttpStatus.NOT_FOUND);

        when(restTemplate.exchange(
                eq(RegistryType.GITHUB.getReadmeUrl()),
                eq(HttpMethod.GET),
                requestCaptor.capture(),
                eq(RegistryFile.class),
                eq("123"))).thenReturn(response);

        // when
        var result = gitHubRawContent.getReadme(module);

        // then
        // content is empty
        assertThat(result).isEmpty();
        // request is authenticated
        assertThat(requestCaptor.getValue().getHeaders()).containsEntry("Authorization", List.of("Bearer TOKENSTRING"));
    }

}