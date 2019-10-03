package io.codeka.gaia.config.security;

import io.codeka.gaia.teams.User;
import io.codeka.gaia.teams.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SuccessHandlerTest {

    @Mock
    UserRepository userRepository;

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    Authentication authentication;

    @Mock
    HttpSession httpSession;

    @InjectMocks
    SuccessHandler successHandler;

    @BeforeEach
    void setup() {
        when(authentication.getName()).thenReturn("spencer");
        when(request.getSession()).thenReturn(httpSession);
    }

    @Test
    void onAuthenticationSuccess_shouldCreateUser_whenNotExists() throws IOException {
        // when
        when(userRepository.existsById(anyString())).thenReturn(false);
        successHandler.onAuthenticationSuccess(request, response, authentication);

        // then
        var captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(captor.capture());
        assertThat(captor.getValue()).isNotNull()
                .hasFieldOrPropertyWithValue("username", "spencer")
                .hasFieldOrPropertyWithValue("team", null);
    }

    @Test
    void onAuthenticationSuccess_shouldNotCreateUser_whenExists() throws IOException {
        // when
        when(userRepository.existsById(anyString())).thenReturn(true);
        successHandler.onAuthenticationSuccess(request, response, authentication);

        // then
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void onAuthenticationSuccess_shouldRedirectToHomePage() throws IOException {
        // when
        when(userRepository.existsById(anyString())).thenReturn(false);
        successHandler.onAuthenticationSuccess(request, response, authentication);

        // then
        verify(response).sendRedirect("/");
    }

    @Test
    void onAuthenticationSuccess_shouldRedirectToAskedPage_whenSpecified() throws IOException {
        // given
        var savedRequest = mock(DefaultSavedRequest.class);

        // when
        when(userRepository.existsById(anyString())).thenReturn(false);
        when(httpSession.getAttribute("SPRING_SECURITY_SAVED_REQUEST")).thenReturn(savedRequest);
        when(savedRequest.getRequestURI()).thenReturn("/test_url");
        successHandler.onAuthenticationSuccess(request, response, authentication);

        // then
        verify(response).sendRedirect("/test_url");
    }
}