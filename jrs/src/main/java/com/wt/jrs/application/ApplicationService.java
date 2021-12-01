package com.wt.jrs.application;

import com.wt.jrs.job.JobDTO;
import com.wt.jrs.job.JobEntity;
import com.wt.jrs.job.JobService;
import com.wt.jrs.user.EmployeeEntity;
import com.wt.jrs.user.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ApplicationService {

    private final ApplicationDAO applicationDAO;
    private final JobService jobService;
    private final UserService userService;

    public ApplicationService(ApplicationDAO applicationDAO, JobService jobService, UserService userService) {
        this.applicationDAO = applicationDAO;
        this.jobService = jobService;
        this.userService = userService;
    }

    public void addApplication(Long employeeId, Long jobId) {
        EmployeeEntity employee = this.userService.findEmployeeById(employeeId);
        JobEntity job = this.jobService.findJobById(jobId);

//        LocalDate currentDate = LocalDate.now();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//        String creationTime = currentDate.format(formatter);

        LocalDateTime currentDate = LocalDateTime.now();

        ApplicationEntity application = new ApplicationEntity(currentDate.toString(), job, employee);
        this.applicationDAO.save(application);
    }

    public ApplicationEntity findApplicationById(Long applicationId){
        return applicationDAO.findApplicationEntityById(applicationId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Application with id: " + applicationId +" was not found")
        );
    }

    public ApplicationDTO mapApplicationToDTO(ApplicationEntity application){
        ApplicationDTO applicationDTO = new ApplicationDTO();
        applicationDTO.setId(application.getId());
        applicationDTO.setCreatedAt(application.getCreatedAt());
        applicationDTO.setJob(application.getJob());
        return applicationDTO;
    }

    public List<ApplicationDTO> mapApplicationsToDTOs(List<ApplicationEntity> applications){
        return applications.stream().map(this::mapApplicationToDTO).collect(Collectors.toList());
    }

    public List<ApplicationEntity> findAllApplications(){
        return applicationDAO.findAll();
    }
}
