package io.codeka.gaia.controller;

import io.codeka.gaia.repository.StackRepository;
import io.codeka.gaia.repository.TerraformModuleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class StackController {

    private TerraformModuleRepository terraformModuleRepository;

    private StackRepository stackRepository;

    private Logger log = LoggerFactory.getLogger(StackController.class);

    @Autowired
    public StackController(TerraformModuleRepository terraformModuleRepository, StackRepository stackRepository) {
        this.terraformModuleRepository = terraformModuleRepository;
        this.stackRepository = stackRepository;
    }

    @GetMapping("/stacks")
    public String listStacks(Model model){
        model.addAttribute("stacks", stackRepository.findAll());

        return "stacks";
    }

    @GetMapping("/stacks/{stackId}")
    public String editStack(@PathVariable String stackId, Model model){
        // checking if the stack exists
        // TODO throw an exception (404) if not
        if(stackRepository.existsById(stackId)){
            model.addAttribute("stackId", stackId);
        }
        return "stack";
    }

}