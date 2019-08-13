package io.codeka.gaia.teams.controller;

import io.codeka.gaia.teams.bo.User;
import io.codeka.gaia.teams.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
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
    void user_shouldBeLoadedFromRepository(){
        // given
        var bob = new User("bob");
        when(userRepository.findById("bob")).thenReturn(Optional.of(bob));
        when(userRepository.existsById("bob")).thenReturn(true);

        when(authentication.getName()).thenReturn("bob");

        // when
        var result = userControllerAdvice.user(authentication);

        // then
        assertThat(result).isEqualTo(bob);
        verify(userRepository).existsById("bob");
        verify(userRepository).findById("bob");
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void newUsers_shouldBeAddedToTheRepository(){
        // given
        when(userRepository.existsById("bob")).thenReturn(false);

        when(authentication.getName()).thenReturn("bob");

        // when
        var result = userControllerAdvice.user(authentication);

        // then
        verify(userRepository).existsById("bob");
        verify(userRepository).save(any(User.class));
        verifyNoMoreInteractions(userRepository);
    }

}