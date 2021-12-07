package com.wt.jrs.application;

import com.wt.jrs.job.JobEntity;
import com.wt.jrs.job.JobService;
import com.wt.jrs.user.EmployeeEntity;
import com.wt.jrs.user.RecruiterEntity;
import com.wt.jrs.user.UserService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final UserService userService;
    private final JobService jobService;

    public EmailService(JavaMailSender javaMailSender, UserService userService, JobService jobService) {
        this.javaMailSender = javaMailSender;
        this.userService = userService;
        this.jobService = jobService;
    }

    public void sendApplicationEmail(Long employeeId, Long jobId, MultipartFile cv) throws MessagingException, IOException {

        EmployeeEntity employee = this.userService.findEmployeeById(employeeId);
        String phoneNumber = null;
        if (employee.getPhoneNumber() == null){
            phoneNumber = "Not provided";
        } else {
            phoneNumber = employee.getPhoneNumber();
        }
        JobEntity job = this.jobService.findJobById(jobId);

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        RecruiterEntity recruiter = job.getRecruiter();

        mimeMessageHelper.setFrom("hireneedrecruitment@gmail.com");
        mimeMessageHelper.setTo(recruiter.getEmail());
        mimeMessageHelper.setText(
                "Hello " + recruiter.getFirstName() + " " + recruiter.getLastName() + "," +
                "\n\nYou have received a new application for job: " + job.getTitle() +
                "\nCandidate information:" +
                "\n\tName: " + employee.getFirstName() + " " + employee.getLastName() +
                "\n\tEmail: " + employee.getEmail() +
                "\n\tPhone: " + phoneNumber +
                "\n\tPreferred Contact Method: " + employee.getContactMethod() +
                "\nPlease find the CV attached to this email." +
                "\n\nThank you!"
                );
        mimeMessageHelper.setSubject("HireNeed Job Application");


        mimeMessageHelper.addAttachment(cv.getOriginalFilename(), new ByteArrayResource(cv.getBytes()));

        javaMailSender.send(mimeMessage);


    }

}
