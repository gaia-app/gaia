package io.gaia_app.teams.controller;

import io.gaia_app.teams.Team;
import io.gaia_app.teams.repository.TeamRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TeamsRestControllerTest {

    @Mock
    private TeamRepository teamRepository;

    @InjectMocks
    private TeamsRestController teamsRestController;

    @Test
    void teams_shouldReturnAllTeams() {
        // given
        var a = new Team("A");
        when(teamRepository.findAll()).thenReturn(List.of(a));

        // when
        var teams = teamsRestController.teams();

        // then
        assertThat(teams).contains(a);
        verify(teamRepository).findAll();
    }
}
