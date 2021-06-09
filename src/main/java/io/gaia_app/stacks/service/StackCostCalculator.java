package io.gaia_app.stacks.service;

import io.gaia_app.stacks.bo.JobStatus;
import io.gaia_app.stacks.bo.JobType;
import io.gaia_app.stacks.bo.Stack;
import io.gaia_app.stacks.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 * This service calculates the cost of the running stack
 */
@Service
public class StackCostCalculator {

    private JobRepository jobRepository;

    @Autowired
    public StackCostCalculator(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    /**
     * Calculates an estimation of what this stack has cost in total
     * @param stack
     * @return
     */
    public BigDecimal calculateRunningCostEstimation(Stack stack){
        var jobs = jobRepository.findAllByStackIdOrderByScheduleTimeDesc(stack.getId());

        if(jobs.isEmpty()){
            return BigDecimal.ZERO;
        }

        var module = stack.getModule();

        if(module.getEstimatedMonthlyCost() == null){
            return BigDecimal.ZERO;
        }

        // calculate total running time
        var duration = Duration.ofDays(0);

        var start = LocalDateTime.now();
        var end = LocalDateTime.now();

        for(var job : jobs ){
            // add
            if(job.getType() == JobType.RUN && job.getStatus() == JobStatus.APPLY_FINISHED){
                start =job.getStartDateTime();
                end = LocalDateTime.now();
            }
            if(job.getType() == JobType.DESTROY && job.getStatus() == JobStatus.APPLY_FINISHED){
                end = job.getStartDateTime();
                duration = duration.plus(Duration.between(start, end));

                // reset start and end
                start = LocalDateTime.now();
                end = LocalDateTime.now();
            }
        }

        // add last duration
        duration = duration.plus(Duration.between(start, end));

        // get hourly cost
        var hourlyCost = module.getEstimatedMonthlyCost().divide(BigDecimal.valueOf(31L*24L), 4, RoundingMode.HALF_UP);

        // get total cost
        return hourlyCost.multiply(BigDecimal.valueOf(duration.toHours()))
                // round it to 2 decimals
                .setScale(2, RoundingMode.HALF_UP);
    }

}
