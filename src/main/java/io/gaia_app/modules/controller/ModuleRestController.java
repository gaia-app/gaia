package io.gaia_app.modules.controller;

import io.gaia_app.modules.bo.TerraformModule;
import io.gaia_app.modules.repository.TerraformModuleGitRepository;
import io.gaia_app.modules.repository.TerraformModuleRepository;
import io.gaia_app.teams.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Rest controller for the module API
 */
@RestController
@RequestMapping("/api/modules")
@Secured({"ROLE_USER","ROLE_ADMIN"})
public class ModuleRestController {

    private TerraformModuleRepository moduleRepository;

    private TerraformModuleGitRepository moduleGitRepository;

    @Autowired
    public ModuleRestController(TerraformModuleRepository moduleRepository, TerraformModuleGitRepository moduleGitRepository) {
        this.moduleRepository = moduleRepository;
        this.moduleGitRepository = moduleGitRepository;
    }

    @GetMapping
    public List<TerraformModule> findAllModules(User user){
        if(user.isAdmin()){
            return moduleRepository.findAll();
        }
        if(user.getTeam() != null){
            return moduleRepository.findAllByModuleMetadataCreatedByOrAuthorizedTeamsContaining(user, user.getTeam());
        }
        return moduleRepository.findAllByModuleMetadataCreatedBy(user);
    }

    @GetMapping("/{id}")
    public TerraformModule findModule(@PathVariable String id, User user){
        var module = moduleRepository.findById(id).orElseThrow(ModuleNotFoundException::new);
        if(!module.isAuthorizedFor(user)){
            throw new ModuleForbiddenException();
        }
        return module;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TerraformModule createModule(@RequestBody TerraformModule module, User user){
        module.setId(UUID.randomUUID().toString());
        module.getModuleMetadata().setCreatedBy(user);
        return moduleRepository.save(module);
    }

    @PutMapping("/{id}")
    public TerraformModule saveModule(@PathVariable String id, @RequestBody @Valid TerraformModule module, User user){
        var existingModule = moduleRepository.findById(id).orElseThrow(ModuleNotFoundException::new);
        if(!existingModule.isAuthorizedFor(user)){
            throw new ModuleForbiddenException();
        }

        module.getModuleMetadata().setUpdatedBy(user);
        module.getModuleMetadata().setUpdatedAt(LocalDateTime.now());

        return moduleRepository.save(module);
    }

    @GetMapping(value = "/{id}/readme", produces = MediaType.TEXT_PLAIN_VALUE)
    public Optional<String> readme(@PathVariable String id) {
        var module = moduleRepository.findById(id).orElseThrow();
        return moduleGitRepository.getReadme(module);
    }

}

@ResponseStatus(HttpStatus.NOT_FOUND)
class ModuleNotFoundException extends RuntimeException{
}

@ResponseStatus(HttpStatus.FORBIDDEN)
class ModuleForbiddenException extends RuntimeException{
}

