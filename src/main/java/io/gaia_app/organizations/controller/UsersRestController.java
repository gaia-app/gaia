package io.gaia_app.organizations.controller;

import io.gaia_app.organizations.User;
import io.gaia_app.organizations.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Secured("ROLE_ADMIN")
public class UsersRestController {

    private UserService userService;

    @Autowired
    public UsersRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> users(){
        return this.userService.findAll();
    }

    @PostMapping
    public User createUser(@RequestBody User user){
        return this.userService.create(user);
    }

    @PutMapping("/{username}")
    public User saveUser(@RequestBody User user){
        return this.userService.update(user);
    }

    @PutMapping("/{username}/password")
    public void changeUserPassword(@PathVariable String username, @RequestParam String password){
        this.userService.changeUserPassword(username, password);
    }

    @DeleteMapping("/{username}")
    public void deleteUser(@PathVariable String username){
        this.userService.deleteUser(username);
    }

}
