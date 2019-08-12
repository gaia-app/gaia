package io.codeka.gaia.service;

import io.codeka.gaia.bo.Job;
import io.codeka.gaia.bo.JobType;
import io.codeka.gaia.bo.Stack;
import io.codeka.gaia.modules.bo.TerraformModule;
import io.codeka.gaia.repository.JobRepository;
import io.codeka.gaia.modules.repository.TerraformModuleRepository;
import io.codeka.gaia.teams.bo.User;
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
    void stacksWithNoJob_shouldHaveZeroCost(){
        // given
        var stack = new Stack();
        stack.setId("12");

        when(jobRepository.findAllByStackId("12")).thenReturn(Collections.EMPTY_LIST);

        // when
        var cost = calculator.calculateRunningCostEstimation(stack);

        // then
        assertEquals(BigDecimal.ZERO, cost);
    }

    @Test
    void stacksWithOneRunJob_shouldHaveCostEqualToRunningTime(){
        // given
        var stack = new Stack();
        stack.setId("12");
        stack.setModuleId("42");

        var module = new TerraformModule();
        module.setEstimatedMonthlyCost(BigDecimal.valueOf(31));
        when(moduleRepository.findById("42")).thenReturn(Optional.of(module));

        // a job started two days ago
        var job = new Job(new User());
        job.start(JobType.RUN);
        job.end();
        job.setStartDateTime(LocalDateTime.now().minusDays(2));
        when(jobRepository.findAllByStackId("12")).thenReturn(List.of(job));

        // when
        var cost = calculator.calculateRunningCostEstimation(stack);

        // then
        assertEquals(BigDecimal.valueOf(2).setScale(2), cost);
    }

    @Test
    void stacksWithOneRunJobAndOneStopJob_shouldHaveCostEqualToRunningTime(){
        // given
        var stack = new Stack();
        stack.setId("12");
        stack.setModuleId("42");

        var module = new TerraformModule();
        module.setEstimatedMonthlyCost(BigDecimal.valueOf(31));
        when(moduleRepository.findById("42")).thenReturn(Optional.of(module));

        // a job started two days ago
        var job = new Job(new User());
        job.start(JobType.RUN);
        job.end();
        job.setStartDateTime(LocalDateTime.now().minusDays(2));

        // a job stopped one day ago
        var jobStop = new Job(new User());
        jobStop.start(JobType.STOP);
        jobStop.end();
        jobStop.setStartDateTime(LocalDateTime.now().minusDays(1));

        when(jobRepository.findAllByStackId("12")).thenReturn(List.of(job, jobStop));

        // when
        var cost = calculator.calculateRunningCostEstimation(stack);

        // then
        assertEquals(BigDecimal.valueOf(1).setScale(2), cost);
    }

    @Test
    void stacksWithOneRunJobAndOneStopJobAndRelaunchedOneHourAgo_shouldHaveCostEqualToRunningTime(){
        // given
        var stack = new Stack();
        stack.setId("12");
        stack.setModuleId("42");

        var module = new TerraformModule();
        module.setEstimatedMonthlyCost(BigDecimal.valueOf(31));
        when(moduleRepository.findById("42")).thenReturn(Optional.of(module));

        // a job started two days ago
        var job = new Job(new User());
        job.start(JobType.RUN);
        job.end();
        job.setStartDateTime(LocalDateTime.now().minusDays(2));

        // a job stopped one day ago
        var jobStop = new Job(new User());
        jobStop.start(JobType.STOP);
        jobStop.end();
        jobStop.setStartDateTime(LocalDateTime.now().minusDays(1));

        // a job started 6 hours ago
        var jobRelaunch = new Job(new User());
        jobRelaunch.start(JobType.RUN);
        jobRelaunch.end();
        jobRelaunch.setStartDateTime(LocalDateTime.now().minusHours(1));

        when(jobRepository.findAllByStackId("12")).thenReturn(List.of(job, jobStop, jobRelaunch));

        // when
        var cost = calculator.calculateRunningCostEstimation(stack);

        // then
        assertEquals(BigDecimal.valueOf(1.04).setScale(2), cost);
    }

    @Test
    void stacksWithModuleHavingNoCost_shouldHaveZeroCost(){
        // given
        var stack = new Stack();
        stack.setId("12");
        stack.setModuleId("42");

        // a job started two days ago
        var job = new Job(new User());
        job.start(JobType.RUN);
        job.end();
        job.setStartDateTime(LocalDateTime.now().minusDays(2));
        when(jobRepository.findAllByStackId("12")).thenReturn(List.of(job));

        // but a module with no cost
        var module = new TerraformModule();
        when(moduleRepository.findById("42")).thenReturn(Optional.of(module));

        // when
        var cost = calculator.calculateRunningCostEstimation(stack);

        // then
        assertEquals(BigDecimal.ZERO, cost);
    }

}