package com.wt.jrs.job;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "/job")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping
    public ResponseEntity<?> addJob(@RequestBody JobEntity job){
        Long jobId = this.jobService.addJob(job);
        this.jobService.assignCurrentUserToJob(jobId);
        return new ResponseEntity<>(jobId, HttpStatus.CREATED);
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<JobDTO>> getAllJobs(){
        List<JobDTO> jobs = this.jobService.findAllJobs();
        return new ResponseEntity<>(jobs, HttpStatus.OK);
    }

    @GetMapping(path = "/{jobId}")
    @ResponseBody
    public ResponseEntity<JobEntity> getJobById(@PathVariable("jobId") Long jobId){
        JobEntity job = jobService.findJobById(jobId);
        return new ResponseEntity<>(job, HttpStatus.OK);
    }

    @PutMapping("/{jobId}")
    public ResponseEntity<JobUpdateDTO> editJob(
            @RequestBody JobUpdateDTO job,
            @PathVariable("jobId") Long jobId){
        this.jobService.updateJob(job, jobId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{jobId}")
    public ResponseEntity<?> deleteJob(@PathVariable("jobId") Long jobId){
        this.jobService.deleteJob(jobId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(path = "{jobId}/category/{categoryId}")
    public ResponseEntity<?> assignJobToCategory(
            @PathVariable("jobId") Long jobId,
            @PathVariable("categoryId") Long categoryId){
        jobService.assignJobToCategory(jobId, categoryId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
