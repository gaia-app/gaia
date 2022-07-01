package io.gaia_app.organizations.controller;

import io.gaia_app.organizations.User;
import io.gaia_app.organizations.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UsersRestControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UsersRestController usersRestController;

    @Test
    void users_shouldReturnAllUsers() {
        usersRestController.users();

        verify(userService).findAll();
    }

    @Test
    void createUser_shouldSaveTheUser() {
        var john = new User("john", null);
        usersRestController.createUser(john);

        verify(userService).create(john);
    }


    @Test
    void saveUser_shouldSaveTheUser() {
        var john = new User("john", null);
        usersRestController.saveUser(john);

        verify(userService).update(john);
    }

    @Test
    void deleteUser_shouldDeleteTheUser() {
        usersRestController.deleteUser("john");

        verify(userService).deleteUser("john");
    }

    @Test
    void changeUserPassword_shouldChangeThePassword() {
        usersRestController.changeUserPassword("john", "password");

        verify(userService).changeUserPassword("john", "password");
    }
}
