package io.codeka.gaia.teams.controller;

import io.codeka.gaia.teams.User;
import io.codeka.gaia.teams.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UsersRestControllerTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UsersRestController usersRestController;

    @Test
    void users_shouldReturnAllTeams() {
        usersRestController.users();

        verify(userRepository).findAll();
    }

    @Test
    void saveUser_shouldSaveTheUser() {
        var john = new User("john", null);
        usersRestController.saveUser(john);

        verify(userRepository).save(john);
    }
}