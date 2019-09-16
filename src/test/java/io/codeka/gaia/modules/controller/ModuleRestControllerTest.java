package io.codeka.gaia.modules.controller;

import io.codeka.gaia.modules.bo.TerraformModule;
import io.codeka.gaia.modules.repository.TerraformModuleRepository;
import io.codeka.gaia.teams.Team;
import io.codeka.gaia.teams.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ModuleRestControllerTest {

    @InjectMocks
    private ModuleRestController moduleRestController;

    @Mock
    private TerraformModuleRepository moduleRepository;

    private User admin;

    private User bob;

    private Team bobsTeam;

    @BeforeEach
    void setUp() {
        admin = new User("admin", null);

        bobsTeam = new Team("bobsTeam");
        bob = new User("Bob Kelso", bobsTeam);
    }

    @Test
    void findAll_shouldReturnAllModules_forAdmin(){
        // when
        moduleRestController.findAllModules(admin);

        // then
        verify(moduleRepository).findAll();
    }

    @Test
    void findAll_shouldReturnAuthorizedModules_forUserTeam(){
        // given
        when(moduleRepository.findAllByAuthorizedTeamsContaining(bobsTeam)).thenReturn(List.of(new TerraformModule()));

        // when
        var modules = moduleRestController.findAllModules(bob);

        // then
        assertThat(modules).hasSize(1);
        verify(moduleRepository).findAllByAuthorizedTeamsContaining(bobsTeam);
    }

    @Test
    void findById_shouldReturnModule_forAdmin(){
        // given
        when(moduleRepository.findById("12")).thenReturn(Optional.of(mock(TerraformModule.class)));

        // when
        moduleRestController.findModule("12", admin);

        // then
        verify(moduleRepository).findById("12");
    }

    @Test
    void findById_shouldReturnAuthorizedModule_forUserTeam(){
        // given
        when(moduleRepository.findByIdAndAuthorizedTeamsContaining("12", bobsTeam)).thenReturn(Optional.of(mock(TerraformModule.class)));

        // when
        moduleRestController.findModule("12", bob);

        // then
        verify(moduleRepository).findByIdAndAuthorizedTeamsContaining("12", bobsTeam);
    }

    @Test
    void findById_shouldThrowModuleNotFound_forNonExistingModules(){
        // given
        when(moduleRepository.findByIdAndAuthorizedTeamsContaining("12", bobsTeam)).thenReturn(Optional.empty());
        when(moduleRepository.findById("12")).thenReturn(Optional.empty());

        // when
        assertThrows(ModuleNotFoundException.class, () -> moduleRestController.findModule("12", admin));
        assertThrows(ModuleNotFoundException.class, () -> moduleRestController.findModule("12", bob));
    }

    @Test
    void save_shouldSaveTheModule(){
        // given
        var module = mock(TerraformModule.class);

        // when
        moduleRestController.saveModule("12", module);

        // then
        verify(moduleRepository).save(module);
    }

}