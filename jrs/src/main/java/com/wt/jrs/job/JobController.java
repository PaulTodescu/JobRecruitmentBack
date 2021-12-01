package com.wt.jrs.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "/job")
public class JobController {

    private final JobService jobService;
    private final FileService fileService;

    @Autowired
    public JobController(JobService jobService, FileService fileService) {

        this.jobService = jobService;
        this.fileService = fileService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
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

    @PutMapping(path = "/{jobId}/category/{categoryId}")
    public ResponseEntity<?> assignJobToCategory(
            @PathVariable("jobId") Long jobId,
            @PathVariable("categoryId") Long categoryId){
        this.jobService.assignJobToCategory(jobId, categoryId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path = "/{jobId}/image")
    public ResponseEntity<?> assignImageToJob(
            @PathVariable("jobId") Long jobId,
            @RequestParam(value = "image") Optional<MultipartFile> image) throws IOException {

        this.fileService.uploadImage(image, jobId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/{jobId}/image")
    @CrossOrigin
    public ResponseEntity<String> getJobImage(@PathVariable("jobId") Long jobId){
        String image = this.fileService.getJobImage(jobId);
        return new ResponseEntity<>(image, HttpStatus.OK);
    }


    @GetMapping (path = "/{jobId}/image/name")
    @ResponseBody
    public String getJobImageName(@PathVariable("jobId") Long jobId){
        return fileService.getImageName(jobId);
    }




}
