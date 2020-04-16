package io.codeka.gaia.teams.controller;

import io.codeka.gaia.teams.Team;
import io.codeka.gaia.teams.User;
import io.codeka.gaia.teams.repository.UserRepository;
import io.codeka.gaia.test.MongoContainer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Simple integration test that validates the security configuration of the UsersRestController, and its http routes
 */
@SpringBootTest
@DirtiesContext
@Testcontainers
@AutoConfigureMockMvc
@WithMockUser(value = "admin", roles = "ADMIN")
class UsersRestControllerIT {

    @Container
    private static MongoContainer mongoContainer = new MongoContainer()
            .withScript("src/test/resources/db/00_team.js")
            .withScript("src/test/resources/db/10_user.js");

    @Autowired
    private UsersRestController usersRestController;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser("Matthew Bellamy")
    void users_shouldNotBeAccessible_forStandardUsers() {
        assertThrows(AccessDeniedException.class, () -> usersRestController.users());
    }

    @Test
    void users_shouldBeAccessible_forAdminUser() {
        assertDoesNotThrow(() -> usersRestController.users());
    }

    @Test
    void users_shouldBeExposed_atSpecificUrl() throws Exception {
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$..username", contains("admin", "Mary J", "Darth Vader", "selmak")))
                .andExpect(jsonPath("$..admin", contains(true, false, false, false)))
                .andExpect(jsonPath("$..team.id", contains("Ze Team", "Not Ze Team")));
    }

    @Test
    void saveUser_shouldBeExposed_atSpecificUrl() throws Exception {
        mockMvc.perform(put("/api/users/test")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"Bob\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("Bob")))
                .andExpect(jsonPath("$.admin", is(false)))
                .andExpect(jsonPath("$.team", isEmptyOrNullString()));

        assertThat(userRepository.existsById("Bob")).isTrue();
    }

    @Test
    void users_canBeChangedOfTeam() throws Exception {
        // given
        assertThat(userRepository.findById("Darth Vader"))
                .isPresent()
                .map(User::getTeam)
                .isNotPresent();

        // when
        mockMvc.perform(put("/api/users/Darth Vader")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"Darth Vader\",\"team\": {\"id\": \"Sith\"}}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("Darth Vader")))
                .andExpect(jsonPath("$.admin", is(false)))
                .andExpect(jsonPath("$.team.id", is("Sith")));

        // then
        assertThat(userRepository.findById("Darth Vader"))
                .isPresent()
                .map(User::getTeam)
                .hasValue(new Team("Sith"));

        mongoContainer.resetDatabase();
    }

    @Test
    void users_shouldNotLeakTheirOAuth2Credentials() throws Exception {
        mockMvc.perform(get("/api/users"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[3].username", is("selmak")))
            .andExpect(jsonPath("$[3].oauth2User.provider", is("github")))
            .andExpect(jsonPath("$[3].oauth2User.token").doesNotExist());
    }

}
