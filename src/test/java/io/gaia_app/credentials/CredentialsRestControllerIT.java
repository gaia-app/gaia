package io.gaia_app.credentials;

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

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
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
        mongo.runScript("src/test/resources/db/00_team.js");
        mongo.runScript("src/test/resources/db/10_user.js");
        mongo.runScript("src/test/resources/db/70_credentials.js");
    }

    @Test
    @WithMockUser("Darth Vader")
    void users_shouldBeAbleToViewTheirOwnCredentials_forListAccess() throws Exception {
        mockMvc.perform(get("/api/credentials"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].provider", is("aws")))
            .andExpect(jsonPath("$[0].accessKey", is("DEATH_STAR_KEY")))
            .andExpect(jsonPath("$[0].secretKey", is("DEATH_STAR_SECRET")))
            .andExpect(jsonPath("$[0].createdBy.username", is("Darth Vader")));
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

}
