package io.gaia_app.organizations.controller;

import io.gaia_app.organizations.Organization;
import io.gaia_app.organizations.repository.OrganizationsRepository;
import io.gaia_app.test.SharedMongoContainerTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
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
class OrganizationsRestControllerIT extends SharedMongoContainerTest {

    @Autowired
    private OrganizationsRestController organizationsRestController;

    @Autowired
    private OrganizationsRepository organizationsRepository;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mongo.emptyDatabase();
        mongo.runScript("00_organization.js");
        mongo.runScript("10_user.js");
    }

    @Test
    void organizations_shouldBeExposed_atSpecificUrl() throws Exception {
        mockMvc.perform(get("/api/organizations"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(3)))
            .andExpect(jsonPath("$..id", contains("Ze Organization", "Not Ze Organization", "Sith")));
    }

    @Test
    void createOrganization_shouldCreateOrganization() throws Exception {
        mockMvc.perform(post("/api/organizations")
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"id\":\"Gungans\"}"))
            .andExpect(status().isOk());

        assertTrue(organizationsRepository.existsById("Gungans"));
    }

    @Test
    void deleteOrganization_shouldDeleteOrganization() throws Exception {
        organizationsRepository.save(new Organization("First Order"));

        mockMvc.perform(delete("/api/organizations/First Order")
            .with(csrf()))
            .andExpect(status().isOk());

        assertFalse(organizationsRepository.existsById("First Order"));
    }

    @Nested
    class AccessControl {

        @Test
        @WithMockUser("Jar Jar Binks")
        void organizations_shouldBeAccessible_forStandardUsers() {
            Assertions.assertDoesNotThrow(() -> organizationsRestController.organizations());
        }

        @Test
        @WithMockUser("Jar Jar Binks")
        void createOrganization_shouldBeForbidden_forStandardUsers() {
            assertThrows(AccessDeniedException.class, () -> organizationsRestController.createOrganization(new Organization("Gungans")));
        }

        @Test
        @WithMockUser("Jar Jar Binks")
        void deleteOrganization_shouldBeForbidden_forStandardUsers() {
            assertThrows(AccessDeniedException.class, () -> organizationsRestController.deleteOrganization("Gungans"));
        }

        @Test
        @WithMockUser(value = "admin", roles = "ADMIN")
        void organizations_shouldBeAccessible_forAdminUser() {
            Assertions.assertDoesNotThrow(() -> organizationsRestController.organizations());
        }

    }

}
