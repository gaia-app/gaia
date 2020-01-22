package io.codeka.gaia.dashboard.controller;

import io.codeka.gaia.modules.repository.TerraformModuleRepository;
import io.codeka.gaia.stacks.bo.StackState;
import io.codeka.gaia.stacks.repository.StackRepository;
import io.codeka.gaia.teams.Team;
import io.codeka.gaia.teams.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IndexControllerTest {

    @InjectMocks
    private IndexController indexController;

    @Mock
    private TerraformModuleRepository terraformModuleRepository;

    @Mock
    private StackRepository stackRepository;

    @Mock
    private Model model;

    private Team team = new Team("userTeam");

    private User user = new User("user", team);

    private User admin = new User("admin", null);

    @Test
    void index_shouldShowModuleCount(){
        // given
        when(terraformModuleRepository.countByAuthorizedTeamsContainingOrModuleMetadataCreatedBy(team, user)).thenReturn(12);

        // when
        var result = indexController.index(model, user, team);

        // then
        assertEquals("index", result);
        verify(terraformModuleRepository).countByAuthorizedTeamsContainingOrModuleMetadataCreatedBy(team, user);
        verify(model).addAttribute("moduleCount", 12L);
    }

    @Test
    void index_shouldShowModuleCount_forAdmin(){
        // given
        when(terraformModuleRepository.count()).thenReturn(12L);

        // when
        var result = indexController.index(model, admin, team);

        // then
        assertEquals("index", result);
        verify(terraformModuleRepository).count();
        verify(model).addAttribute("moduleCount", 12L);
    }

    @Test
    void index_shouldShowStacksCount(){
        // given
        doReturn(1).when(stackRepository).countStacksByStateAndOwnerTeam(StackState.RUNNING, team);
        doReturn(2).when(stackRepository).countStacksByStateAndOwnerTeam(StackState.TO_UPDATE, team);

        // when
        var result = indexController.index(model, user, team);

        // then
        assertEquals("index", result);
        verify(stackRepository).countStacksByStateAndOwnerTeam(StackState.RUNNING, team);
        verify(stackRepository).countStacksByStateAndOwnerTeam(StackState.TO_UPDATE, team);
        verify(model).addAttribute("moduleCount", 0L);
        verify(model).addAttribute("runningStackCount", 1L);
        verify(model).addAttribute("toUpdateStackCount", 2L);
    }

    @Test
    void index_shouldShowStacksCount_forAdmin(){
        // given
        doReturn(1).when(stackRepository).countStacksByState(StackState.RUNNING);
        doReturn(2).when(stackRepository).countStacksByState(StackState.TO_UPDATE);

        // when
        var result = indexController.index(model, admin, team);

        // then
        assertEquals("index", result);
        verify(stackRepository).countStacksByState(StackState.RUNNING);
        verify(stackRepository).countStacksByState(StackState.TO_UPDATE);
        verify(model).addAttribute("moduleCount", 0L);
        verify(model).addAttribute("runningStackCount", 1L);
        verify(model).addAttribute("toUpdateStackCount", 2L);
    }

    @Test
    void usersWIthNoTeam_shouldSeeTheirCreatedModuleOrStacks(){
        // given
        doReturn(3).when(terraformModuleRepository).countByModuleMetadataCreatedBy(user);
        doReturn(1).when(stackRepository).countStacksByStateAndCreatedBy(StackState.RUNNING, user);
        doReturn(2).when(stackRepository).countStacksByStateAndCreatedBy(StackState.TO_UPDATE, user);

        // when
        var result = indexController.index(model, user, null);

        // then
        assertEquals("index", result);
        verify(model).addAttribute("moduleCount", 3L);
        verify(model).addAttribute("runningStackCount", 1L);
        verify(model).addAttribute("toUpdateStackCount", 2L);
    }

}