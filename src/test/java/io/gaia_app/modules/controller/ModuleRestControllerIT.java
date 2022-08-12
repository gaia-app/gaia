package io.gaia_app.modules.controller;

import io.gaia_app.test.SharedMongoContainerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Simple integration test that validates the security configuration of the OrganizationsRestController, and its http routes
 */
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(value = "admin", roles = "ADMIN")
class ModuleRestControllerIT extends SharedMongoContainerTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mongo.emptyDatabase();
        mongo.runScript("00_organization.js");
        mongo.runScript("10_user.js");
        mongo.runScript("20_module.js");
        mongo.runScript("30_stack.js");
    }

    @Test
    @WithMockUser("Mary J")
    void findAllModules_shouldReturnLimitedModules_forStandardUsers() throws Exception {
        mockMvc.perform(get("/api/modules"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0]name", is("terraform-docker-mongo-limited")))
                .andExpect(jsonPath("$[0]authorizedOrganizations..id", contains("Not Ze Organization")));
    }

    @Test
    void findAllModules_shouldReturnAllModules_forAdmin() throws Exception {
        mockMvc.perform(get("/api/modules"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[*]name", contains("terraform-docker-mongo", "terraform-docker-mongo-limited", "terraform-docker-mongo-with-validation")))
                .andExpect(jsonPath("$..authorizedOrganizations..id", contains("Ze Organization", "Not Ze Organization", "Ze Organization")));
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
    void findModule_shouldNotReturnModuleOfOtherOrganizations_forStandardUsers() throws Exception {
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
    void findModule_shouldReturnModulesOfOtherOrganizations_forAdmin() throws Exception {
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

    @Nested
    class DeleteModule{

        @Test
        @WithMockUser("Mary J")
        void deleteModule_shouldNotBeAuthorized_forWrongUser() throws Exception {
            mockMvc.perform(delete("/api/modules/e01f9925-a559-45a2-8a55-f93dc434c676")
                    .with(csrf()))
                .andExpect(status().isForbidden());
        }

        @Test
        void deleteModule_shouldNotBeAuthorized_withExistingStacks() throws Exception {
            mockMvc.perform(delete("/api/modules/e01f9925-a559-45a2-8a55-f93dc434c676")
                    .with(csrf()))
                .andExpect(status().isBadRequest());
        }

        @Test
        void deleteModule_shouldWork_forModuleWithoutStacks() throws Exception {
            mockMvc.perform(delete("/api/modules/b39ccd07-80f5-455f-a6b3-b94f915738c4")
                    .with(csrf()))
                .andExpect(status().isNoContent());
        }


    }

}

