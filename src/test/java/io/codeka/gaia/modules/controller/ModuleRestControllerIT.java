package io.codeka.gaia.modules.controller;

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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
class ModuleRestControllerIT {

    @Container
    private static MongoContainer mongoContainer = new MongoContainer()
            .withScript("src/test/resources/db/00_team.js")
            .withScript("src/test/resources/db/10_user.js")
            .withScript("src/test/resources/db/20_module.js");

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mongoContainer.resetDatabase();
    }

    @Test
    @WithMockUser("Mary J")
    void findAllModules_shouldReturnLimitedModules_forStandardUsers() throws Exception {
        mockMvc.perform(get("/api/modules"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0]name", is("terraform-docker-mongo-limited")))
                .andExpect(jsonPath("$[0]authorizedTeams..id", contains("Not Ze Team")));
    }

    @Test
    void findAllModules_shouldReturnAllModules_forAdmin() throws Exception {
        mockMvc.perform(get("/api/modules"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[*]name", contains("terraform-docker-mongo", "terraform-docker-mongo-limited", "terraform-docker-mongo-with-validation")))
                .andExpect(jsonPath("$..authorizedTeams..id", contains("Ze Team", "Not Ze Team", "Ze Team")));
    }

    @Test
    @WithMockUser("Mary J")
    void findModule_shouldReturnModule_forStandardUsers() throws Exception {
        mockMvc.perform(get("/api/modules/845543d0-20a5-466c-8978-33c9a4661606"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("terraform-docker-mongo-limited")));
    }

    @Test
    @WithMockUser("Mary J")
    void findModule_shouldNotReturnModuleOfOtherTeams_forStandardUsers() throws Exception {
        mockMvc.perform(get("/api/modules/e01f9925-a559-45a2-8a55-f93dc434c676"))
                .andExpect(status().isForbidden());
    }

    @Test
    void findModule_shouldReturnModules_forAdmin() throws Exception {
        mockMvc.perform(get("/api/modules/e01f9925-a559-45a2-8a55-f93dc434c676"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("terraform-docker-mongo")));
    }

    @Test
    void findModule_shouldReturnModulesOfOtherTeams_forAdmin() throws Exception {
        mockMvc.perform(get("/api/modules/845543d0-20a5-466c-8978-33c9a4661606"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("terraform-docker-mongo-limited")));
    }

    @Test
    @WithMockUser("Mary J")
    void saveModule_shouldNotBeAccessible_forStandardUsers() throws Exception {
        mockMvc.perform(put("/api/modules/e01f9925-a559-45a2-8a55-f93dc434c676")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"module-test\"," +
                        "\"terraformImage\":{\"repository\":\"hashicorp/terraform\",\"tag\":\"latest\"}," +
                        "\"gitRepositoryUrl\":\"https://github.com/juwit/terraform-docker-mongo.git\"}"))
                .andExpect(status().isForbidden());
    }

    @Test
    void saveModule_shouldBeAccessible_forAdmin() throws Exception {
        mockMvc.perform(put("/api/modules/e01f9925-a559-45a2-8a55-f93dc434c676")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"module-test\"," +
                        "\"terraformImage\":{\"repository\":\"hashicorp/terraform\",\"tag\":\"latest\"}," +
                        "\"gitRepositoryUrl\":\"https://github.com/juwit/terraform-docker-mongo.git\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.name", is("module-test")));
    }

    @Test
    void saveModule_shouldValidateModuleContent_forBlankFields() throws Exception {
        mockMvc.perform(put("/api/modules/stacks")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                // empty module
                .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containsString("name must not be blank")))
                .andExpect(jsonPath("$.message", containsString("gitRepositoryUrl must not be blank")));
    }

    @Test
    void saveModule_shouldValidateModuleVariables_forBlankFields() throws Exception {
        mockMvc.perform(put("/api/modules/stacks")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                // empty variable name
                .content("{\"variables\":[{\"name\":\"  \"}]}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containsString("variables[0].name must not be blank")));
    }

    @Test
    void saveModule_shouldValidateTerraformImage_forBlankFields() throws Exception {
        mockMvc.perform(put("/api/modules/stacks")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                // empty terraform image
                .content("{\"terraformImage\":{\"repository\":\"  \",\"tag\":\"  \"}}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containsString("terraformImage.repository must not be blank")))
                .andExpect(jsonPath("$.message", containsString("terraformImage.tag must not be blank")));
    }

    @Test
    void saveModule_shouldValidateTerraformImage_forWrongRepositoryName() throws Exception {
        mockMvc.perform(put("/api/modules/stacks")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                // empty terraform image
                .content("{\"terraformImage\":{\"repository\":\"wrong+pattern+image\",\"tag\":\"shame\"}}"))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andExpect(jsonPath("$.message", containsString("terraformImage.repository must match \"^[\\w][\\w.\\-\\/]{0,127}$")));
    }

    @Test
    void createModule_shouldSaveAModule() throws Exception {
        mockMvc.perform(post("/api/modules")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                // empty variable name
                .content("{\"name\":\"new-module\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("new-module")));
    }

}
