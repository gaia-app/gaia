package io.codeka.gaia.modules.controller;

import io.codeka.gaia.modules.repository.TerraformModuleGitRepository;
import io.codeka.gaia.modules.repository.TerraformModuleRepository;
import io.codeka.gaia.teams.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Optional;

@Controller
public class ModulesMVCController {

    private TerraformModuleRepository terraformModuleRepository;
    private TerraformModuleGitRepository terraformModuleGitRepository;

    @Autowired
    public ModulesMVCController(
            TerraformModuleRepository terraformModuleRepository,
            TerraformModuleGitRepository terraformModuleGitRepository) {
        this.terraformModuleRepository = terraformModuleRepository;
        this.terraformModuleGitRepository = terraformModuleGitRepository;
    }

//    @GetMapping("/modules")
    public String modulesList(){
        return "modules";
    }

//    @GetMapping("/modules/import")
    public String importModule() {
        return "modules_import";
    }

//    @GetMapping("/modules/{id}")
    public String module(@PathVariable String id, Model model, User user){
        var module = terraformModuleRepository.findById(id).orElseThrow(ModuleNotFoundException::new);
        if(!module.isAuthorizedFor(user)){
            throw new ModuleForbiddenException();
        }
        model.addAttribute("module", module);
        return "module";
    }

//    @GetMapping("/modules/{id}/description")
    public String description(@PathVariable String id, Model model) {
        model.addAttribute("module", terraformModuleRepository.findById(id).orElseThrow());
        return "module_description";
    }

    @GetMapping("/modules/{id}/readme")
    @Produces(MediaType.TEXT_PLAIN)
    @ResponseBody
    public Optional<String> readme(@PathVariable String id) {
        var module = terraformModuleRepository.findById(id).orElseThrow();
        return terraformModuleGitRepository.getReadme(module);
    }

}

@ResponseStatus(HttpStatus.NOT_FOUND)
class ModuleNotFoundException extends RuntimeException{
}

@ResponseStatus(HttpStatus.FORBIDDEN)
class ModuleForbiddenException extends RuntimeException{
}
