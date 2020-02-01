package io.codeka.gaia.teams.controller;

import io.codeka.gaia.test.MongoContainer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

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
@DirtiesContext
@Testcontainers
@AutoConfigureMockMvc
@WithMockUser(value = "admin", roles = "ADMIN")
class TeamsRestControllerIT {

    @Container
    private static MongoContainer mongoContainer = new MongoContainer()
            .withScript("src/test/resources/db/00_team.js")
            .withScript("src/test/resources/db/10_user.js");

    @Autowired
    private TeamsRestController teamsRestController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser("Mary J")
    void teams_shouldBeAccessible_forStandardUsers() {
        assertDoesNotThrow(() -> teamsRestController.teams());
    }

    @Test
    void teams_shouldBeAccessible_forAdminUser() {
        assertDoesNotThrow(() -> teamsRestController.teams());
    }

    @Test
    void teams_shouldBeExposed_atSpecificUrl() throws Exception {
        mockMvc.perform(get("/api/teams"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$..id", contains("Ze Team", "Not Ze Team", "Sith")));
    }

}