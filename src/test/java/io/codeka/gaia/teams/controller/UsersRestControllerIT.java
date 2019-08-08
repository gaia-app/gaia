package io.codeka.gaia.teams.controller;

import io.codeka.gaia.config.SecurityConfig;
import io.codeka.gaia.teams.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Simple integration test that validates the security configuration of the UsersRestController, and its http routes
 */
@SpringBootTest(classes={UsersRestController.class, SecurityConfig.class})
@AutoConfigureWebMvc
@AutoConfigureMockMvc
@WithMockUser(value = "admin", roles = "ADMIN")
class UsersRestControllerIT {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UsersRestController usersRestController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser("Matthew Bellamy")
    void users_shouldNotBeAccessible_forStandardUsers(){
        assertThrows(AccessDeniedException.class, () -> usersRestController.users());
    }

    @Test
    void users_shouldBeAccessible_forAdminUser(){
        assertDoesNotThrow(() -> usersRestController.users());
    }

    @Test
    void users_shouldBeExposed_atSpecificUrl() throws Exception {
        mockMvc.perform(get("/api/users")).andExpect(status().isOk());

        verify(userRepository).findAll();
    }

    @Test
    void saveUser_shouldBeExposed_atSpecificUrl() throws Exception {
        mockMvc.perform(put("/api/users/test")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"Bob\"}")).andExpect(status().isOk());

        verify(userRepository).save(any());
    }

}