package io.gaia_app.teams.controller;

import io.gaia_app.teams.Team;
import io.gaia_app.teams.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teams")
public class TeamsRestController {

    private TeamRepository teamRepository;

    @Autowired
    public TeamsRestController(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @GetMapping
    public List<Team> teams(){
        return this.teamRepository.findAll();
    }

    @PostMapping
    @Secured("ROLE_ADMIN")
    public Team createOrganization(@RequestBody Team organization){
        return this.teamRepository.save(organization);
    }

    @DeleteMapping("/{organizationId}")
    @Secured("ROLE_ADMIN")
    public void deleteOrganization(@PathVariable String organizationId){
        this.teamRepository.deleteById(organizationId);
    }

}
