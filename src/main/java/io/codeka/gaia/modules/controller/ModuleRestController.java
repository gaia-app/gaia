package io.codeka.gaia.modules.controller;

import io.codeka.gaia.modules.bo.TerraformModule;
import io.codeka.gaia.modules.repository.TerraformModuleRepository;
import io.codeka.gaia.teams.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Rest controller for the module API
 */
@RestController
@RequestMapping("/api/modules")
@Secured({"ROLE_USER","ROLE_ADMIN"})
public class ModuleRestController {

    private TerraformModuleRepository moduleRepository;

    @Autowired
    public ModuleRestController(TerraformModuleRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
    }

    @GetMapping
    public List<TerraformModule> findAllModules(User user){
        if(user.isAdmin()){
            return moduleRepository.findAll();
        }
        if(user.getTeam() != null){
            return moduleRepository.findAllByCreatedByOrAuthorizedTeamsContaining(user, user.getTeam());
        }
        return moduleRepository.findAllByCreatedBy(user);
    }

    @GetMapping("/{id}")
    public TerraformModule findModule(@PathVariable String id, User user){
        var module = moduleRepository.findById(id).orElseThrow(ModuleNotFoundException::new);
        if(!module.isAuthorizedFor(user)){
            throw new ModuleForbiddenException();
        }
        return module;
    }

    @PutMapping("/{id}")
    public TerraformModule saveModule(@PathVariable String id, @RequestBody @Valid TerraformModule module, User user){
        var existingModule = moduleRepository.findById(id).orElseThrow(ModuleNotFoundException::new);
        if(!existingModule.isAuthorizedFor(user)){
            throw new ModuleForbiddenException();
        }
        return moduleRepository.save(module);
    }

}