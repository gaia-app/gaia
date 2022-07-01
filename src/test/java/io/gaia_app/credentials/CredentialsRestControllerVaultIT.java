package io.gaia_app.credentials;

import io.gaia_app.test.SharedMongoContainerTest;
import io.gaia_app.test.VaultContainerTestInitializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = {"gaia.vault.enabled=true", "gaia.vault.encryption.transit.path=/gaia-transit", "gaia.vault.encryption.transit.key=gaia-key"})
@Testcontainers
@ContextConfiguration(initializers = VaultContainerTestInitializer.class)
@AutoConfigureMockMvc
class CredentialsRestControllerVaultIT extends SharedMongoContainerTest {

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
    @WithMockUser("Luke Skywalker")
    void providerList_containsVaultProvider() throws Exception {
        mockMvc.perform(get("/api/credentials/providers"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", contains("aws", "azurerm", "google", "vault-aws")));
    }

}
