package io.codeka.gaia.stacks.controller;

import io.codeka.gaia.stacks.bo.Job;
import io.codeka.gaia.stacks.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
public class JobRestController {

    private JobRepository jobRepository;

    @Autowired
    public JobRestController(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @GetMapping(params = "stackId")
    public List<Job> jobs(@RequestParam String stackId){
        return this.jobRepository.findAllByStackId(stackId);
    }

    @GetMapping("/{id}")
    public Job job(@PathVariable String id){
        return this.jobRepository.findById(id).orElseThrow(JobNotFoundException::new);
    }

}

@ResponseStatus(HttpStatus.NOT_FOUND)
class JobNotFoundException extends RuntimeException{
}
