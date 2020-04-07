package io.codeka.gaia.stacks.controller

import io.codeka.gaia.modules.repository.TerraformModuleRepository
import io.codeka.gaia.runner.StackRunner
import io.codeka.gaia.stacks.bo.Job
import io.codeka.gaia.stacks.repository.JobRepository
import io.codeka.gaia.stacks.repository.StackRepository
import io.codeka.gaia.stacks.repository.StepRepository
import io.codeka.gaia.stacks.workflow.JobWorkflow
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/jobs")
class JobRestController(
        private val jobRepository: JobRepository,
        private val stackRepository: StackRepository,
        private val moduleRepository: TerraformModuleRepository,
        private val stackRunner: StackRunner,
        private val stepRepository: StepRepository) {

    @GetMapping(params = ["stackId"])
    fun jobs(@RequestParam stackId: String) = jobRepository.findAllByStackIdOrderByStartDateTimeDesc(stackId)

    @GetMapping("/{id}")
    fun job(@PathVariable id: String): Job  {
        val runningJob = stackRunner.getJob(id);
        if (runningJob.isPresent) {
            return runningJob.get();
        }
        return jobRepository.findById(id).orElseThrow { JobNotFoundException() }
    }

    @PostMapping("/{id}/plan")
    fun plan(@PathVariable id: String) {
        val job = jobRepository.findById(id).orElseThrow { JobNotFoundException() }
        val stack = stackRepository.findById(job.stackId).orElseThrow()
        val module = moduleRepository.findById(stack.moduleId).orElseThrow()
        stackRunner.plan(JobWorkflow(job), module, stack)
    }

    @PostMapping("/{id}/apply")
    fun apply(@PathVariable id: String) {
        val job = jobRepository.findById(id).orElseThrow { JobNotFoundException() }
        val stack = stackRepository.findById(job.stackId).orElseThrow()
        val module = moduleRepository.findById(stack.moduleId).orElseThrow()
        stackRunner.apply(JobWorkflow(job), module, stack)
    }

    @PostMapping("/{id}/retry")
    fun retry(@PathVariable id: String) {
        val job = jobRepository.findById(id).orElseThrow { JobNotFoundException() }
        val stack = stackRepository.findById(job.stackId).orElseThrow()
        val module = moduleRepository.findById(stack.moduleId).orElseThrow()
        stackRunner.retry(JobWorkflow(job), module, stack)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: String) {
        stepRepository.deleteByJobId(id)
        jobRepository.deleteById(id)
    }

}

@ResponseStatus(HttpStatus.NOT_FOUND)
internal class JobNotFoundException : RuntimeException()
