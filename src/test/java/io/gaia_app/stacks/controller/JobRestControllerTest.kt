package io.gaia_app.stacks.controller

import io.gaia_app.stacks.bo.Job
import io.gaia_app.stacks.bo.JobStatus
import io.gaia_app.stacks.bo.Step
import io.gaia_app.stacks.repository.JobRepository
import io.gaia_app.stacks.repository.StepRepository
import io.gaia_app.test.whenever
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import java.util.Optional.empty
import java.util.Optional.of

@ExtendWith(MockitoExtension::class)
class JobRestControllerTest {

    @Mock
    lateinit var jobRepository: JobRepository

    @Mock
    lateinit var stepRepository: StepRepository

    @InjectMocks
    lateinit var controller: JobRestController

    @Test
    fun `jobs() should return all jobs for a stack id`() {
        // when
        controller.jobs("stackId")

        // then
        verify(jobRepository).findAllByStackIdOrderByScheduleTimeDesc("stackId")
    }

    @Test
    fun `job() should return the job`() {
        // given
        val job = mock(Job::class.java)

        // when
        whenever(jobRepository.findById("12")).thenReturn(of(job))
        controller.job("12")

        // then
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
    fun `plan() should plan a job`() {
        // given
        val job = Job()

        // when
        whenever(jobRepository.findById(any())).thenReturn(of(job))
        controller.plan("test_jobId")

        // then
        assertThat(job.status).isEqualTo(JobStatus.PLAN_PENDING)
        verify(jobRepository).save(job)
    }

    @Test
    fun `apply() should throw an exception for non existing job`() {
        // when
        whenever(jobRepository.findById(any())).thenReturn(empty())

        // then
        assertThrows(JobNotFoundException::class.java) { controller.apply("test_jobId") }
    }

    @Test
    fun `apply() should apply a job`() {
        // given
        val job = Job()
        job.status = JobStatus.PLAN_FINISHED
        job.steps.add(Step())

        // when
        whenever(jobRepository.findById(any())).thenReturn(of(job))
        controller.apply("test_jobId")

        // then
        assertThat(job.status).isEqualTo(JobStatus.APPLY_PENDING)
        verify(jobRepository).save(job)
    }

    @Test
    fun `retry() should throw an exception for non existing job`() {
        // when
        whenever(jobRepository.findById(any())).thenReturn(empty())

        // then
        assertThrows(JobNotFoundException::class.java) { controller.retry("test_jobId") }
    }

    @Test
    fun `retry() should retry a job`() {
        // given
        val job = Job()
        job.status = JobStatus.PLAN_FAILED

        // when
        whenever(jobRepository.findById(any())).thenReturn(of(job))
        controller.retry("test_jobId")

        // then
        assertThat(job.status).isEqualTo(JobStatus.PLAN_PENDING)
        verify(jobRepository).save(job)
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
