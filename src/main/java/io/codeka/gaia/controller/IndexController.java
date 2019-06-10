package io.codeka.gaia.controller;

import io.codeka.gaia.bo.StackState;
import io.codeka.gaia.repository.StackRepository;
import io.codeka.gaia.repository.TerraformModuleRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    private TerraformModuleRepository moduleRepository;

    private StackRepository stackRepository;

    public IndexController(TerraformModuleRepository moduleRepository, StackRepository stackRepository) {
        this.moduleRepository = moduleRepository;
        this.stackRepository = stackRepository;
    }

    @GetMapping("/")
    String index(Model model){
        model.addAttribute("moduleCount", this.moduleRepository.count());

        model.addAttribute("runningStackCount", stackRepository.countStacksByState(StackState.RUNNING));
        model.addAttribute("toUpdateStackCount", stackRepository.countStacksByState(StackState.TO_UPDATE));

        return "index";
    }

}
