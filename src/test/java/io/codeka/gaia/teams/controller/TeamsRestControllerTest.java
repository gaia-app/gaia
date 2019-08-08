package io.codeka.gaia.teams.controller;

import io.codeka.gaia.teams.repository.TeamRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
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