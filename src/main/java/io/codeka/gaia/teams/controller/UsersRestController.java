package io.codeka.gaia.teams.controller;

import io.codeka.gaia.teams.User;
import io.codeka.gaia.teams.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Secured("ROLE_ADMIN")
public class UsersRestController {

    private UserRepository userRepository;

    @Autowired
    public UsersRestController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<User> users(){
        return this.userRepository.findAll();
    }

    @PutMapping("/{userId}")
    public User saveUser(@RequestBody User user){
        return this.userRepository.save(user);
    }

}
