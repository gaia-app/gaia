package io.codeka.gaia.stacks.controller;

import io.codeka.gaia.modules.bo.TerraformImage;
import io.codeka.gaia.modules.bo.TerraformModule;
import io.codeka.gaia.modules.repository.TerraformModuleRepository;
import io.codeka.gaia.runner.StackRunner;
import io.codeka.gaia.stacks.bo.Job;
import io.codeka.gaia.stacks.bo.JobType;
import io.codeka.gaia.stacks.bo.Stack;
import io.codeka.gaia.stacks.repository.JobRepository;
import io.codeka.gaia.stacks.repository.StackRepository;
import io.codeka.gaia.teams.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StackControllerTest {

    private StackController controller;

    @Mock
    private StackRepository stackRepository;

    @Mock
    private StackRunner stackRunner;

    @Mock
    private TerraformModuleRepository terraformModuleRepository;

    @Mock
    private JobRepository jobRepository;

    @Mock
    private Model model;

    private User user = new User("test_user", null);

    @BeforeEach
    void setup() {
        controller = new StackController(stackRepository, stackRunner, terraformModuleRepository, jobRepository);
    }

    @Test
    void newStack_shouldReturnToTheView() {
        var result = controller.newStack("test_stack", model);

        assertEquals("new_stack", result);
        verify(model).addAttribute("moduleId", "test_stack");
    }

    @Test
    void listStack_shouldReturnToTheView() {
        var result = controller.listStacks();

        assertEquals("stacks", result);
    }

    @Test
    void editStack_shouldReturnTheView() {
        var result = controller.editStack("test_stack", model);

        assertEquals("stack", result);
    }

    @Test
    void editStack_shouldSetModelIfStackExists() {
        when(stackRepository.existsById(anyString())).thenReturn(true);
        var result = controller.editStack("test_stack", model);

        assertEquals("stack", result);
        verify(model).addAttribute("stackId", "test_stack");
    }

    @Test
    void launchJob_shouldReturnTheView() {
        when(stackRepository.findById(anyString())).thenReturn(Optional.of(new Stack()));
        when(terraformModuleRepository.findById(any())).thenReturn(Optional.of(new TerraformModule()));
        var result = controller.launchJob("test_stack", JobType.RUN, model, user);

        assertEquals("job", result);
    }

    @Test
    void launchJob_shouldSetModelIfStackExists() {
        when(stackRepository.findById(anyString())).thenReturn(Optional.of(new Stack()));
        when(terraformModuleRepository.findById(any())).thenReturn(Optional.of(new TerraformModule()));
        var result = controller.launchJob("test_stack", JobType.RUN, model, user);

        assertEquals("job", result);
        verify(model).addAttribute("stackId", "test_stack");
    }

    @Test
    void launchJob_shouldConfigureAndSaveTheJob() {
        var stack = new Stack();
        var module = new TerraformModule();
        module.setTerraformImage(TerraformImage.Companion.defaultInstance());

        when(stackRepository.findById(anyString())).thenReturn(Optional.of(stack));
        when(terraformModuleRepository.findById(any())).thenReturn(Optional.of(module));
        var result = controller.launchJob("test_stack", JobType.RUN, model, user);

        assertEquals("job", result);

        var captor = ArgumentCaptor.forClass(Job.class);
        verify(jobRepository).save(captor.capture());
        var job = captor.getValue();
        assertNotNull(job);
        assertEquals(JobType.RUN, job.getType());
        assertEquals("test_stack", job.getStackId());
        assertEquals(user, job.getUser());
        assertEquals(module.getTerraformImage(), job.getTerraformImage());
    }

    @Test
    void viewJob_shouldReturnTheView() {
        var result = controller.viewJob("test_stack", "test_job", model);

        assertEquals("job", result);
    }

    @Test
    void viewJob_shouldSetModelIfStackExists() {
        when(stackRepository.existsById(anyString())).thenReturn(true);
        var result = controller.viewJob("test_stack", "test_job", model);

        assertEquals("job", result);
        verify(model).addAttribute("stackId", "test_stack");
    }

    @Test
    void viewJob_shouldSetModelIfJobExists() {
        when(jobRepository.existsById(anyString())).thenReturn(true);
        var result = controller.viewJob("test_stack", "test_job", model);

        assertEquals("job", result);
        verify(model).addAttribute("jobId", "test_job");
    }

    @Test
    void viewJob_shouldSetEditionMode() {
        var result = controller.viewJob("test_stack", "test_job", model);

        assertEquals("job", result);
        verify(model).addAttribute("edition", true);
    }

    @Test
    void getJob_shouldReturnTheJob() {
        var job = new Job();

        when(stackRunner.getJob(anyString())).thenReturn(job);
        var result = controller.getJob("test_stack", "test_job");

        assertEquals(job, result);
    }

}