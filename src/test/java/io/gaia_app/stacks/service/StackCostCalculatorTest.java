package io.gaia_app.stacks.service;

import io.gaia_app.modules.bo.TerraformModule;
import io.gaia_app.stacks.bo.Job;
import io.gaia_app.stacks.bo.JobStatus;
import io.gaia_app.stacks.bo.JobType;
import io.gaia_app.stacks.bo.Stack;
import io.gaia_app.stacks.repository.JobRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StackCostCalculatorTest {

    @Mock
    private JobRepository jobRepository;

    @InjectMocks
    private StackCostCalculator calculator;

    @Test
    void stacksWithNoJob_shouldHaveZeroCost() {
        // given
        var stack = new Stack();
        stack.setId("12");

        when(jobRepository.findAllByStackIdOrderByScheduleTimeDesc("12")).thenReturn(List.of());

        // when
        var cost = calculator.calculateRunningCostEstimation(stack);

        // then
        assertEquals(BigDecimal.ZERO, cost);
    }

    @Test
    void stacksWithOneRunJob_shouldHaveCostEqualToRunningTime() {
        // given
        var stack = new Stack();
        stack.setId("12");

        var module = new TerraformModule();
        module.setEstimatedMonthlyCost(BigDecimal.valueOf(31));
        stack.setModule(module);

        // a job started two days ago
        var job = new Job();
        job.setType(JobType.RUN);
        job.start();
        job.end(JobStatus.APPLY_FINISHED);
        job.setStartDateTime(LocalDateTime.now().minusDays(2));
        when(jobRepository.findAllByStackIdOrderByScheduleTimeDesc("12")).thenReturn(List.of(job));

        // when
        var cost = calculator.calculateRunningCostEstimation(stack);

        // then
        assertEquals(BigDecimal.valueOf(2).setScale(2), cost);
    }

    @Test
    void stacksWithOneRunJobAndOneStopJob_shouldHaveCostEqualToRunningTime() {
        // given
        var stack = new Stack();
        stack.setId("12");

        var module = new TerraformModule();
        module.setEstimatedMonthlyCost(BigDecimal.valueOf(31));
        stack.setModule(module);

        // a job started two days ago
        var job = new Job();
        job.setType(JobType.RUN);
        job.start();
        job.end(JobStatus.APPLY_FINISHED);
        job.setStartDateTime(LocalDateTime.now().minusDays(2));

        // a job stopped one day ago
        var jobStop = new Job();
        jobStop.setType(JobType.DESTROY);
        jobStop.start();
        jobStop.end(JobStatus.APPLY_FINISHED);
        jobStop.setStartDateTime(LocalDateTime.now().minusDays(1));

        when(jobRepository.findAllByStackIdOrderByScheduleTimeDesc("12")).thenReturn(List.of(job, jobStop));

        // when
        var cost = calculator.calculateRunningCostEstimation(stack);

        // then
        assertEquals(BigDecimal.valueOf(1).setScale(2), cost);
    }

    @Test
    void stacksWithOneRunJobAndOneStopJobAndRelaunchedOneHourAgo_shouldHaveCostEqualToRunningTime() {
        // given
        var stack = new Stack();
        stack.setId("12");

        var module = new TerraformModule();
        module.setEstimatedMonthlyCost(BigDecimal.valueOf(31));
        stack.setModule(module);

        // a job started two days ago
        var job = new Job();
        job.setType(JobType.RUN);
        job.start();
        job.end(JobStatus.APPLY_FINISHED);
        job.setStartDateTime(LocalDateTime.now().minusDays(2));

        // a job stopped one day ago
        var jobStop = new Job();
        jobStop.setType(JobType.DESTROY);
        jobStop.start();
        jobStop.end(JobStatus.APPLY_FINISHED);
        jobStop.setStartDateTime(LocalDateTime.now().minusDays(1));

        // a job started 6 hours ago
        var jobRelaunch = new Job();
        jobRelaunch.setType(JobType.RUN);
        jobRelaunch.start();
        jobRelaunch.end(JobStatus.APPLY_FINISHED);
        jobRelaunch.setStartDateTime(LocalDateTime.now().minusHours(1));

        when(jobRepository.findAllByStackIdOrderByScheduleTimeDesc("12")).thenReturn(List.of(job, jobStop, jobRelaunch));

        // when
        var cost = calculator.calculateRunningCostEstimation(stack);

        // then
        assertEquals(BigDecimal.valueOf(1.04).setScale(2), cost);
    }

    @Test
    void stacksWithModuleHavingNoCost_shouldHaveZeroCost() {
        // given
        var stack = new Stack();
        stack.setId("12");

        // a job started two days ago
        var job = new Job();
        job.setType(JobType.RUN);
        job.start();
        job.end(JobStatus.APPLY_FINISHED);
        job.setStartDateTime(LocalDateTime.now().minusDays(2));
        when(jobRepository.findAllByStackIdOrderByScheduleTimeDesc("12")).thenReturn(List.of(job));

        // but a module with no cost
        var module = new TerraformModule();
        stack.setModule(module);

        // when
        var cost = calculator.calculateRunningCostEstimation(stack);

        // then
        assertEquals(BigDecimal.ZERO, cost);
    }

}
