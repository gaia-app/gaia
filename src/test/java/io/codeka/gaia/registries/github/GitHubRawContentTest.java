package io.codeka.gaia.registries.github;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class GitHubRawContentTest {

    private GitHubRawContent gitHubRawContent;

    @BeforeEach
    void setup() {
        gitHubRawContent = new GitHubRawContent();
    }

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
    void getRawUrl_shouldReturnRawUrl() {
        // given
        var url = "https://github.com/CodeKaio/gaia.git";

        // when
        var result = gitHubRawContent.getRawUrl(url, "branch", "directory");

        // then
        assertThat(result).isNotNull().isEqualTo("https://raw.githubusercontent.com/CodeKaio/gaia/branch/directory");
    }

    @Test
    void getRawUrl_shouldManageEmptyParameters() {
        // given
        var url = "https://github.com/CodeKaio/gaia.git";

        // when
        var result = gitHubRawContent.getRawUrl(url, null, null);

        // then
        assertThat(result).isNotNull().isEqualTo("https://raw.githubusercontent.com/CodeKaio/gaia/master");
    }

    @Test
    void getRawUrl_shouldReturnEmptyForUnmatchedUrl() {
        // given
        var url = "https://gitlab.com/CodeKaio/gaia.git";

        // when
        var result = gitHubRawContent.getRawUrl(url, null, null);

        // then
        assertThat(result).isEmpty();
    }

}