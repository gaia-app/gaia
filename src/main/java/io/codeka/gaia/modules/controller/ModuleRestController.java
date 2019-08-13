package io.codeka.gaia.modules.controller;

import io.codeka.gaia.modules.bo.TerraformModule;
import io.codeka.gaia.modules.repository.TerraformModuleRepository;
import io.codeka.gaia.teams.bo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Rest controller for the module API
 */
@RestController
@RequestMapping("/api/modules")
public class ModuleRestController {

    private TerraformModuleRepository moduleRepository;

    @Autowired
    public ModuleRestController(TerraformModuleRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
    }

    @GetMapping("")
    public List<TerraformModule> findAllModules(User user){
        if(user.isAdmin()){
            return moduleRepository.findAll();
        }
        return moduleRepository.findAllByAuthorizedTeamsContaining(user.getTeam());
    }

    @GetMapping("/{id}")
    public TerraformModule findModule(@PathVariable String id, User user){
        if(user.isAdmin()){
            return moduleRepository.findById(id).orElseThrow(ModuleNotFoundException::new);
        }
        return moduleRepository.findByIdAndAuthorizedTeamsContaining(id, user.getTeam()).orElseThrow(ModuleNotFoundException::new);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/{id}")
    public TerraformModule saveModule(@PathVariable String id, @RequestBody TerraformModule module){
        return moduleRepository.save(module);
    }

}