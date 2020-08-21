package io.gaia_app.runner;

import io.gaia_app.stacks.repository.StackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for the operations that are called by the runner only
 */
@RestController
@RequestMapping("/api/runner")
public class RunnerController {

    @Autowired
    private StackRepository stackRepository;

    @GetMapping(value = "/stacks/{id}.tfvars", produces = "text/plain")
    public String tfvars(@PathVariable String id){
        var stack = stackRepository.findById(id).orElseThrow();
        return stack.tfvars();
    }
}
