package io.gaia_app.teams.controller;

import io.gaia_app.teams.Team;
import io.gaia_app.teams.repository.TeamRepository;
import io.gaia_app.test.SharedMongoContainerTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Simple integration test that validates the security configuration of the TeamsRestController, and its http routes
 */
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(value = "admin", roles = "ADMIN")
class TeamsRestControllerIT extends SharedMongoContainerTest {

    @Autowired
    private TeamsRestController teamsRestController;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mongo.emptyDatabase();
        mongo.runScript("00_team.js");
        mongo.runScript("10_user.js");
    }

    @Test
    void teams_shouldBeExposed_atSpecificUrl() throws Exception {
        mockMvc.perform(get("/api/teams"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(3)))
            .andExpect(jsonPath("$..id", contains("Ze Team", "Not Ze Team", "Sith")));
    }

    @Test
    void createOrganization_shouldCreateOrganization() throws Exception {
        mockMvc.perform(post("/api/teams")
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"id\":\"Gungans\"}"))
            .andExpect(status().isOk());

        assertTrue(teamRepository.existsById("Gungans"));
    }

    @Test
    void deleteOrganization_shouldDeleteOrganization() throws Exception {
        teamRepository.save(new Team("First Order"));

        mockMvc.perform(delete("/api/teams/First Order")
            .with(csrf()))
            .andExpect(status().isOk());

        assertFalse(teamRepository.existsById("First Order"));
    }

    @Nested
    class AccessControl {

        @Test
        @WithMockUser("Jar Jar Binks")
        void teams_shouldBeAccessible_forStandardUsers() {
            Assertions.assertDoesNotThrow(() -> teamsRestController.teams());
        }

        @Test
        @WithMockUser("Jar Jar Binks")
        void createOrganization_shouldBeForbidden_forStandardUsers() {
            assertThrows(AccessDeniedException.class, () -> teamsRestController.createOrganization(new Team("Gungans")));
        }

        @Test
        @WithMockUser("Jar Jar Binks")
        void deleteOrganization_shouldBeForbidden_forStandardUsers() {
            assertThrows(AccessDeniedException.class, () -> teamsRestController.deleteOrganization("Gungans"));
        }

        @Test
        @WithMockUser(value = "admin", roles = "ADMIN")
        void teams_shouldBeAccessible_forAdminUser() {
            Assertions.assertDoesNotThrow(() -> teamsRestController.teams());
        }

    }

}
