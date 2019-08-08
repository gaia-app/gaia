package io.codeka.gaia.teams.controller;

import io.codeka.gaia.config.SecurityConfig;
import io.codeka.gaia.teams.repository.TeamRepository;
import io.codeka.gaia.test.MongoContainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Simple integration test that validates the security configuration of the TeamsRestController, and its http routes
 */
@SpringBootTest(classes={TeamsRestController.class, SecurityConfig.class})
@AutoConfigureWebMvc
@AutoConfigureMockMvc
@WithMockUser(value = "admin", roles = "ADMIN")
class TeamsRestControllerIT {

    @MockBean
    private TeamRepository teamRepository;

    @Autowired
    private TeamsRestController teamsRestController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser("Mary J")
    void teams_shouldNotBeAccessible_forStandardUsers(){
        assertThrows(AccessDeniedException.class, () -> teamsRestController.teams());
    }

    @Test
    void teams_shouldBeAccessible_forAdminUser(){
        assertDoesNotThrow(() -> teamsRestController.teams());
    }

    @Test
    void teams_shouldBeExposed_atSpecificUrl() throws Exception {
        mockMvc.perform(get("/api/teams")).andExpect(status().isOk());

        verify(teamRepository).findAll();
    }

}