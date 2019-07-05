package io.codeka.gaia.controller;

import io.codeka.gaia.bo.TerraformModule;
import io.codeka.gaia.repository.TerraformModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TerraformModuleController {

    private TerraformModuleRepository terraformModuleRepository;

    @Autowired
    public TerraformModuleController(TerraformModuleRepository terraformModuleRepository) {
        this.terraformModuleRepository = terraformModuleRepository;
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

}
