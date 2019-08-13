package io.codeka.gaia.stacks.controller;

import io.codeka.gaia.test.MongoContainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
class StackRestControllerIT {

    @Container
    private static MongoContainer mongoContainer = new MongoContainer()
            .withScript("src/test/resources/db/00_team.js")
            .withScript("src/test/resources/db/10_user.js")
            .withScript("src/test/resources/db/20_module.js")
            .withScript("src/test/resources/db/30_stack.js");

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mongoContainer.resetDatabase();
    }

    @Test
    @WithMockUser("Mary J")
    void listStacks_shouldReturnLimitedStacks_forStandardUsers() throws Exception {
        mockMvc.perform(get("/api/stacks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0]name", is("mongo-instance-limited")))
                .andExpect(jsonPath("$[0]ownerTeam.id", is("Not Ze Team")));
    }

    @Test
    void listStacks_shouldReturnAllStacks_forAdmin() throws Exception {
        mockMvc.perform(get("/api/stacks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$..name", contains("mongo-instance-1", "mongo-instance-2", "mongo-instance-limited")))
                .andExpect(jsonPath("$..ownerTeam.id", contains("Ze Team", "Ze Team", "Not Ze Team")));
    }

    @Test
    @WithMockUser("Mary J")
    void getStacks_shouldReturnStack_forStandardUsers() throws Exception {
        mockMvc.perform(get("/api/stacks/845543d0-20a5-466c-8978-33c9a4661606"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("mongo-instance-limited")))
                .andExpect(jsonPath("$.ownerTeam.id", is("Not Ze Team")))
                .andExpect(jsonPath("$.estimatedRunningCost", is(0)));
    }

    @Test
    @WithMockUser("Mary J")
    void getStacks_shouldNotReturnStackOfOtherTeams_forStandardUsers() throws Exception {
        mockMvc.perform(get("/api/stacks/5a215b6b-fe53-4afa-85f0-a10175a7f264"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getStacks_shouldReturnStack_forAdmin() throws Exception {
        mockMvc.perform(get("/api/stacks/5a215b6b-fe53-4afa-85f0-a10175a7f264"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("mongo-instance-1")))
                .andExpect(jsonPath("$.ownerTeam.id", is("Ze Team")))
                .andExpect(jsonPath("$.estimatedRunningCost", is(0)));
    }

    @Test
    @WithMockUser("Mary J")
    void saveModule_shouldBeAccessible() throws Exception {
        mockMvc.perform(post("/api/stacks")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"stack-test\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.ownerTeam.id", is("Not Ze Team")))
                .andExpect(jsonPath("$.createdBy.username", is("Mary J")))
                .andExpect(jsonPath("$.createdAt", notNullValue()));
    }

    @Test
    @WithMockUser("Mary J")
    void updateModule_shouldBeAccessible() throws Exception {
        mockMvc.perform(put("/api/stacks/test")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"stack-test\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.updatedBy.username", is("Mary J")))
                .andExpect(jsonPath("$.updatedAt", notNullValue()));
    }

}