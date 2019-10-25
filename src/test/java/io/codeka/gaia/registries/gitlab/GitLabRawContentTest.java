package io.codeka.gaia.registries.gitlab;

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
class GitLabRawContentTest {

    @InjectMocks
    private GitLabRawContent gitLabRawContent;

    @Mock
    private RestTemplate restTemplate;

    @Test
    void getPattern_shouldReturnPattern() {
        assertNotNull(gitLabRawContent.getPattern());
    }

    @Test
    void matches_shouldReturnTrueForValidUrl() {
        assertTrue(gitLabRawContent.matches("https://gitlab.com/CodeKaio/gaia.git"));
    }

    @Test
    void matches_shouldReturnFalseForInvalidUrl() {
        assertFalse(gitLabRawContent.matches("https://github.com/CodeKaio/gaia.git"));
    }

    @Test
    void getReadmeContent_shouldCallTheApi_andServeDecodedContent(){
        // given
        var module = new TerraformModule();
        module.setRegistryDetails(new RegistryDetails(RegistryType.GITLAB, "123"));

        var jack = new User("Jack", null);
        jack.setOAuth2User(new OAuth2User("GITLAB","TOKENSTRING", null));
        module.setCreatedBy(jack);

        var requestCaptor = ArgumentCaptor.forClass(HttpEntity.class);

        var gitlabFile = new RegistryFile(Base64.encode("# Module Readme".getBytes()));
        var response = new ResponseEntity<>(gitlabFile, HttpStatus.OK);

        when(restTemplate.exchange(
                eq(RegistryType.GITLAB.getReadmeUrl()),
                eq(HttpMethod.GET),
                requestCaptor.capture(),
                eq(RegistryFile.class),
                eq("123"))).thenReturn(response);

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
        module.setCreatedBy(jack);

        var requestCaptor = ArgumentCaptor.forClass(HttpEntity.class);

        var response = new ResponseEntity<RegistryFile>(HttpStatus.NOT_FOUND);

        when(restTemplate.exchange(
                eq(RegistryType.GITLAB.getReadmeUrl()),
                eq(HttpMethod.GET),
                requestCaptor.capture(),
                eq(RegistryFile.class),
                eq("123"))).thenReturn(response);

        // when
        var result = gitLabRawContent.getReadme(module);

        // then
        // content is empty
        assertThat(result).isEmpty();
        // request is authenticated
        assertThat(requestCaptor.getValue().getHeaders()).containsEntry("Authorization", List.of("Bearer TOKENSTRING"));
    }

}