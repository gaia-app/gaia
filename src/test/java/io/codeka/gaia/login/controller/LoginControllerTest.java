package io.codeka.gaia.login.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.ui.Model;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginControllerTest {

    private LoginController controller;

    @Mock
    OAuth2ClientProperties oAuth2ClientProperties;

    @Mock
    Model model;

    @BeforeEach
    void setup() {
        controller = new LoginController();
        controller.setOAuth2ClientProperties(oAuth2ClientProperties);
    }

    @Test
    void login_shouldAddOAuth2ClientsInModel_whenConfigured() {
        // given
        var oAuth2clients = new HashMap<String, OAuth2ClientProperties.Registration>();
        oAuth2clients.put("client_1", new OAuth2ClientProperties.Registration());
        oAuth2clients.put("client_2", new OAuth2ClientProperties.Registration());
        oAuth2clients.put("client_3", new OAuth2ClientProperties.Registration());

        // when
        when(oAuth2ClientProperties.getRegistration()).thenReturn(oAuth2clients);
        controller.login(model);

        // then
        verify(model).addAttribute("clients", oAuth2clients.keySet());
    }

    @Test
    void login_shouldIgnoreOAuth2_whenNotConfigured() {
        // when
        controller.setOAuth2ClientProperties(null);
        controller.login(model);

        // then
        verify(model, never()).addAttribute(eq("clients"), any());
    }

    @Test
    void login_shouldReturnTheCorrectView() {
        // when
        var result = controller.login(model);

        // then
        assertEquals("login", result);
    }
}