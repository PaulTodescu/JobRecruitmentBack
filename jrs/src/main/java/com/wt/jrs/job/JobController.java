package com.wt.jrs.job;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping(path = "/job")
public class JobController {

    private final JobService jobService;

    @Autowired
    ServletContext context;

    @Autowired
    FileService fileService;

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
        this.jobService.assignJobToCategory(jobId, categoryId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path = "{jobId}/image")
    public ResponseEntity<?> assignImageToJob(
            @PathVariable("jobId") Long jobId,
            @RequestParam(value = "image") MultipartFile image) throws IOException {

        this.fileService.uploadFile(image, jobId);

//        boolean folderIsCreated = new File(context.getRealPath("/images/")).exists();
//        if (!folderIsCreated){
//            boolean created = new File(context.getRealPath("/images/")).mkdir();
//        }
////        String fileName = image.getOriginalFilename();
////        String modifiedFileName = FilenameUtils.getBaseName(fileName) + "_" + System.currentTimeMillis() + "_" +
////                FilenameUtils.getExtension(fileName);
////        File serverFile = new File(context.getRealPath("/images/" + File.separator + modifiedFileName));
//        File serverFile = new File(context.getRealPath("/images/"));
//        try {
//            FileUtils.writeByteArrayToFile(serverFile, image.getBytes());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
        return new ResponseEntity<>(HttpStatus.OK);
        }
}
