package io.gaia_app.organizations.controller;

import io.gaia_app.organizations.Organization;
import io.gaia_app.organizations.User;
import io.gaia_app.organizations.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerAdviceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private UserControllerAdvice userControllerAdvice;

    @Test
    void user_shouldBeLoadedFromRepository() {
        // given
        var bob = new User("bob", null);

        // when
        when(userRepository.findById("bob")).thenReturn(Optional.of(bob));
        when(authentication.getName()).thenReturn("bob");
        var result = userControllerAdvice.user(authentication);

        // then
        assertThat(result).isEqualTo(bob);
        verify(userRepository).findById("bob");
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void user_shouldReturnNothing_withoutAuthentication() {
        assertNull(userControllerAdvice.user(null));
    }

    @Test
    void userOrganization_shouldOrganizationOfUser() {
        // given
        var the_wailers = new Organization("the_wailers");
        var marley = new User("marley", the_wailers);

        // when
        var result = userControllerAdvice.userOrganization(authentication, marley);

        // then
        assertThat(result).isEqualTo(the_wailers);
    }

    @Test
    void userOrganization_shouldReturnNothing_withoutAuthentication() {
        assertNull(userControllerAdvice.userOrganization(null, null));
    }
}
