package io.codeka.gaia.controller;

import io.codeka.gaia.bo.Stack;
import io.codeka.gaia.repository.StackRepository;
import io.codeka.gaia.teams.bo.Team;
import io.codeka.gaia.teams.bo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/stacks")
public class StackRestController {

    private StackRepository stackRepository;

    @Autowired
    public StackRestController(StackRepository stackRepository) {
        this.stackRepository = stackRepository;
    }

    @GetMapping
    public List<Stack> listStacks(User user){
        if(user.isAdmin()){
            return stackRepository.findAll();
        }
        return stackRepository.findByOwnerTeam(user.getTeam());
    }

    @GetMapping("/{id}")
    public Stack getStack(@PathVariable String id, User user){
        if(user.isAdmin()){
            return stackRepository.findById(id).orElseThrow(StackNotFoundException::new);
        }
        return stackRepository.findByIdAndOwnerTeam(id, user.getTeam()).orElseThrow(StackNotFoundException::new);
    }

    @PostMapping()
    public Stack save(@RequestBody Stack stack, Team userTeam){
        stack.setOwnerTeam(userTeam);
        stack.setId(UUID.randomUUID().toString());
        return stackRepository.save(stack);
    }

    @PutMapping("/{id}")
    public Stack update(@PathVariable String id, @RequestBody Stack stack){
        return stackRepository.save(stack);
    }

}

@ResponseStatus(HttpStatus.NOT_FOUND)
class StackNotFoundException extends RuntimeException {
}
