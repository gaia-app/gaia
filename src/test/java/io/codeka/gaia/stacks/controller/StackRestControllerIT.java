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
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
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
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$..name", contains("mongo-instance-1", "mongo-instance-2", "mongo-instance-limited", "local-mongo")))
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
    void saveStack_shouldBeAccessible_forStandardUser() throws Exception {
        mockMvc.perform(post("/api/stacks")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"stack-test\", \"moduleId\": \"e01f9925-a559-45a2-8a55-f93dc434c676\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.ownerTeam.id", is("Not Ze Team")))
                .andExpect(jsonPath("$.createdBy.username", is("Mary J")))
                .andExpect(jsonPath("$.createdAt", notNullValue()));
    }

    @Test
    @WithMockUser("Mary J")
    void updateStack_shouldBeAccessible_forStandardUser() throws Exception {
        mockMvc.perform(put("/api/stacks/test")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"stack-test\", \"moduleId\": \"e01f9925-a559-45a2-8a55-f93dc434c676\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.updatedBy.username", is("Mary J")))
                .andExpect(jsonPath("$.updatedAt", notNullValue()));
    }

    @Test
    void saveStack_shouldValidateStackContent() throws Exception {
        mockMvc.perform(post("/api/stacks")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                // empty name and module id
                .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containsString("name must not be blank")))
                .andExpect(jsonPath("$.message", containsString("moduleId must not be blank")));
    }

    @Test
    void saveStack_shouldValidateStackContent_forBlankFields() throws Exception {
        mockMvc.perform(post("/api/stacks")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                // empty name and module id
                .content("{\"name\":\"      \",\"moduleId\":\"   \"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containsString("name must not be blank")))
                .andExpect(jsonPath("$.message", containsString("moduleId must not be blank")));
    }

    @Test
    void updateStack_shouldValidateStackContent() throws Exception {
        mockMvc.perform(put("/api/stacks/test")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                // empty name and module id
                .content("{\"name\":\"\", \"moduleId\": \"\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containsString("name must not be blank")))
                .andExpect(jsonPath("$.message", containsString("moduleId must not be blank")));
    }

    @Test
    void saveStack_shouldValidateStackVariables() throws Exception {
        mockMvc.perform(post("/api/stacks")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                // null variable
                .content("{\"name\":\"stack-test\", \"moduleId\": \"b39ccd07-80f5-455f-a6b3-b94f915738c4\", \"variableValues\":{}}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("mandatory variables should not be blank")));
    }

    @Test
    void saveStack_shouldWork_stackIsValid() throws Exception {
        mockMvc.perform(post("/api/stacks")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                // empty name
                .content("{\"name\":\"stack-test\", \"moduleId\": \"b39ccd07-80f5-455f-a6b3-b94f915738c4\", \"variableValues\":{\"mongo_container_name\":\"someContainerName\"}}"))
                .andExpect(status().isOk());
    }

    @Test
    void saveStack_shouldValidateStackVariablesRegex() throws Exception {
        mockMvc.perform(post("/api/stacks")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                // null variable
                .content("{\"name\":\"stack-test\", \"moduleId\": \"b39ccd07-80f5-455f-a6b3-b94f915738c4\", \"variableValues\":{\"mongo_container_name\":\"someContainerName\",\"mongo_exposed_port\":\"toto\"}}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("variables should match the regex")));
    }

}
