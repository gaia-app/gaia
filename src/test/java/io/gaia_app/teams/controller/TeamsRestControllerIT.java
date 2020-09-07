package io.gaia_app.teams.controller;

import io.gaia_app.test.SharedMongoContainerTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mongo.emptyDatabase();
        mongo.runScript("00_team.js");
        mongo.runScript("10_user.js");
    }

    @Test
    @WithMockUser("Mary J")
    void teams_shouldBeAccessible_forStandardUsers() {
        Assertions.assertDoesNotThrow(() -> teamsRestController.teams());
    }

    @Test
    void teams_shouldBeAccessible_forAdminUser() {
        Assertions.assertDoesNotThrow(() -> teamsRestController.teams());
    }

    @Test
    void teams_shouldBeExposed_atSpecificUrl() throws Exception {
        mockMvc.perform(get("/api/teams"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(3)))
            .andExpect(jsonPath("$..id", contains("Ze Team", "Not Ze Team", "Sith")));
    }

}
