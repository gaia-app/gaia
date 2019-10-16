package io.codeka.gaia.registries.gitlab;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class GitLabRawContentTest {

    private GitLabRawContent gitLabRawContent;

    @BeforeEach
    void setup() {
        gitLabRawContent = new GitLabRawContent();
    }

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
    void getRawUrl_shouldReturnRawUrl() {
        // given
        var url = "https://gitlab.com/CodeKaio/gaia.git";

        // when
        var result = gitLabRawContent.getRawUrl(url, "branch", "directory");

        // then
        assertThat(result).isNotNull().isEqualTo("https://gitlab.com/CodeKaio/gaia/raw/branch/directory");
    }

    @Test
    void getRawUrl_shouldManageEmptyParameters() {
        // given
        var url = "https://gitlab.com/CodeKaio/gaia.git";

        // when
        var result = gitLabRawContent.getRawUrl(url, null, null);

        // then
        assertThat(result).isNotNull().isEqualTo("https://gitlab.com/CodeKaio/gaia/raw/master");
    }

    @Test
    void getRawUrl_shouldReturnEmptyForUnmatchedUrl() {
        // given
        var url = "https://github.com/CodeKaio/gaia.git";

        // when
        var result = gitLabRawContent.getRawUrl(url, null, null);

        // then
        assertThat(result).isEmpty();
    }

}