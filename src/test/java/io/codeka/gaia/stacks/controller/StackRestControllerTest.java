package io.codeka.gaia.stacks.controller;

import io.codeka.gaia.modules.bo.TerraformImage;
import io.codeka.gaia.modules.bo.TerraformModule;
import io.codeka.gaia.modules.repository.TerraformModuleRepository;
import io.codeka.gaia.stacks.bo.Job;
import io.codeka.gaia.stacks.bo.JobType;
import io.codeka.gaia.stacks.bo.Stack;
import io.codeka.gaia.stacks.repository.JobRepository;
import io.codeka.gaia.stacks.repository.StackRepository;
import io.codeka.gaia.stacks.service.StackCostCalculator;
import io.codeka.gaia.teams.Team;
import io.codeka.gaia.teams.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StackRestControllerTest {

    private User adminUser = new User("admin", null);

    private Team userTeam = new Team("Red Is Dead");

    private User standardUser = new User("Serge Karamazov", userTeam);

    private User userWithNoTeam = new User("Émile Gravier", null);

    private Stack stack = new Stack();

    @InjectMocks
    private StackRestController stackRestController;

    @Mock
    private StackRepository stackRepository;

    @Mock
    private StackCostCalculator stackCostCalculator;

    @Mock
    private TerraformModuleRepository terraformModuleRepository;

    @Mock
    private JobRepository jobRepository;

    @Test
    void listStack_shouldFindAllStacks_forAdminUser() {
        // when
        stackRestController.listStacks(adminUser);

        // then
        verify(stackRepository).findAll();
    }

    @Test
    void listStack_shouldFindTeamStacks_forStandardUser() {
        // when
        stackRestController.listStacks(standardUser);

        // then
        verify(stackRepository).findByOwnerTeam(userTeam);
    }

    @Test
    void listStack_shouldFindOwnedStacks_forUserWithNoTeam() {
        // when
        stackRestController.listStacks(userWithNoTeam);

        // then
        verify(stackRepository).findByCreatedBy(userWithNoTeam);
    }

    @Test
    void getStack_shouldFindStack_forAdminUser() {
        // given
        when(stackRepository.findById("42")).thenReturn(Optional.of(stack));

        // when
        stackRestController.getStack("42", adminUser);

        // then
        verify(stackRepository).findById("42");
    }

    @Test
    void getStack_shouldFindStack_forStandardUser() {
        // given
        when(stackRepository.findByIdAndOwnerTeam("42", userTeam)).thenReturn(Optional.of(stack));

        // when
        stackRestController.getStack("42", standardUser);

        // then
        verify(stackRepository).findByIdAndOwnerTeam("42", userTeam);
    }

    @Test
    void getStack_shouldFindStack_forUserWithNoTeam() {
        // given
        when(stackRepository.findById("42")).thenReturn(Optional.of(stack));

        // when
        stackRestController.getStack("42", userWithNoTeam);

        // then
        verify(stackRepository).findById("42");
    }

    @Test
    void getStack_shouldCalculateRunningCost_forStandardUser() {
        // given
        when(stackRepository.findByIdAndOwnerTeam("42", userTeam)).thenReturn(Optional.of(stack));

        // when
        stackRestController.getStack("42", standardUser);

        // then
        verify(stackRepository).findByIdAndOwnerTeam("42", userTeam);
        verify(stackCostCalculator).calculateRunningCostEstimation(stack);
    }

    @Test
    void getStack_shouldThrowStackNotFoundException() {
        // given
        when(stackRepository.findById("12")).thenReturn(Optional.empty());
        when(stackRepository.findByIdAndOwnerTeam("42", userTeam)).thenReturn(Optional.empty());

        // when/then
        assertThrows(StackNotFoundException.class, () -> stackRestController.getStack("12", adminUser));
        assertThrows(StackNotFoundException.class, () -> stackRestController.getStack("42", standardUser));
    }

    @Test
    void save_shouldSaveStack() {
        // when
        stackRestController.save(stack, userTeam, standardUser);

        // then
        assertThat(stack.getId()).isNotBlank();
        assertThat(stack.getOwnerTeam()).isEqualTo(userTeam);
        assertThat(stack.getCreatedBy()).isEqualTo(standardUser);
        assertThat(stack.getCreatedAt()).isEqualToIgnoringSeconds(LocalDateTime.now());
        verify(stackRepository).save(stack);
    }

    @Test
    void update_shouldSaveStack() {
        // when
        stackRestController.update("12", stack, standardUser);

        // then
        assertThat(stack.getUpdatedBy()).isEqualTo(standardUser);
        assertThat(stack.getUpdatedAt()).isEqualToIgnoringSeconds(LocalDateTime.now());
        verify(stackRepository).save(stack);
    }

    @Test
    void launchJob_shouldConfigureAndSaveTheJob() {
        // given
        var stack = new Stack();
        var module = new TerraformModule();
        module.setTerraformImage(TerraformImage.Companion.defaultInstance());
        var user = new User("test_user", null);

        // when
        when(stackRepository.findById(anyString())).thenReturn(Optional.of(stack));
        when(terraformModuleRepository.findById(any())).thenReturn(Optional.of(module));
        var result = stackRestController.launchJob("test_stack", JobType.RUN, user);

        // then
        assertThat(result)
            .isNotNull()
            .containsKeys("jobId");

        var captor = forClass(Job.class);
        verify(jobRepository).save(captor.capture());
        var job = captor.getValue();
        assertNotNull(job);
        assertEquals(JobType.RUN, job.getType());
        assertEquals("test_stack", job.getStackId());
        assertEquals(user, job.getUser());
        assertEquals(module.getTerraformImage(), job.getTerraformImage());
    }

}
