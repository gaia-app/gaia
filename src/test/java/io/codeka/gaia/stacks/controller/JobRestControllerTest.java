package io.codeka.gaia.stacks.controller;

import io.codeka.gaia.stacks.bo.Job;
import io.codeka.gaia.stacks.repository.JobRepository;
import io.codeka.gaia.stacks.controller.JobNotFoundException;
import io.codeka.gaia.stacks.controller.JobRestController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JobRestControllerTest {

    @Mock
    private JobRepository jobRepository;

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
}