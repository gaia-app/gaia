package io.codeka.gaia.teams.controller;

import io.codeka.gaia.teams.Team;
import io.codeka.gaia.teams.User;
import io.codeka.gaia.teams.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class UserControllerAdvice {

    @Autowired
    private UserRepository userRepository;

    @ModelAttribute
    public User user(Authentication authentication){
        // in case of anonymous access (like healthcheck)
        if (authentication == null){
            return null;
        }
        if (! userRepository.existsById(authentication.getName())){
            // creating user
            var user = new User(authentication.getName(), null);
            userRepository.save(user);
            return user;
        }
        return userRepository.findById(authentication.getName()).orElseThrow();
    }

    @ModelAttribute
    public Team userTeam(Authentication authentication, @ModelAttribute User user){
        // in case of anonymous access (like healthcheck)
        if (authentication == null){
            return null;
        }
        return user.getTeam();
    }


}
