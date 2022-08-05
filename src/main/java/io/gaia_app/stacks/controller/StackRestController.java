package io.gaia_app.stacks.controller;

import io.gaia_app.credentials.CredentialsRepository;
import io.gaia_app.stacks.bo.Job;
import io.gaia_app.stacks.bo.JobType;
import io.gaia_app.stacks.bo.Stack;
import io.gaia_app.stacks.repository.JobRepository;
import io.gaia_app.stacks.repository.StackRepository;
import io.gaia_app.stacks.repository.TerraformStateRepository;
import io.gaia_app.stacks.service.StackCostCalculator;
import io.gaia_app.organizations.Organization;
import io.gaia_app.organizations.User;
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

    private JobRepository jobRepository;

    private CredentialsRepository credentialsRepository;

    private TerraformStateRepository stateRepository;

    @Autowired
    public StackRestController(
        StackRepository stackRepository,
        StackCostCalculator stackCostCalculator,
        JobRepository jobRepository,
        CredentialsRepository credentialsRepository,
        TerraformStateRepository stateRepository) {
        this.stackRepository = stackRepository;
        this.stackCostCalculator = stackCostCalculator;
        this.jobRepository = jobRepository;
        this.credentialsRepository = credentialsRepository;
        this.stateRepository = stateRepository;
    }

    @GetMapping
    public List<Stack> listStacks(User user) {
        if (user.isAdmin()) {
            return stackRepository.findAll();
        } else if (user.getOrganization() != null) {
            return stackRepository.findByOwnerOrganization(user.getOrganization());
        }
        return stackRepository.findByCreatedBy(user);
    }

    @GetMapping("/{id}")
    public Stack getStack(@PathVariable String id, User user) {
        Stack stack;
        if (user.isAdmin()) {
            stack = stackRepository.findById(id).orElseThrow(StackNotFoundException::new);
        } else if (user.getOrganization() != null) {
            stack = stackRepository.findByIdAndOwnerOrganization(id, user.getOrganization()).orElseThrow(StackNotFoundException::new);
        } else {
            stack = stackRepository.findById(id).orElseThrow(StackNotFoundException::new);
        }
        stack.setEstimatedRunningCost(stackCostCalculator.calculateRunningCostEstimation(stack));
        return stack;
    }

    @PostMapping
    public Stack save(@RequestBody @Valid Stack stack, Organization userOrganization, User user) {
        stack.setOwnerOrganization(userOrganization);
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

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id, User user){
        // try to load stack for the current user
        var stack = this.getStack(id, user);
        if(!stack.isArchived()){
            throw new StackArchivedException();
        }
        // delete stack if it was found
        stackRepository.delete(stack);
        jobRepository.deleteByStackId(id);
        stateRepository.deleteById(id);
    }

    @PostMapping("/{id}/{jobType}")
    public Map<String, String> launchJob(@PathVariable String id, @PathVariable JobType jobType, User user) {
        // get the stack
        var stack = this.stackRepository.findById(id).orElseThrow(StackNotFoundException::new);

        // do not launch jobs of archived stacks
        if (stack.isArchived()) {
            throw new StackArchivedException();
        }

        // create a new job
        var job = new Job(jobType, id, user);
        job.setTerraformImage(stack.getModule().getTerraformImage());
        if (stack.getCredentialsId() != null) {
            this.credentialsRepository.findById(stack.getCredentialsId())
                .ifPresent(job::setCredentials);
        }
        jobRepository.save(job);

        return Map.of("jobId", job.getId());
    }

}

@ResponseStatus(HttpStatus.NOT_FOUND)
class StackNotFoundException extends RuntimeException {
}

@ResponseStatus(HttpStatus.FORBIDDEN)
class StackArchivedException extends RuntimeException {
}
