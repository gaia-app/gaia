package io.codeka.gaia.stacks.controller;

import io.codeka.gaia.modules.bo.TerraformModule;
import io.codeka.gaia.modules.repository.TerraformModuleRepository;
import io.codeka.gaia.runner.StackRunner;
import io.codeka.gaia.stacks.bo.Job;
import io.codeka.gaia.stacks.bo.Stack;
import io.codeka.gaia.stacks.bo.StepType;
import io.codeka.gaia.stacks.repository.JobRepository;
import io.codeka.gaia.stacks.repository.StackRepository;
import io.codeka.gaia.stacks.repository.StepRepository;
import io.codeka.gaia.stacks.workflow.JobWorkflow;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JobRestControllerTest {

    @Mock
    private JobRepository jobRepository;

    @Mock
    private StepRepository stepRepository;

    @Mock
    private StackRepository stackRepository;

    @Mock
    private TerraformModuleRepository moduleRepository;

    @Mock
    private StackRunner stackRunner;

    @InjectMocks
    private JobRestController controller;

    @Test
    void jobs_shouldReturnAllJobs_forStackId() {
        // when
        controller.jobs("stackId");

        // then
        verify(jobRepository).findAllByStackId("stackId");
    }

    @Test
    void job_shouldReturnASpecificJob() {
        // given
        var job = mock(Job.class);
        when(jobRepository.findById("12")).thenReturn(Optional.of(job));

        // when
        controller.job("12");

        // then
        verify(jobRepository).findById("12");
    }

    @Test
    void job_shouldThrowJobNotFoundException_forNonExistingJobs() {
        // given
        when(jobRepository.findById("12")).thenReturn(Optional.empty());

        // when
        assertThrows(JobNotFoundException.class, () -> controller.job("12"));

        // then
        verify(jobRepository).findById("12");
    }

    @Test
    void planOrApplyJob_shouldThrowJobNotFoundException_forNonExistingJob() {
        // when
        when(jobRepository.findById(any())).thenReturn(Optional.empty());

        // then
        assertThrows(JobNotFoundException.class, () -> controller.planOrApplyJob("test_jobId", StepType.PLAN));
    }

    @Test
    void planOrApplyJob_shouldThrowException_forNonExistingStack() {
        // when
        when(jobRepository.findById(any())).thenReturn(Optional.of(new Job()));
        when(stackRepository.findById(any())).thenReturn(Optional.empty());

        // then
        assertThrows(NoSuchElementException.class, () -> controller.planOrApplyJob("test_jobId", StepType.PLAN));
    }

    @Test
    void planOrApplyJob_shouldThrowException_forNonExistingModule() {
        // when
        when(jobRepository.findById(any())).thenReturn(Optional.of(new Job()));
        when(stackRepository.findById(any())).thenReturn(Optional.of(new Stack()));
        when(moduleRepository.findById(any())).thenReturn(Optional.empty());

        // then
        assertThrows(NoSuchElementException.class, () -> controller.planOrApplyJob("test_jobId", StepType.PLAN));
    }

    @Test
    void planOrApplyJob_shouldPlan_whenStepTypeIsPlan() {
        // given
        var job = new Job();
        var stack = new Stack();
        var module = new TerraformModule();

        // when
        when(jobRepository.findById(any())).thenReturn(Optional.of(job));
        when(stackRepository.findById(any())).thenReturn(Optional.of(stack));
        when(moduleRepository.findById(any())).thenReturn(Optional.of(module));
        controller.planOrApplyJob("test_jobId", StepType.PLAN);

        // then
        var captor = ArgumentCaptor.forClass(JobWorkflow.class);
        verify(stackRunner).plan(captor.capture(), eq(module), eq(stack));
        assertThat(captor.getValue()).isNotNull();
        assertThat(captor.getValue().getJob()).isNotNull().isEqualTo(job);
    }

    @Test
    void planOrApplyJob_shouldApply_whenStepTypeIsApply() {
        // given
        var job = new Job();
        var stack = new Stack();
        var module = new TerraformModule();

        // when
        when(jobRepository.findById(any())).thenReturn(Optional.of(job));
        when(stackRepository.findById(any())).thenReturn(Optional.of(stack));
        when(moduleRepository.findById(any())).thenReturn(Optional.of(module));
        controller.planOrApplyJob("test_jobId", StepType.APPLY);

        // then
        var captor = ArgumentCaptor.forClass(JobWorkflow.class);
        verify(stackRunner).apply(captor.capture(), eq(module), eq(stack));
        assertThat(captor.getValue()).isNotNull();
        assertThat(captor.getValue().getJob()).isNotNull().isEqualTo(job);
    }

    @Test
    void retryJob_shouldThrowJobNotFoundException_forNonExistingJob() {
        // when
        when(jobRepository.findById(any())).thenReturn(Optional.empty());

        // then
        assertThrows(JobNotFoundException.class, () -> controller.retryJob("test_jobId"));
    }

    @Test
    void retryJob_shouldThrowException_forNonExistingStack() {
        // when
        when(jobRepository.findById(any())).thenReturn(Optional.of(new Job()));
        when(stackRepository.findById(any())).thenReturn(Optional.empty());

        // then
        assertThrows(NoSuchElementException.class, () -> controller.retryJob("test_jobId"));
    }

    @Test
    void retryJob_shouldThrowException_forNonExistingModule() {
        // when
        when(jobRepository.findById(any())).thenReturn(Optional.of(new Job()));
        when(stackRepository.findById(any())).thenReturn(Optional.of(new Stack()));
        when(moduleRepository.findById(any())).thenReturn(Optional.empty());

        // then
        assertThrows(NoSuchElementException.class, () -> controller.retryJob("test_jobId"));
    }

    @Test
    void retryJob_shouldRetry() {
        // given
        var job = new Job();
        var stack = new Stack();
        var module = new TerraformModule();

        // when
        when(jobRepository.findById(any())).thenReturn(Optional.of(job));
        when(stackRepository.findById(any())).thenReturn(Optional.of(stack));
        when(moduleRepository.findById(any())).thenReturn(Optional.of(module));
        controller.retryJob("test_jobId");

        // then
        var captor = ArgumentCaptor.forClass(JobWorkflow.class);
        verify(stackRunner).retry(captor.capture(), eq(module), eq(stack));
        assertThat(captor.getValue()).isNotNull();
        assertThat(captor.getValue().getJob()).isNotNull().isEqualTo(job);
    }

    @Test
    void deleteJob_shouldDeleteJobAndSteps() {
        // when
        controller.deleteJob("test_jobId");

        // then
        verify(stepRepository, times(1)).deleteByJobId("test_jobId");
        verify(jobRepository, times(1)).deleteById("test_jobId");
        verifyNoMoreInteractions(stepRepository, jobRepository);
    }

}