package io.codeka.gaia.controller;

import io.codeka.gaia.bo.StackState;
import io.codeka.gaia.repository.StackRepository;
import io.codeka.gaia.repository.TerraformModuleRepository;
import io.codeka.gaia.teams.bo.Team;
import io.codeka.gaia.teams.bo.User;
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
    public String index(Model model, User user, Team userTeam){
        if(user.isAdmin()){
            model.addAttribute("moduleCount", this.moduleRepository.count());
            model.addAttribute("runningStackCount", this.stackRepository.countStacksByState(StackState.RUNNING));
            model.addAttribute("toUpdateStackCount", this.stackRepository.countStacksByState(StackState.TO_UPDATE));
        }
        else{
            model.addAttribute("moduleCount", this.moduleRepository.countByAuthorizedTeamsContaining(userTeam));
            model.addAttribute("runningStackCount", stackRepository.countStacksByStateAndOwnerTeam(StackState.RUNNING, userTeam));
            model.addAttribute("toUpdateStackCount", stackRepository.countStacksByStateAndOwnerTeam(StackState.TO_UPDATE, userTeam));
        }

        return "index";
    }

}
