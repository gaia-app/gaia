package io.codeka.gaia.controller;

import io.codeka.gaia.bo.Job;
import io.codeka.gaia.bo.Stack;
import io.codeka.gaia.modules.bo.TerraformModule;
import io.codeka.gaia.repository.JobRepository;
import io.codeka.gaia.repository.StackRepository;
import io.codeka.gaia.modules.repository.TerraformModuleRepository;
import io.codeka.gaia.runner.StackRunner;
import io.codeka.gaia.teams.bo.User;
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
import static org.mockito.ArgumentMatchers.*;
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

    private User user = new User("test_user");

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
    void applyStack_shouldReturnTheView() {
        when(stackRepository.findById(anyString())).thenReturn(Optional.of(new Stack()));
        when(terraformModuleRepository.findById(any())).thenReturn(Optional.of(new TerraformModule()));
        var result = controller.applyStack("test_stack", model, user);

        assertEquals("job", result);
    }

    @Test
    void applyStack_shouldSetModelIfStackExists() {
        when(stackRepository.existsById(anyString())).thenReturn(true);
        when(stackRepository.findById(anyString())).thenReturn(Optional.of(new Stack()));
        when(terraformModuleRepository.findById(any())).thenReturn(Optional.of(new TerraformModule()));
        var result = controller.applyStack("test_stack", model, user);

        assertEquals("job", result);
        verify(model).addAttribute("stackId", "test_stack");
    }

    @Test
    void applyStack_shouldConfigureAndLaunchTheJob() {
        var stack = new Stack();
        var module = new TerraformModule();

        when(stackRepository.findById(anyString())).thenReturn(Optional.of(stack));
        when(terraformModuleRepository.findById(any())).thenReturn(Optional.of(module));
        var result = controller.applyStack("test_stack", model, user);

        assertEquals("job", result);

        var captor = ArgumentCaptor.forClass(Job.class);
        verify(stackRunner).apply(captor.capture(), eq(module), eq(stack));
        var job = captor.getValue();
        assertNotNull(job);
        assertEquals(user, job.getUser());
        assertNotNull(job.getId());
        assertEquals("test_stack", job.getStackId());
    }

    @Test
    void previewStack_shouldReturnTheView() {
        when(stackRepository.findById(anyString())).thenReturn(Optional.of(new Stack()));
        when(terraformModuleRepository.findById(any())).thenReturn(Optional.of(new TerraformModule()));
        var result = controller.previewStack("test_stack", model, user);

        assertEquals("job", result);
    }

    @Test
    void previewStack_shouldSetModelIfStackExists() {
        when(stackRepository.existsById(anyString())).thenReturn(true);
        when(stackRepository.findById(anyString())).thenReturn(Optional.of(new Stack()));
        when(terraformModuleRepository.findById(any())).thenReturn(Optional.of(new TerraformModule()));
        var result = controller.previewStack("test_stack", model, user);

        assertEquals("job", result);
        verify(model).addAttribute("stackId", "test_stack");
    }

    @Test
    void previewStack_shouldConfigureAndLaunchTheJob() {
        var stack = new Stack();
        var module = new TerraformModule();

        when(stackRepository.findById(anyString())).thenReturn(Optional.of(stack));
        when(terraformModuleRepository.findById(any())).thenReturn(Optional.of(module));
        var result = controller.previewStack("test_stack", model, user);

        assertEquals("job", result);

        var captor = ArgumentCaptor.forClass(Job.class);
        verify(stackRunner).plan(captor.capture(), eq(module), eq(stack));
        var job = captor.getValue();
        assertNotNull(job);
        assertEquals(user, job.getUser());
        assertNotNull(job.getId());
        assertEquals("test_stack", job.getStackId());
    }


    @Test
    void stopStack_shouldReturnTheView() {
        when(stackRepository.findById(anyString())).thenReturn(Optional.of(new Stack()));
        when(terraformModuleRepository.findById(any())).thenReturn(Optional.of(new TerraformModule()));
        var result = controller.stopStack("test_stack", model, user);

        assertEquals("job", result);
    }

    @Test
    void stopStack_shouldSetModelIfStackExists() {
        when(stackRepository.existsById(anyString())).thenReturn(true);
        when(stackRepository.findById(anyString())).thenReturn(Optional.of(new Stack()));
        when(terraformModuleRepository.findById(any())).thenReturn(Optional.of(new TerraformModule()));
        var result = controller.stopStack("test_stack", model, user);

        assertEquals("job", result);
        verify(model).addAttribute("stackId", "test_stack");
    }

    @Test
    void stopStack_shouldConfigureAndLaunchTheJob() {
        var stack = new Stack();
        var module = new TerraformModule();

        when(stackRepository.findById(anyString())).thenReturn(Optional.of(stack));
        when(terraformModuleRepository.findById(any())).thenReturn(Optional.of(module));
        var result = controller.stopStack("test_stack", model, user);

        assertEquals("job", result);

        var captor = ArgumentCaptor.forClass(Job.class);
        verify(stackRunner).stop(captor.capture(), eq(module), eq(stack));
        var job = captor.getValue();
        assertNotNull(job);
        assertEquals(user, job.getUser());
        assertNotNull(job.getId());
        assertEquals("test_stack", job.getStackId());
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
    void getJob_shouldReturnTheJob() {
        var job = new Job(new User());

        when(stackRunner.getJob(anyString())).thenReturn(job);
        var result = controller.getJob("test_stack", "test_job");

        assertEquals(job, result);
    }

}