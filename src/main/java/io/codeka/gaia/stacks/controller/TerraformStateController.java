package io.codeka.gaia.stacks.controller;

import io.codeka.gaia.stacks.bo.TerraformState;
import io.codeka.gaia.stacks.repository.TerraformStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
public class TerraformStateController {

    private TerraformStateRepository repository;

    @Autowired
    public TerraformStateController(TerraformStateRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/api/state/{id}")
    public Map<String, Object> getState(@PathVariable String id){
        return repository.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND))
                .getValue();
    }

    @PostMapping("/api/state/{id}")
    public void postState(@PathVariable String id, @RequestBody Map<String, Object> body){
        var terraformState = new TerraformState();
        terraformState.setId(id);
        terraformState.setValue(body);
        repository.save(terraformState);
    }

}
