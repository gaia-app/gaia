package io.codeka.gaia.teams.controller;

import io.codeka.gaia.teams.Team;
import io.codeka.gaia.teams.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
