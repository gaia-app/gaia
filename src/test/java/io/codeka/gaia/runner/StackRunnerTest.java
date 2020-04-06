package io.codeka.gaia.runner;

import io.codeka.gaia.modules.bo.TerraformModule;
import io.codeka.gaia.stacks.bo.Job;
import io.codeka.gaia.stacks.bo.JobType;
import io.codeka.gaia.stacks.bo.Stack;
import io.codeka.gaia.stacks.bo.StackState;
import io.codeka.gaia.stacks.repository.JobRepository;
import io.codeka.gaia.stacks.repository.StackRepository;
import io.codeka.gaia.stacks.repository.StepRepository;
import io.codeka.gaia.stacks.workflow.JobWorkflow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StackRunnerTest {

    @Mock
    private DockerRunner dockerRunner;

    @Mock
    private StackCommandBuilder stackCommandBuilder;

    @Mock
    private StackRepository stackRepository;

    @Mock
    private JobRepository jobRepository;

    @Mock
    private StepRepository stepRepository;

    @Mock
    private JobWorkflow jobWorkflow;

    private Job job;

    private TerraformModule module;

    private Stack stack;

    @InjectMocks
    private StackRunner stackRunner;

    @BeforeEach
    void setUp() {
        stack = new Stack();
        job = new Job(JobType.RUN, null, null);
        lenient().when(jobWorkflow.getJob()).thenReturn(job); // use lenient to prevent mockito from throwing exception for tests not needing this mock (getLog_*)
        module = new TerraformModule();
    }

    @Test
    void plan_shouldExecutePlanWorkflow() {
        // when
        when(dockerRunner.runContainerForJob(any(), any())).thenReturn(0);
        stackRunner.plan(jobWorkflow, module, stack);

        // then
        verify(jobWorkflow).plan();
    }

    @Test
    void plan_shouldUsePlanScript_WhenJobIsRun() {
        // when
        when(dockerRunner.runContainerForJob(any(), any())).thenReturn(0);
        stackRunner.plan(jobWorkflow, module, stack);

        // then
        verify(stackCommandBuilder).buildPlanScript(jobWorkflow.getJob(), stack, module);
    }

    @Test
    void plan_shouldUsePlanDestroyScript_WhenJobIsStop() {
        // given
        job.setType(JobType.DESTROY);

        // when
        when(dockerRunner.runContainerForJob(any(), any())).thenReturn(0);
        stackRunner.plan(jobWorkflow, module, stack);

        // then
        verify(stackCommandBuilder).buildPlanDestroyScript(jobWorkflow.getJob(), stack, module);
    }

    @Test
    void plan_shouldEndJob_WhenSuccessful() {
        // when
        when(dockerRunner.runContainerForJob(any(), any())).thenReturn(0);
        stackRunner.plan(jobWorkflow, module, stack);

        // then
        verify(jobWorkflow).end();
    }

    @Test
    void plan_shouldEndJob_WhenThereIsADiff() {
        // when
        when(dockerRunner.runContainerForJob(any(), any())).thenReturn(2);
        stackRunner.plan(jobWorkflow, module, stack);

        // then
        verify(jobWorkflow).end();
    }

    @Test
    void plan_shouldFailJob_WhenThereIsAError() {
        // when
        when(dockerRunner.runContainerForJob(any(), any())).thenReturn(99);
        stackRunner.plan(jobWorkflow, module, stack);

        // then
        verify(jobWorkflow).fail();
    }

    @Test
    void plan_shouldUpdateStack_WhenThereIsADiff() {
        // given
        stack.setState(StackState.RUNNING);

        // when
        when(dockerRunner.runContainerForJob(any(), any())).thenReturn(2);
        stackRunner.plan(jobWorkflow, module, stack);

        // then
        assertEquals(StackState.TO_UPDATE, stack.getState());
        verify(stackRepository).save(stack);
    }

    @Test
    void plan_shouldNotUpdateStack_WhenThereIsADiffForNewStacks() {
        // given
        stack.setState(StackState.NEW);

        // when
        when(dockerRunner.runContainerForJob(any(), any())).thenReturn(2);
        stackRunner.plan(jobWorkflow, module, stack);

        // then
        assertEquals(StackState.NEW, stack.getState());
        verifyNoInteractions(stackRepository);
    }

    @Test
    void plan_shouldNotUpdateStack_WhenThereIsADiffAndJobIsStop() {
        // given
        stack.setState(StackState.RUNNING);
        job.setType(JobType.DESTROY);

        // when
        when(dockerRunner.runContainerForJob(any(), any())).thenReturn(2);
        stackRunner.plan(jobWorkflow, module, stack);

        // then
        assertEquals(StackState.RUNNING, stack.getState());
        verifyNoInteractions(stackRepository);
    }

    @Test
    void plan_shouldSaveJobAndSteps() {
        // when
        stackRunner.plan(jobWorkflow, module, stack);

        // then
        verify(jobRepository, times(2)).save(job);
        verify(stepRepository, times(2)).saveAll(job.getSteps());
    }

    @Test
    void apply_shouldExecuteApplyWorkflow() {
        // when
        when(dockerRunner.runContainerForJob(any(), any())).thenReturn(0);
        stackRunner.apply(jobWorkflow, module, stack);

        // then
        verify(jobWorkflow).apply();
    }

    @Test
    void apply_shouldUseApplyScript_WhenJobIsRun() {
        // when
        when(dockerRunner.runContainerForJob(any(), any())).thenReturn(0);
        stackRunner.apply(jobWorkflow, module, stack);

        // then
        verify(stackCommandBuilder).buildApplyScript(jobWorkflow.getJob(), stack, module);
    }

    @Test
    void apply_shouldUseDestroyScript_WhenJobIsStop() {
        // given
        job.setType(JobType.DESTROY);

        // when
        when(dockerRunner.runContainerForJob(any(), any())).thenReturn(0);
        stackRunner.apply(jobWorkflow, module, stack);

        // then
        verify(stackCommandBuilder).buildDestroyScript(jobWorkflow.getJob(), stack, module);
    }

    @Test
    void apply_shouldEndJob_WhenSuccessful() {
        // when
        when(dockerRunner.runContainerForJob(any(), any())).thenReturn(0);
        stackRunner.apply(jobWorkflow, module, stack);

        // then
        verify(jobWorkflow).end();
    }

    @Test
    void apply_shouldFailJob_WhenThereIsAError() {
        // when
        when(dockerRunner.runContainerForJob(any(), any())).thenReturn(99);
        stackRunner.apply(jobWorkflow, module, stack);

        // then
        verify(jobWorkflow).fail();
    }

    @Test
    void apply_shouldUpdateStack_WhenSuccessfulAndJobIsRun() {
        // given
        stack.setState(StackState.NEW);

        // when
        when(dockerRunner.runContainerForJob(any(), any())).thenReturn(0);
        stackRunner.apply(jobWorkflow, module, stack);

        // then
        assertEquals(StackState.RUNNING, stack.getState());
        verify(stackRepository).save(stack);
    }

    @Test
    void apply_shouldUpdateStack_WhenSuccessfulAndJobIsStop() {
        // given
        stack.setState(StackState.RUNNING);
        job.setType(JobType.DESTROY);

        // when
        when(dockerRunner.runContainerForJob(any(), any())).thenReturn(0);
        stackRunner.apply(jobWorkflow, module, stack);

        // then
        assertEquals(StackState.STOPPED, stack.getState());
        verify(stackRepository).save(stack);
    }

    @Test
    void apply_shouldSaveJobAndSteps() {
        // when
        stackRunner.apply(jobWorkflow, module, stack);

        // then
        verify(jobRepository, times(2)).save(job);
        verify(stepRepository, times(2)).saveAll(job.getSteps());
    }

    @Test
    void retry_shouldDeletePreviousSteps() {
        // when
        when(dockerRunner.runContainerForJob(any(), any())).thenReturn(0);
        stackRunner.retry(jobWorkflow, module, stack);

        // then
        verify(stepRepository).deleteByJobId(jobWorkflow.getJob().getId());
    }

    @Test
    void retry_shouldExecuteRetryWorkflow() {
        // when
        when(dockerRunner.runContainerForJob(any(), any())).thenReturn(0);
        stackRunner.retry(jobWorkflow, module, stack);

        // then
        verify(jobWorkflow).retry();
    }

    @Test
    void retry_shouldUsePlanScript_WhenJobIsRun() {
        // when
        when(dockerRunner.runContainerForJob(any(), any())).thenReturn(0);
        stackRunner.retry(jobWorkflow, module, stack);

        // then
        verify(stackCommandBuilder).buildPlanScript(jobWorkflow.getJob(), stack, module);
    }

    @Test
    void retry_shouldUsePlanDestroyScript_WhenJobIsStop() {
        // given
        job.setType(JobType.DESTROY);

        // when
        when(dockerRunner.runContainerForJob(any(), any())).thenReturn(0);
        stackRunner.retry(jobWorkflow, module, stack);

        // then
        verify(stackCommandBuilder).buildPlanDestroyScript(jobWorkflow.getJob(), stack, module);
    }

    @Test
    void retry_shouldEndJob_WhenSuccessful() {
        // when
        when(dockerRunner.runContainerForJob(any(), any())).thenReturn(0);
        stackRunner.retry(jobWorkflow, module, stack);

        // then
        verify(jobWorkflow).end();
    }

    @Test
    void retry_shouldEndJob_WhenThereIsADiff() {
        // when
        when(dockerRunner.runContainerForJob(any(), any())).thenReturn(2);
        stackRunner.retry(jobWorkflow, module, stack);

        // then
        verify(jobWorkflow).end();
    }

    @Test
    void retry_shouldFailJob_WhenThereIsAError() {
        // when
        when(dockerRunner.runContainerForJob(any(), any())).thenReturn(99);
        stackRunner.retry(jobWorkflow, module, stack);

        // then
        verify(jobWorkflow).fail();
    }

    @Test
    void retry_shouldUpdateStack_WhenThereIsADiff() {
        // given
        stack.setState(StackState.RUNNING);

        // when
        when(dockerRunner.runContainerForJob(any(), any())).thenReturn(2);
        stackRunner.retry(jobWorkflow, module, stack);

        // then
        assertEquals(StackState.TO_UPDATE, stack.getState());
        verify(stackRepository).save(stack);
    }

    @Test
    void retry_shouldNotUpdateStack_WhenThereIsADiffForNewStacks() {
        // given
        stack.setState(StackState.NEW);

        // when
        when(dockerRunner.runContainerForJob(any(), any())).thenReturn(2);
        stackRunner.retry(jobWorkflow, module, stack);

        // then
        assertEquals(StackState.NEW, stack.getState());
        verifyNoInteractions(stackRepository);
    }

    @Test
    void retry_shouldNotUpdateStack_WhenThereIsADiffAndJobIsStop() {
        // given
        stack.setState(StackState.RUNNING);
        job.setType(JobType.DESTROY);

        // when
        when(dockerRunner.runContainerForJob(any(), any())).thenReturn(2);
        stackRunner.retry(jobWorkflow, module, stack);

        // then
        assertEquals(StackState.RUNNING, stack.getState());
        verifyNoInteractions(stackRepository);
    }

    @Test
    void retry_shouldSaveJobAndSteps() {
        // when
        stackRunner.retry(jobWorkflow, module, stack);

        // then
        verify(jobRepository, times(2)).save(job);
        verify(stepRepository, times(2)).saveAll(job.getSteps());
    }

}
