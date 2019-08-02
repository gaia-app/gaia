package io.codeka.gaia.controller;

import io.codeka.gaia.bo.TerraformModule;
import io.codeka.gaia.repository.TerraformModuleGitRepository;
import io.codeka.gaia.repository.TerraformModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Optional;

@Controller
public class TerraformModuleController {

    private TerraformModuleRepository terraformModuleRepository;
    private TerraformModuleGitRepository terraformModuleGitRepository;

    @Autowired
    public TerraformModuleController(
            TerraformModuleRepository terraformModuleRepository,
            TerraformModuleGitRepository terraformModuleGitRepository) {
        this.terraformModuleRepository = terraformModuleRepository;
        this.terraformModuleGitRepository = terraformModuleGitRepository;
    }

    @GetMapping("/modules")
    public String modulesList(Model model){
        model.addAttribute("modules", terraformModuleRepository.findAll());

        return "modules";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/modules/{id}")
    public String module(@PathVariable String id, Model model){
        model.addAttribute("module", terraformModuleRepository.findById(id).orElseThrow());
        return "module";
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/modules/{id}")
    public String saveModule(@ModelAttribute TerraformModule module, Model model){
        terraformModuleRepository.save(module);
        return modulesList(model);
    }

    @GetMapping("/modules/{id}/description")
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
