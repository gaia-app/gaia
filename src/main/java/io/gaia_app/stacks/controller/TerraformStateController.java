package io.gaia_app.stacks.controller;

import io.gaia_app.stacks.bo.TerraformState;
import io.gaia_app.stacks.service.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
public class TerraformStateController {

    private StateService stateService;

    @Autowired
    public TerraformStateController(StateService stateService) {
        this.stateService = stateService;
    }

    @GetMapping({"/api/state/{id}", "/api/runner/state/{id}"})
    public Map<String, Object> getState(@PathVariable String id){
        return stateService.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND))
                .getValue();
    }

    @PostMapping({"/api/state/{id}", "/api/runner/state/{id}"})
    public void postState(@PathVariable String id, @RequestBody Map<String, Object> body){
        var terraformState = new TerraformState();
        terraformState.setId(id);
        terraformState.setValue(body);
        stateService.save(terraformState);
    }

}
