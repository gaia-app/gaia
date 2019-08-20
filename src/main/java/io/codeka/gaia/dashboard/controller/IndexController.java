package io.codeka.gaia.dashboard.controller;

import io.codeka.gaia.modules.repository.TerraformModuleRepository;
import io.codeka.gaia.stacks.bo.StackState;
import io.codeka.gaia.stacks.repository.StackRepository;
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
        long moduleCount = 0;
        long runningStackCount = 0;
        long toUpdateStackCount = 0;
        if(user.isAdmin()){
            moduleCount = this.moduleRepository.count();
            runningStackCount = this.stackRepository.countStacksByState(StackState.RUNNING);
            toUpdateStackCount = this.stackRepository.countStacksByState(StackState.TO_UPDATE);
        }
        else if(userTeam != null){
            moduleCount = this.moduleRepository.countByAuthorizedTeamsContaining(userTeam);
            runningStackCount = stackRepository.countStacksByStateAndOwnerTeam(StackState.RUNNING, userTeam);
            toUpdateStackCount = stackRepository.countStacksByStateAndOwnerTeam(StackState.TO_UPDATE, userTeam);
        }

        model.addAttribute("moduleCount", moduleCount);
        model.addAttribute("runningStackCount", runningStackCount);
        model.addAttribute("toUpdateStackCount", toUpdateStackCount);

        return "index";
    }

}
