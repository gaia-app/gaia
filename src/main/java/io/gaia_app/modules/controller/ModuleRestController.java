package io.gaia_app.modules.controller;

import io.gaia_app.modules.ModuleService;
import io.gaia_app.modules.bo.TerraformModule;
import io.gaia_app.modules.repository.TerraformModuleGitRepository;
import io.gaia_app.modules.repository.TerraformModuleRepository;
import io.gaia_app.registries.service.RegistryService;
import io.gaia_app.organizations.User;
import io.gaia_app.stacks.repository.StackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Rest controller for the module API
 */
@RestController
@RequestMapping("/api/modules")
@Secured({"ROLE_USER", "ROLE_ADMIN"})
public class ModuleRestController {

    private TerraformModuleRepository moduleRepository;

    private TerraformModuleGitRepository moduleGitRepository;

    private RegistryService registryService;

    private ModuleService moduleService;

    private StackRepository stackRepository;

    @Autowired
    public ModuleRestController(TerraformModuleRepository moduleRepository, TerraformModuleGitRepository moduleGitRepository, RegistryService registryService, ModuleService moduleService, StackRepository stackRepository) {
        this.moduleRepository = moduleRepository;
        this.moduleGitRepository = moduleGitRepository;
        this.registryService = registryService;
        this.moduleService = moduleService;
        this.stackRepository = stackRepository;
    }

    @GetMapping
    public List<TerraformModule> findAllModules(User user) {
        if (user.isAdmin()) {
            return moduleRepository.findAll();
        }
        if (user.getOrganization() != null) {
            return moduleRepository.findAllByModuleMetadataCreatedByOrAuthorizedOrganizationsContaining(user, user.getOrganization());
        }
        return moduleRepository.findAllByModuleMetadataCreatedBy(user);
    }

    @GetMapping("/{id}")
    public TerraformModule findModule(@PathVariable String id, User user) {
        var module = moduleRepository.findById(id).orElseThrow(ModuleNotFoundException::new);
        if (!module.isAuthorizedFor(user)) {
            throw new ModuleForbiddenException();
        }
        return module;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TerraformModule createModule(@RequestBody TerraformModule module, User user) {
        module.setId(UUID.randomUUID().toString());
        module.getModuleMetadata().setCreatedBy(user);
        return moduleRepository.save(module);
    }

    @PutMapping("/{id}")
    public TerraformModule saveModule(@PathVariable String id, @RequestBody @Valid TerraformModule module, User user) {
        var existingModule = moduleRepository.findById(id).orElseThrow(ModuleNotFoundException::new);
        if (!existingModule.isAuthorizedFor(user)) {
            throw new ModuleForbiddenException();
        }

        // try to update registry details
        moduleService.updateRegistryDetails(module);

        module.getModuleMetadata().setUpdatedBy(user);
        module.getModuleMetadata().setUpdatedAt(LocalDateTime.now());

        return moduleRepository.save(module);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteModule(@PathVariable String id, User user) {
        var existingModule = moduleRepository.findById(id).orElseThrow(ModuleNotFoundException::new);
        if (!existingModule.isAuthorizedFor(user)) {
            throw new ModuleForbiddenException();
        }

        // find if module is still used by stacks
        if( this.stackRepository.countStacksByModule(existingModule) > 0 ){
            throw new CannotDeleteModuleException();
        }

        moduleRepository.delete(existingModule);
    }

    @GetMapping(value = "/{id}/readme", produces = MediaType.TEXT_PLAIN_VALUE)
    public String readme(@PathVariable String id) {
        var module = moduleRepository.findById(id).orElseThrow();
        return moduleGitRepository.getReadme(module).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    /**
     * Refresh the module definition (variables/outputs) from the repository (git)
     *
     * @param id   id of the module to refresh
     * @param user loggued user
     * @return the refreshed module
     */
    @PostMapping(value = "/{id}/refresh")
    public TerraformModule refreshModule(@PathVariable String id, User user) {
        var existingModule = moduleRepository.findById(id).orElseThrow(ModuleNotFoundException::new);
        if (!existingModule.isAuthorizedFor(user)) {
            throw new ModuleForbiddenException();
        }

        var projectId = existingModule.getRegistryDetails().getProjectId();
        var registryType = existingModule.getRegistryDetails().getRegistryType();

        // refresh variables & outputs
        existingModule.setVariables(registryService.importVariables(projectId, registryType, user));
        existingModule.setOutputs(registryService.importOutputs(projectId, registryType, user));

        existingModule.getModuleMetadata().setUpdatedBy(user);
        existingModule.getModuleMetadata().setUpdatedAt(LocalDateTime.now());

        return moduleRepository.save(existingModule);
    }

}

@ResponseStatus(HttpStatus.NOT_FOUND)
class ModuleNotFoundException extends RuntimeException {
}

@ResponseStatus(HttpStatus.FORBIDDEN)
class ModuleForbiddenException extends RuntimeException {
}

@ResponseStatus(HttpStatus.BAD_REQUEST)
class CannotDeleteModuleException extends RuntimeException {

    public CannotDeleteModuleException() {
        super("Module cannot be deleted as some stacks still use it.");
    }
}

