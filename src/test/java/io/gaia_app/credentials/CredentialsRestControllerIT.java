package io.gaia_app.credentials;
//src/test/java/io/gaia_app/credentials/CredentialsRestControllerIT.java

import io.gaia_app.test.SharedMongoContainerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class CredentialsRestControllerIT extends SharedMongoContainerTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mongo.emptyDatabase();
        mongo.runScript("00_organization.js");
        mongo.runScript("10_user.js");
        mongo.runScript("70_credentials.js");
    }

    @Test
    @WithMockUser("Darth Vader")
    void users_shouldBeAbleToViewTheirOwnCredentials_forListAccess() throws Exception {
        mockMvc.perform(get("/api/credentials"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].provider", is("aws")));
    }

    @Test
    @WithMockUser("Darth Vader")
    void credentialsDetailsShouldNotLeak_forListAccess() throws Exception {
        mockMvc.perform(get("/api/credentials"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].provider", is("aws")))
            .andExpect(jsonPath("$[0].name", is("Holocron")))
            .andExpect(jsonPath("$[0].accessKey").doesNotExist())
            .andExpect(jsonPath("$[0].secretKey").doesNotExist());
    }

    @Test
    @WithMockUser("Darth Vader")
    void users_shouldBeAbleToViewTheirOwnCredentials_forSingleAccess() throws Exception {
        mockMvc.perform(get("/api/credentials/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("provider", is("aws")))
            .andExpect(jsonPath("name", is("Holocron")))
            .andExpect(jsonPath("accessKey", is("DEATH_STAR_KEY")))
            .andExpect(jsonPath("secretKey", is("DEATH_STAR_SECRET")))
            .andExpect(jsonPath("createdBy.username", is("Darth Vader")));
    }

    @Test
    @WithMockUser("Luke Skywalker")
    void users_shouldNotBeAbleToView_othersCredentials_forListAccess() throws Exception {
        // Luke cannot see Vader's credentials
        mockMvc.perform(get("/api/credentials"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @WithMockUser("Luke Skywalker")
    void users_shouldNotBeAbleToView_othersCredentials_forSingleAccess() throws Exception {
        mockMvc.perform(get("/api/credentials/1"))
            .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser("Darth Vader")
    void users_shouldBeAbleToCreate_newAWSCredentials() throws Exception {
        mockMvc.perform(
            post("/api/credentials")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                    "    \"provider\": \"aws\",\n" +
                    "    \"name\": \"Holocron\",\n" +
                    "    \"accessKey\": \"DEATH_STAR_KEY\",\n" +
                    "    \"secretKey\": \"DEATH_STAR_SECRET\"\n" +
                    "  }"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("name", is("Holocron")))
            .andExpect(jsonPath("provider", is("aws")))
            .andExpect(jsonPath("accessKey", is("DEATH_STAR_KEY")))
            .andExpect(jsonPath("secretKey", is("DEATH_STAR_SECRET")))
            .andExpect(jsonPath("createdBy.username", is("Darth Vader")))
            .andExpect(jsonPath("id").exists());
    }

    @Test
    @WithMockUser("Darth Vader")
    void users_shouldBeAbleToCreate_newAWSCredentials_withError() throws Exception {
        // Luke cannot see Vader's credentials
        mockMvc.perform(
            post("/api/credentials")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                    "    \"provider\": \"aws\",\n" +
                    "    \"name\": \"Holocron\",\n" +
                    "    \"toto\": \"DEATH_STAR_KEY\",\n" +
                    "    \"secretKey\": \"DEATH_STAR_SECRET\"\n" +
                    "  }"))
            .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser("Darth Vader")
    void users_shouldBeAbleToCreate_newGoogleCredentials() throws Exception {
        mockMvc.perform(
            post("/api/credentials")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                    "    \"provider\": \"google\",\n" +
                    "    \"name\": \"Holocron\",\n" +
                    "    \"serviceAccountJSONContents\": \"DEATH_STAR_KEY\"\n" +
                    "  }"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("name", is("Holocron")))
            .andExpect(jsonPath("provider", is("google")))
            .andExpect(jsonPath("serviceAccountJSONContents", is("DEATH_STAR_KEY")))
            .andExpect(jsonPath("createdBy.username", is("Darth Vader")))
            .andExpect(jsonPath("id").exists());
    }

    @Test
    @WithMockUser("Darth Vader")
    void users_shouldBeAbleToCreate_newAzurermCredentials() throws Exception {
        mockMvc.perform(
            post("/api/credentials")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                    "    \"provider\": \"azurerm\",\n" +
                    "    \"name\": \"Holocron\",\n" +
                    "    \"clientId\": \"DEATH_STAR_KEY\",\n" +
                    "    \"subscriptionId\": \"DEATH_STAR_SUBSCRIPTION\",\n" +
                    "    \"tenantId\": \"DEATH_STAR_TENANT\",\n" +
                    "    \"environment\": \"DEATH_STAR_ENVIRONMENT\",\n" +
                    "    \"backendAccessKey\": \"DEATH_STAR_BACKEND\",\n" +
                    "    \"clientSecret\": \"DEATH_STAR_SECRET\"\n" +
                    "  }"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("name", is("Holocron")))
            .andExpect(jsonPath("provider", is("azurerm")))
            .andExpect(jsonPath("clientId", is("DEATH_STAR_KEY")))
            .andExpect(jsonPath("clientSecret", is("DEATH_STAR_SECRET")))
            .andExpect(jsonPath("subscriptionId", is("DEATH_STAR_SUBSCRIPTION")))
            .andExpect(jsonPath("tenantId", is("DEATH_STAR_TENANT")))
            .andExpect(jsonPath("environment", is("DEATH_STAR_ENVIRONMENT")))
            .andExpect(jsonPath("backendAccessKey", is("DEATH_STAR_BACKEND")))
            .andExpect(jsonPath("createdBy.username", is("Darth Vader")))
            .andExpect(jsonPath("id").exists());
    }

    @Test
    @WithMockUser("Darth Vader")
    void users_shouldBeAbleToUpdate_theirCredentials() throws Exception {
        // Luke cannot see Vader's credentials
        mockMvc.perform(
            put("/api/credentials/1")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                    "    \"_id\": \"1\",\n" +
                    "    \"provider\": \"aws\",\n" +
                    "    \"name\": \"Holocron-Updated\",\n" +
                    "    \"accessKey\": \"DEATH_STAR_KEY\",\n" +
                    "    \"secretKey\": \"DEATH_STAR_SECRET\",\n" +
                    "    \"createdBy\": {\"username\": \"Darth Vader\"},\n" +
                    "    \"_class\": \"io.gaia_app.credentials.AWSCredentials\"\n" +
                    "  }"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("name", is("Holocron-Updated")));
    }

    @Test
    @WithMockUser("Luke Skywalker")
    void users_shouldNotBeAbleToUpdate_othersCredentials() throws Exception {
        mockMvc.perform(
            put("/api/credentials/1")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                    "    \"_id\": \"1\",\n" +
                    "    \"provider\": \"aws\",\n" +
                    "    \"name\": \"Holocron-Updated\",\n" +
                    "    \"accessKey\": \"DEATH_STAR_KEY\",\n" +
                    "    \"secretKey\": \"DEATH_STAR_SECRET\",\n" +
                    "    \"createdBy\": {\"username\": \"Darth Vader\"},\n" +
                    "    \"_class\": \"io.gaia_app.credentials.AWSCredentials\"\n" +
                    "  }"))
            .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser("Darth Vader")
    void users_shouldBeAbleToDelete_theirCredentials() throws Exception {
        // Luke cannot see Vader's credentials
        mockMvc.perform(delete("/api/credentials/1").with(csrf()))
            .andExpect(status().isOk());
    }

    @Test
    @WithMockUser("Luke Skywalker")
    void users_shouldNotBeAbleToDelete_othersCredentials() throws Exception {
        // Luke cannot see Vader's credentials
        mockMvc.perform(delete("/api/credentials/1").with(csrf()))
            .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser("Luke Skywalker")
    void providerListTest() throws Exception {
        mockMvc.perform(get("/api/credentials/providers"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", contains("aws","azurerm","google")));
    }

}
