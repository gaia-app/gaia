package io.codeka.gaia.teams.controller;

import io.codeka.gaia.teams.repository.TeamRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TeamsRestControllerTest {

    @Mock
    private TeamRepository teamRepository;

    @InjectMocks
    private TeamsRestController teamsRestController;

    @Test
    void teams_shouldReturnAllTeams() {
        teamsRestController.teams();

        verify(teamRepository).findAll();
    }
}