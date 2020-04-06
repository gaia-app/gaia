package io.codeka.gaia.stacks.controller;

import io.codeka.gaia.modules.repository.TerraformModuleRepository;
import io.codeka.gaia.stacks.bo.Job;
import io.codeka.gaia.stacks.bo.JobType;
import io.codeka.gaia.stacks.bo.Stack;
import io.codeka.gaia.stacks.repository.JobRepository;
import io.codeka.gaia.stacks.repository.StackRepository;
import io.codeka.gaia.stacks.service.StackCostCalculator;
import io.codeka.gaia.teams.Team;
import io.codeka.gaia.teams.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/stacks")
public class StackRestController {

    private StackRepository stackRepository;

    private StackCostCalculator stackCostCalculator;

    private TerraformModuleRepository terraformModuleRepository;

    private JobRepository jobRepository;

    @Autowired
    public StackRestController(
        StackRepository stackRepository,
        StackCostCalculator stackCostCalculator,
        TerraformModuleRepository terraformModuleRepository,
        JobRepository jobRepository) {
        this.stackRepository = stackRepository;
        this.stackCostCalculator = stackCostCalculator;
        this.terraformModuleRepository = terraformModuleRepository;
        this.jobRepository = jobRepository;
    }

    @GetMapping
    public List<Stack> listStacks(User user) {
        if (user.isAdmin()) {
            return stackRepository.findAll();
        } else if (user.getTeam() != null) {
            return stackRepository.findByOwnerTeam(user.getTeam());
        }
        return stackRepository.findByCreatedBy(user);
    }

    @GetMapping("/{id}")
    public Stack getStack(@PathVariable String id, User user) {
        Stack stack;
        if (user.isAdmin()) {
            stack = stackRepository.findById(id).orElseThrow(StackNotFoundException::new);
        } else if (user.getTeam() != null) {
            stack = stackRepository.findByIdAndOwnerTeam(id, user.getTeam()).orElseThrow(StackNotFoundException::new);
        } else {
            stack = stackRepository.findById(id).orElseThrow(StackNotFoundException::new);
        }
        stack.setEstimatedRunningCost(stackCostCalculator.calculateRunningCostEstimation(stack));
        return stack;
    }

    @PostMapping
    public Stack save(@RequestBody @Valid Stack stack, Team userTeam, User user) {
        stack.setOwnerTeam(userTeam);
        stack.setId(UUID.randomUUID().toString());
        stack.setCreatedBy(user);
        stack.setCreatedAt(LocalDateTime.now());
        return stackRepository.save(stack);
    }

    @PutMapping("/{id}")
    public Stack update(@PathVariable String id, @RequestBody @Valid Stack stack, User user) {
        stack.setUpdatedBy(user);
        stack.setUpdatedAt(LocalDateTime.now());
        return stackRepository.save(stack);
    }

    @PostMapping("/{id}/{jobType}")
    public Map<String, String> launchJob(@PathVariable String id, @PathVariable JobType jobType, User user) {
        // get the stack
        var stack = this.stackRepository.findById(id).orElseThrow(StackNotFoundException::new);

        // get the module
        var module = this.terraformModuleRepository.findById(stack.getModuleId()).orElseThrow();

        // create a new job
        var job = new Job(jobType, id, user);
        job.setTerraformImage(module.getTerraformImage());
        jobRepository.save(job);

        return Map.of("jobId", job.getId());
    }

}

@ResponseStatus(HttpStatus.NOT_FOUND)
class StackNotFoundException extends RuntimeException {
}
