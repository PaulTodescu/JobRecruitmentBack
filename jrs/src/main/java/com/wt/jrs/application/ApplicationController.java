package com.wt.jrs.application;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping(path = "/application")
public class ApplicationController {

    private final ApplicationService applicationService;
    private final EmailService emailService;

    public ApplicationController(ApplicationService applicationService, EmailService emailService) {
        this.applicationService = applicationService;
        this.emailService = emailService;
    }

    @PostMapping(path = "/employee/{employeeId}/job/{jobId}")
    public ResponseEntity<ApplicationEntity> addApplication(
            @PathVariable("employeeId") Long employeeId,
            @PathVariable("jobId") Long jobId,
            @RequestParam(value = "cv") MultipartFile cv) throws IOException, MessagingException {
        this.applicationService.addApplication(employeeId, jobId);
        this.emailService.sendApplicationEmail(employeeId, jobId, cv);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(path = "/{applicationId}")
    public ResponseEntity<ApplicationEntity> getApplicationById(@PathVariable("applicationId") Long applicationId){
        ApplicationEntity application = this.applicationService.findApplicationById(applicationId);
        return new ResponseEntity<>(application, HttpStatus.OK);
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<ApplicationEntity>> getAllApplications(){
        List<ApplicationEntity> applications = this.applicationService.findAllApplications();
        return new ResponseEntity<>(applications, HttpStatus.OK);
    }

}
