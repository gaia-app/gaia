package io.codeka.gaia.stacks.service;

import io.codeka.gaia.modules.bo.TerraformModule;
import io.codeka.gaia.modules.repository.TerraformModuleRepository;
import io.codeka.gaia.stacks.bo.Job;
import io.codeka.gaia.stacks.bo.JobStatus;
import io.codeka.gaia.stacks.bo.JobType;
import io.codeka.gaia.stacks.bo.Stack;
import io.codeka.gaia.stacks.repository.JobRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StackCostCalculatorTest {

    @Mock
    private JobRepository jobRepository;

    @Mock
    private TerraformModuleRepository moduleRepository;

    @InjectMocks
    private StackCostCalculator calculator;

    @Test
    void stacksWithNoJob_shouldHaveZeroCost() {
        // given
        var stack = new Stack();
        stack.setId("12");

        when(jobRepository.findAllByStackIdOrderByStartDateTimeDesc("12")).thenReturn(Collections.EMPTY_LIST);

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
        stack.setModuleId("42");

        var module = new TerraformModule();
        module.setEstimatedMonthlyCost(BigDecimal.valueOf(31));
        when(moduleRepository.findById("42")).thenReturn(Optional.of(module));

        // a job started two days ago
        var job = new Job();
        job.setType(JobType.RUN);
        job.start();
        job.end(JobStatus.APPLY_FINISHED);
        job.setStartDateTime(LocalDateTime.now().minusDays(2));
        when(jobRepository.findAllByStackIdOrderByStartDateTimeDesc("12")).thenReturn(List.of(job));

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
        stack.setModuleId("42");

        var module = new TerraformModule();
        module.setEstimatedMonthlyCost(BigDecimal.valueOf(31));
        when(moduleRepository.findById("42")).thenReturn(Optional.of(module));

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

        when(jobRepository.findAllByStackIdOrderByStartDateTimeDesc("12")).thenReturn(List.of(job, jobStop));

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
        stack.setModuleId("42");

        var module = new TerraformModule();
        module.setEstimatedMonthlyCost(BigDecimal.valueOf(31));
        when(moduleRepository.findById("42")).thenReturn(Optional.of(module));

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

        when(jobRepository.findAllByStackIdOrderByStartDateTimeDesc("12")).thenReturn(List.of(job, jobStop, jobRelaunch));

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
        stack.setModuleId("42");

        // a job started two days ago
        var job = new Job();
        job.setType(JobType.RUN);
        job.start();
        job.end(JobStatus.APPLY_FINISHED);
        job.setStartDateTime(LocalDateTime.now().minusDays(2));
        when(jobRepository.findAllByStackIdOrderByStartDateTimeDesc("12")).thenReturn(List.of(job));

        // but a module with no cost
        var module = new TerraformModule();
        when(moduleRepository.findById("42")).thenReturn(Optional.of(module));

        // when
        var cost = calculator.calculateRunningCostEstimation(stack);

        // then
        assertEquals(BigDecimal.ZERO, cost);
    }

}
