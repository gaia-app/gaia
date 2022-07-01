package io.gaia_app.organizations.controller;

import io.gaia_app.organizations.Organization;
import io.gaia_app.organizations.User;
import io.gaia_app.organizations.repository.UserRepository;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Simple integration test that validates the security configuration of the UsersRestController, and its http routes
 */
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(value = "admin", roles = "ADMIN")
class UsersRestControllerIT extends SharedMongoContainerTest {

    @Autowired
    private UsersRestController usersRestController;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mongo.emptyDatabase();
        mongo.runScript("00_organization.js");
        mongo.runScript("10_user.js");
    }

    @Test
    void users_shouldBeExposed_atSpecificUrl() throws Exception {
        mockMvc.perform(get("/api/users"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(5)))
            .andExpect(jsonPath("$..username", contains("admin", "Mary J", "Darth Vader", "Luke Skywalker", "selmak")))
            .andExpect(jsonPath("$..admin", contains(true, false, false, false, false)))
            .andExpect(jsonPath("$..organization.id", contains("Ze Organization", "Not Ze Organization")));
    }

    @Test
    void saveUser_shouldBeExposed_atSpecificUrl() throws Exception {
        mockMvc.perform(put("/api/users/Luke Skywalker")
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"username\":\"Luke Skywalker\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.username", is("Luke Skywalker")))
            .andExpect(jsonPath("$.admin", is(false)))
            .andExpect(jsonPath("$.organization", is(emptyOrNullString())));

        assertThat(userRepository.existsById("Luke Skywalker")).isTrue();
    }

    @Test
    void users_canBeChangedOfOrganization() throws Exception {
        // given
        assertThat(userRepository.findById("Darth Vader"))
            .isPresent()
            .map(User::getOrganization)
            .isNotPresent();

        // when
        mockMvc.perform(put("/api/users/Darth Vader")
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"username\": \"Darth Vader\",\"organization\": {\"id\": \"Sith\"}}"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.username", is("Darth Vader")))
            .andExpect(jsonPath("$.admin", is(false)))
            .andExpect(jsonPath("$.organization.id", is("Sith")));

        // then
        assertThat(userRepository.findById("Darth Vader"))
            .isPresent()
            .map(User::getOrganization)
            .hasValue(new Organization("Sith"));
    }

    @Test
    void users_shouldNotLeakTheirOAuth2Credentials() throws Exception {
        mockMvc.perform(get("/api/users"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[4].username", is("selmak")))
            .andExpect(jsonPath("$[4].oauth2User.provider", is("github")))
            .andExpect(jsonPath("$[4].oauth2User.token").doesNotExist());
    }

    @Test
    void users_shouldNotLeakTheirPassword() throws Exception {
        mockMvc.perform(get("/api/users"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].username", is("admin")))
            .andExpect(jsonPath("$[0].password").doesNotExist());
    }

    @Test
    void createUser_shouldSaveANewUser() throws Exception {
        mockMvc.perform(post("/api/users")
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"username\":\"Obi Wan Kenobi\", \"password\": \"Use the Force, Luke.\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.username", is("Obi Wan Kenobi")))
            .andExpect(jsonPath("$.admin", is(false)))
            .andExpect(jsonPath("$.organization", is(emptyOrNullString())));

        assertThat(userRepository.existsById("Obi Wan Kenobi")).isTrue();
    }

    @Test
    void deleteUser_shouldDeleteAUser() throws Exception {
        var jarJarBinks = new User("Jar Jar Binks", null);
        userRepository.save(jarJarBinks);

        mockMvc.perform(delete("/api/users/Jar Jar Binks")
            .with(csrf()))
            .andExpect(status().isOk());

        assertThat(userRepository.existsById("Jar Jar Binks")).isFalse();
    }

    @Test
    void changeUserPassword() throws Exception {
        var newPassword = "I find your lack of faith disturbing";
        mockMvc.perform(put("/api/users/Darth Vader/password")
            .with(csrf())
            .param("password", newPassword))
            .andExpect(status().isOk());

        var darthVader = userRepository.findById("Darth Vader");
        assertThat(darthVader)
            .isPresent()
            .hasValueSatisfying(vader -> {
                assertTrue(passwordEncoder.matches(newPassword, vader.getPassword()));
            });
    }

    @Nested
    class AccessControl {

        @Test
        @WithMockUser("Jar Jar")
        void users_shouldNotBeAccessible_forStandardUsers() {
            assertThrows(AccessDeniedException.class, () -> usersRestController.users());
        }

        @Test
        @WithMockUser(value = "admin", roles = "ADMIN")
        void users_shouldBeAccessible_forAdminUser() {
            Assertions.assertDoesNotThrow(() -> usersRestController.users());
        }

        @Test
        @WithMockUser("Jar Jar")
        void createUser_shouldNotBeAccessible_forStandardUsers() {
            assertThrows(AccessDeniedException.class, () -> usersRestController.createUser(new User("Darth Vader", null)));
        }

        @Test
        @WithMockUser("Jar Jar")
        void saveUser_shouldNotBeAccessible_forStandardUsers() {
            assertThrows(AccessDeniedException.class, () -> usersRestController.saveUser(new User("Darth Vader", null)));
        }

        @Test
        @WithMockUser("Jar Jar")
        void deleteUser_shouldNotBeAccessible_forStandardUsers() {
            assertThrows(AccessDeniedException.class, () -> usersRestController.deleteUser("Darth Vader"));
        }

    }

}
