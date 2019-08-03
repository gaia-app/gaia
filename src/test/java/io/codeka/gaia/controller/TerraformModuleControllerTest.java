package io.codeka.gaia.controller;

import io.codeka.gaia.bo.TerraformModule;
import io.codeka.gaia.repository.TerraformModuleGitRepository;
import io.codeka.gaia.repository.TerraformModuleRepository;
import io.codeka.gaia.teams.bo.Team;
import io.codeka.gaia.teams.bo.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TerraformModuleControllerTest {

    private TerraformModuleController controller;

    @Mock
    private TerraformModuleRepository moduleRepository;

    @Mock
    private TerraformModuleGitRepository moduleGitRepository;

    private User admin = new User("admin");

    private User standardUser = new User("Odile Deray");

    private Team userTeam = new Team("userTeam");

    @BeforeEach
    void setup() {
        controller = new TerraformModuleController(moduleRepository, moduleGitRepository);
        standardUser.setTeam(userTeam);
    }

    @Test
    void description_shouldReturnRightView() {
        // given
        var module = new TerraformModule();
        var model = mock(Model.class);

        // when
        when(moduleRepository.findById(anyString())).thenReturn(Optional.of(module));
        var result = controller.description("TEST", model);

        // then
        assertThat(result).isEqualTo("module_description");
        verify(moduleRepository).findById("TEST");
        verify(model).addAttribute("module", module);
    }

    @Test
    void description_shouldThrowExceptionIfModuleNotFound() {
        // given
        var model = mock(Model.class);

        // when
        when(moduleRepository.findById(anyString())).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> controller.description("TEST", model));

        // then
        verify(moduleRepository).findById("TEST");
        verify(model, never()).addAttribute(eq("module"), any());
    }

    @Test
    void readme_shouldReturnReadmeContent() {
        // given
        var module = new TerraformModule();
        var readme = "README...";

        // when
        when(moduleRepository.findById(anyString())).thenReturn(Optional.of(module));
        when(moduleGitRepository.getReadme(module)).thenReturn(Optional.of(readme));
        var result = controller.readme("TEST");

        // then
        assertThat(result).isPresent().get().isEqualTo(readme);
        verify(moduleRepository).findById("TEST");
        verify(moduleGitRepository).getReadme(module);
    }

    @Test
    void readme_shouldThrowExceptionIfModuleNotFound() {
        // given

        // when
        when(moduleRepository.findById(anyString())).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> controller.readme("TEST"));

        // then
        verify(moduleRepository).findById("TEST");
        verifyZeroInteractions(moduleGitRepository);
    }

    @Test
    void modulesList_shouldShowAllModules_forAdminUser(){
        // given
        var model = mock(Model.class);

        // when
        controller.modulesList(model, admin);

        // then
        verify(moduleRepository).findAll();
        verify(model).addAttribute(eq("modules"), any());
    }

    @Test
    void modulesList_shouldShowUserTeamModules_forStandardUser(){
        // given
        var model = mock(Model.class);

        // when
        controller.modulesList(model, standardUser);

        // then
        verify(moduleRepository).findAllByAuthorizedTeamsContaining(userTeam);
        verify(model).addAttribute(eq("modules"), any());
    }

    @Test
    void module_shouldShowModule_forAuthorizedUser(){
        // given
        var model = mock(Model.class);
        var module = mock(TerraformModule.class);
        when(module.isAuthorizedFor(standardUser)).thenReturn(true);
        when(moduleRepository.findById("12")).thenReturn(Optional.of(module));

        // when
        controller.module("12", model, standardUser);

        // then
        verify(moduleRepository).findById("12");
        verify(model).addAttribute("module", module);
    }

    @Test
    void module_shouldThrowException_forUnauthorizedUser(){
        // given
        var model = mock(Model.class);
        var module = mock(TerraformModule.class);
        when(module.isAuthorizedFor(standardUser)).thenReturn(false);
        when(moduleRepository.findById("12")).thenReturn(Optional.of(module));

        // when
        assertThrows(ModuleForbiddenException.class, () -> controller.module("12", model, standardUser));

        // then
        verify(moduleRepository).findById("12");
        verifyZeroInteractions(model);
    }

    @Test
    void module_shouldThrowException_forUnexistingModule(){
        // given
        var model = mock(Model.class);
        when(moduleRepository.findById("12")).thenReturn(Optional.empty());

        // when
        assertThrows(ModuleNotFoundException.class, () -> controller.module("12", model, standardUser));

        // then
        verify(moduleRepository).findById("12");
        verifyZeroInteractions(model);
    }


    @Test
    void saveModule_shouldSaveModule(){
        // given
        var module = mock(TerraformModule.class);
        var model = mock(Model.class);

        // when
        controller.saveModule(module, model, admin);

        // then
        verify(moduleRepository).save(module);
    }

}