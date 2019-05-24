package io.codeka.gaia.controller;

import io.codeka.gaia.repository.TerraformModuleRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    private TerraformModuleRepository moduleRepository;

    public IndexController(TerraformModuleRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
    }

    @GetMapping("/")
    String index(Model model){
        var moduleCount = this.moduleRepository.count();
        model.addAttribute("moduleCount", moduleCount);
        return "index";
    }

}
