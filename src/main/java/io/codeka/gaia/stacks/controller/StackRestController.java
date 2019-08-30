package io.codeka.gaia.stacks.controller;

import io.codeka.gaia.stacks.bo.Stack;
import io.codeka.gaia.stacks.repository.StackRepository;
import io.codeka.gaia.stacks.service.StackCostCalculator;
import io.codeka.gaia.teams.bo.Team;
import io.codeka.gaia.teams.bo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/stacks")
public class StackRestController {

    private StackRepository stackRepository;

    private StackCostCalculator stackCostCalculator;

    @Autowired
    public StackRestController(StackRepository stackRepository, StackCostCalculator stackCostCalculator) {
        this.stackRepository = stackRepository;
        this.stackCostCalculator = stackCostCalculator;
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
        Stack stack;
        if(user.isAdmin()){
            stack = stackRepository.findById(id).orElseThrow(StackNotFoundException::new);
        }
        else{
            stack = stackRepository.findByIdAndOwnerTeam(id, user.getTeam()).orElseThrow(StackNotFoundException::new);
        }
        stack.setEstimatedRunningCost(stackCostCalculator.calculateRunningCostEstimation(stack));
        return stack;
    }

    @PostMapping
    public Stack save(@RequestBody @Valid Stack stack, Team userTeam, User user){
        stack.setOwnerTeam(userTeam);
        stack.setId(UUID.randomUUID().toString());
        stack.setCreatedBy(user);
        stack.setCreatedAt(LocalDateTime.now());
        return stackRepository.save(stack);
    }

    @PutMapping("/{id}")
    public Stack update(@PathVariable String id, @RequestBody @Valid Stack stack, User user){
        stack.setUpdatedBy(user);
        stack.setUpdatedAt(LocalDateTime.now());
        return stackRepository.save(stack);
    }

}

@ResponseStatus(HttpStatus.NOT_FOUND)
class StackNotFoundException extends RuntimeException {
}
