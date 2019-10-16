package io.codeka.gaia.config.security;

import io.codeka.gaia.teams.User;
import io.codeka.gaia.teams.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class SuccessHandler implements AuthenticationSuccessHandler {

    protected UserRepository userRepository;

    @Autowired
    public SuccessHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        if (!userRepository.existsById(authentication.getName())) {
            // enter this case especially for people authenticated with ldap
            userRepository.save(new User(authentication.getName(), null));
        }
        redirect(request, response);
    }

    protected void redirect(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var savedRequest = (DefaultSavedRequest) request.getSession().getAttribute("SPRING_SECURITY_SAVED_REQUEST");
        if (savedRequest == null) {
            // redirect to home page
            response.sendRedirect("/");
        } else {
            // redirect to asked page
            response.sendRedirect(savedRequest.getRequestURI());
        }
    }
}