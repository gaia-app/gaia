package io.codeka.gaia.stacks.controller

import io.codeka.gaia.modules.bo.TerraformModule
import io.codeka.gaia.modules.repository.TerraformModuleRepository
import io.codeka.gaia.runner.StackRunner
import io.codeka.gaia.stacks.bo.Job
import io.codeka.gaia.stacks.bo.Stack
import io.codeka.gaia.stacks.repository.JobRepository
import io.codeka.gaia.stacks.repository.StackRepository
import io.codeka.gaia.stacks.repository.StepRepository
import io.codeka.gaia.stacks.workflow.JobWorkflow
import io.codeka.gaia.test.whenever
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentCaptor.forClass
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*
import java.util.Optional.empty
import java.util.Optional.of

@ExtendWith(MockitoExtension::class)
class JobRestControllerTest {

    @Mock
    lateinit var jobRepository: JobRepository

    @Mock
    lateinit var stepRepository: StepRepository

    @Mock
    lateinit var stackRepository: StackRepository

    @Mock
    lateinit var moduleRepository: TerraformModuleRepository

    @Mock
    lateinit var stackRunner: StackRunner

    @InjectMocks
    lateinit var controller: JobRestController

    @Test
    fun `jobs() should return all jobs for a stack id`() {
        // when
        controller.jobs("stackId")

        // then
        verify(jobRepository).findAllByStackIdOrderByStartDateTimeDesc("stackId")
    }

    @Test
    fun `job() should return the running job if exists`() {
        // given
        val job = mock(Job::class.java)

        // when
        whenever(stackRunner.getJob(any())).thenReturn(of(job))
        controller.job("12")

        // then
        verify(stackRunner).getJob("12")
        verifyNoInteractions(jobRepository)
    }

    @Test
    fun `job() should return the saved job if no running`() {
        // given
        val job = mock(Job::class.java)

        // when
        whenever(stackRunner.getJob(any())).thenReturn(empty())
        whenever(jobRepository.findById("12")).thenReturn(of(job))
        controller.job("12")

        // then
        verify(stackRunner, times(1)).getJob("12")
        verify(jobRepository).findById("12")
    }

    @Test
    fun `job() should throw an exception for non existing jobs`() {
        // when
        whenever(jobRepository.findById("12")).thenReturn(empty())

        // then
        assertThrows(JobNotFoundException::class.java) { controller.job("12") }
        verify(jobRepository).findById("12")
    }

    @Test
    fun `plan() should throw an exception for non existing job`() {
        // when
        whenever(jobRepository.findById(any())).thenReturn(empty())

        // then
        assertThrows(JobNotFoundException::class.java) { controller.plan("test_jobId") }
    }

    @Test
    fun `plan() should throw an exception for non existing stack`() {
        // when
        whenever(jobRepository.findById(any())).thenReturn(of(Job()))
        whenever(stackRepository.findById(any())).thenReturn(empty())

        // then
        assertThrows(NoSuchElementException::class.java) { controller.plan("test_jobId") }
    }

    @Test
    fun `plan() should throw an exception for non existing module`() {
        // when
        whenever(jobRepository.findById(any())).thenReturn(of(Job()))
        whenever(stackRepository.findById(any())).thenReturn(of(Stack()))
        whenever(moduleRepository.findById(any())).thenReturn(empty())

        // then
        assertThrows(NoSuchElementException::class.java) { controller.plan("test_jobId") }
    }

    @Test
    fun `plan() should plan a job`() {
        // given
        val job = Job()
        val stack = Stack()
        val module = TerraformModule()

        // when
        whenever(jobRepository.findById(any())).thenReturn(of(job))
        whenever(stackRepository.findById(any())).thenReturn(of(stack))
        whenever(moduleRepository.findById(any())).thenReturn(of(module))
        controller.plan("test_jobId")

        // then
        val captor = forClass(JobWorkflow::class.java)
        verify(stackRunner).plan(captor.capture(), eq(module), eq(stack))
        assertThat(captor.value).isNotNull
        assertThat(captor.value.job).isNotNull.isEqualTo(job)
    }

    @Test
    fun `apply() should throw an exception for non existing job`() {
        // when
        whenever(jobRepository.findById(any())).thenReturn(empty())

        // then
        assertThrows(JobNotFoundException::class.java) { controller.apply("test_jobId") }
    }

    @Test
    fun `apply() should throw an exception for non existing stack`() {
        // when
        whenever(jobRepository.findById(any())).thenReturn(of(Job()))
        whenever(stackRepository.findById(any())).thenReturn(empty())

        // then
        assertThrows(NoSuchElementException::class.java) { controller.apply("test_jobId") }
    }

    @Test
    fun `apply() should throw an exception for non existing module`() {
        // when
        whenever(jobRepository.findById(any())).thenReturn(of(Job()))
        whenever(stackRepository.findById(any())).thenReturn(of(Stack()))
        whenever(moduleRepository.findById(any())).thenReturn(empty())

        // then
        assertThrows(NoSuchElementException::class.java) { controller.apply("test_jobId") }
    }

    @Test
    fun `apply() should apply a job`() {
        // given
        val job = Job()
        val stack = Stack()
        val module = TerraformModule()

        // when
        whenever(jobRepository.findById(any())).thenReturn(of(job))
        whenever(stackRepository.findById(any())).thenReturn(of(stack))
        whenever(moduleRepository.findById(any())).thenReturn(of(module))
        controller.apply("test_jobId")

        // then
        val captor = forClass(JobWorkflow::class.java)
        verify(stackRunner).apply(captor.capture(), eq(module), eq(stack))
        assertThat(captor.value).isNotNull
        assertThat(captor.value.job).isNotNull.isEqualTo(job)
    }

    @Test
    fun `retry() should throw an exception for non existing job`() {
        // when
        whenever(jobRepository.findById(any())).thenReturn(empty())

        // then
        assertThrows(JobNotFoundException::class.java) { controller.retry("test_jobId") }
    }

    @Test
    fun `retry() should throw an exception for non existing stack`() {
        // when
        whenever(jobRepository.findById(any())).thenReturn(of(Job()))
        whenever(stackRepository.findById(any())).thenReturn(empty())

        // then
        assertThrows(NoSuchElementException::class.java) { controller.retry("test_jobId") }
    }

    @Test
    fun `retry() should throw an exception for non existing module`() {
        // when
        whenever(jobRepository.findById(any())).thenReturn(of(Job()))
        whenever(stackRepository.findById(any())).thenReturn(of(Stack()))
        whenever(moduleRepository.findById(any())).thenReturn(empty())

        // then
        assertThrows(NoSuchElementException::class.java) { controller.retry("test_jobId") }
    }

    @Test
    fun `retry() should retry a job`() {
        // given
        val job = Job()
        val stack = Stack()
        val module = TerraformModule()

        // when
        whenever(jobRepository.findById(any())).thenReturn(of(job))
        whenever(stackRepository.findById(any())).thenReturn(of(stack))
        whenever(moduleRepository.findById(any())).thenReturn(of(module))
        controller.retry("test_jobId")

        // then
        val captor = forClass(JobWorkflow::class.java)
        verify(stackRunner).retry(captor.capture(), eq(module), eq(stack))
        assertThat(captor.value).isNotNull
        assertThat(captor.value.job).isNotNull.isEqualTo(job)
    }

    @Test
    fun `delete() should delete a job and its steps`() {
        // when
        controller.delete("test_jobId")

        // then
        verify(stepRepository, times(1)).deleteByJobId("test_jobId")
        verify(jobRepository, times(1)).deleteById("test_jobId")
        verifyNoMoreInteractions(stepRepository, jobRepository)
    }

}
