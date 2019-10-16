package io.codeka.gaia.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    private OAuth2ClientProperties oAuth2ClientProperties;

    @Autowired(required = false)
    public void setOAuth2ClientProperties(OAuth2ClientProperties oAuth2ClientProperties) {
        this.oAuth2ClientProperties = oAuth2ClientProperties;
    }

    @GetMapping("/login")
    public String login(Model model) {
        if (oAuth2ClientProperties != null) {
            model.addAttribute("clients", oAuth2ClientProperties.getRegistration().keySet());
        }
        return "login";
    }
}
