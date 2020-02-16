package io.codeka.gaia.teams.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Secured("ROLE_ADMIN")
public class UsersMVCController {

//    @GetMapping("/users")
    public String users(){
        return "users";
    }

}
