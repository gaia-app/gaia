package io.gaia_app.stacks.controller;

import io.gaia_app.test.SharedMongoContainerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Simple integration test that validates the security configuration of the OrganizationsRestController, and its http routes
 */
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(value = "admin", roles = "ADMIN")
class StackRestControllerIT extends SharedMongoContainerTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mongo.emptyDatabase();
        mongo.runScript("00_organization.js");
        mongo.runScript("10_user.js");
        mongo.runScript("20_module.js");
        mongo.runScript("30_stack.js");
    }

    @Test
    @WithMockUser("Mary J")
    void listStacks_shouldReturnLimitedStacks_forStandardUsers() throws Exception {
        mockMvc.perform(get("/api/stacks"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0]name", is("mongo-instance-limited")))
            .andExpect(jsonPath("$[0]ownerOrganization.id", is("Not Ze Organization")));
    }

    @Test
    void listStacks_shouldReturnAllStacks_forAdmin() throws Exception {
        mockMvc.perform(get("/api/stacks"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(5)))
            .andExpect(jsonPath("$.[*].name", contains("mongo-instance-1", "mongo-instance-2", "mongo-instance-limited", "local-mongo", "archived-stack")))
            .andExpect(jsonPath("$.[*].ownerOrganization.id", contains("Ze Organization", "Ze Organization", "Not Ze Organization")));
    }

    @Test
    @WithMockUser("Mary J")
    void getStacks_shouldReturnStack_forStandardUsers() throws Exception {
        mockMvc.perform(get("/api/stacks/845543d0-20a5-466c-8978-33c9a4661606"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name", is("mongo-instance-limited")))
            .andExpect(jsonPath("$.ownerOrganization.id", is("Not Ze Organization")))
            .andExpect(jsonPath("$.estimatedRunningCost", is(0)));
    }

    @Test
    @WithMockUser("Mary J")
    void getStacks_shouldNotReturnStackOfOtherOrganizations_forStandardUsers() throws Exception {
        mockMvc.perform(get("/api/stacks/5a215b6b-fe53-4afa-85f0-a10175a7f264"))
            .andExpect(status().isNotFound());
    }

    @Test
    void getStacks_shouldReturnStack_forAdmin() throws Exception {
        mockMvc.perform(get("/api/stacks/5a215b6b-fe53-4afa-85f0-a10175a7f264"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name", is("mongo-instance-1")))
            .andExpect(jsonPath("$.ownerOrganization.id", is("Ze Organization")))
            .andExpect(jsonPath("$.estimatedRunningCost", is(0)));
    }

    @Test
    @WithMockUser("Mary J")
    void saveStack_shouldBeAccessible_forStandardUser() throws Exception {
        mockMvc.perform(post("/api/stacks")
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\":\"stack-test\", \"module\": {\"id\":\"e01f9925-a559-45a2-8a55-f93dc434c676\"}}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", notNullValue()))
            .andExpect(jsonPath("$.ownerOrganization.id", is("Not Ze Organization")))
            .andExpect(jsonPath("$.createdBy.username", is("Mary J")))
            .andExpect(jsonPath("$.createdAt", notNullValue()));
    }

    @Test
    @WithMockUser("Mary J")
    void updateStack_shouldBeAccessible_forStandardUser() throws Exception {
        mockMvc.perform(put("/api/stacks/test")
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\":\"stack-test\", \"module\": {\"id\":\"e01f9925-a559-45a2-8a55-f93dc434c676\"}}"))
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
            .andExpect(jsonPath("$.message", containsString("module must not be null")));
    }

    @Test
    void saveStack_shouldValidateStackContent_forBlankFields() throws Exception {
        mockMvc.perform(post("/api/stacks")
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            // empty name and module id
            .content("{\"name\":\"      \"}"))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message", containsString("name must not be blank")))
            .andExpect(jsonPath("$.message", containsString("module must not be null")));
    }

    @Test
    void saveStack_shouldValidateStackVariables() throws Exception {
        mockMvc.perform(post("/api/stacks")
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            // null variable
            .content("{\"name\":\"stack-test\", \"module\": {\"id\":\"b39ccd07-80f5-455f-a6b3-b94f915738c4\"}, \"variableValues\":{}}"))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message", is("mandatory variables should not be blank")));
    }

    @Test
    void saveStack_shouldWork_stackIsValid() throws Exception {
        mockMvc.perform(post("/api/stacks")
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            // empty name
            .content("{\"name\":\"stack-test\", \"module\": {\"id\":\"b39ccd07-80f5-455f-a6b3-b94f915738c4\"}, \"variableValues\":{\"mongo_container_name\":\"someContainerName\"}}"))
            .andExpect(status().isOk());
    }

    @Test
    void saveStack_shouldValidateStackVariablesRegex() throws Exception {
        mockMvc.perform(post("/api/stacks")
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            // null variable
            .content("{\"name\":\"stack-test\", \"module\": {\"id\":\"b39ccd07-80f5-455f-a6b3-b94f915738c4\"}, \"variableValues\":{\"mongo_container_name\":\"someContainerName\",\"mongo_exposed_port\":\"toto\"}}"))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message", is("variables should match the regex")));
    }

    @Test
    void launchJob_shouldThrowABadRequest_forArchivedStacks() throws Exception {
        mockMvc.perform(post("/api/stacks/archived-stack/RUN")
            .with(csrf()))
            .andExpect(status().isForbidden());
    }

}
