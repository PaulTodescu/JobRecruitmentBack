package com.wt.jrs.job;

import com.wt.jrs.application.ApplicationDAO;
import com.wt.jrs.category.CategoryEntity;
import com.wt.jrs.category.CategoryService;
import com.wt.jrs.user.RecruiterEntity;
import com.wt.jrs.user.UserEntity;
import com.wt.jrs.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class JobService {

    private final JobDAO jobDAO;
    private final ApplicationDAO applicationDAO;
    private final UserService userService;
    private final CategoryService categoryService;
    private final FileService fileService;

    @Autowired
    public JobService(JobDAO jobDAO, ApplicationDAO applicationDAO, UserService userService, @Lazy CategoryService categoryService, FileService fileService) {
        this.jobDAO = jobDAO;
        this.applicationDAO = applicationDAO;
        this.userService = userService;
        this.categoryService = categoryService;
        this.fileService = fileService;
    }

    public Long addJob(JobEntity job) {
        return jobDAO.save(job).getId();
    }

    public JobEntity findJobById(Long id){
        return jobDAO.findJobById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Job with id: " + id + " was not found")
        );
    }

    public List<JobDTO> findAllJobs(){
        return mapJobsToDTOS(this.jobDAO.findAll());
    }

    public void assignJobToCategory(Long jobId, Long categoryId){
        JobEntity job = this.findJobById(jobId);
        CategoryEntity category = categoryService.findCategoryById(categoryId);
        job.setCategory(category);
        this.jobDAO.save(job);
    }

    public void assignCurrentUserToJob(Long jobId){
        JobEntity job = this.findJobById(jobId);
        String currentUserEmail = userService.findLoggedInUserEmail();
        UserEntity currentUser = userService.findUserByEmail(currentUserEmail);
        if (currentUser instanceof RecruiterEntity) {
            job.setRecruiter((RecruiterEntity) currentUser);
            this.jobDAO.save(job);
        } else throw new RuntimeException("Employees are not allowed to add jobs");
    }

    public JobDTO mapJobToDTO(JobEntity job){
        JobDTO jobDTO = new JobDTO();
        jobDTO.setId(job.getId());
        jobDTO.setTitle(job.getTitle());
        jobDTO.setCreatedAt(job.getCreatedAt());
        jobDTO.setCompanyName(job.getCompanyName());
        jobDTO.setLocation(job.getLocation());
        return jobDTO;
    }


    public JobUpdateDTO mapJobToUpdateDTO(JobEntity job){
        JobUpdateDTO jobUpdateDTO = new JobUpdateDTO();
        jobUpdateDTO.setId(job.getId());
        jobUpdateDTO.setTitle(job.getTitle());
        jobUpdateDTO.setDescription(job.getDescription());
        jobUpdateDTO.setSalary(job.getSalary());
        jobUpdateDTO.setSalaryCurrency(job.getSalaryCurrency());
        jobUpdateDTO.setSalaryType(job.getSalaryType());
        jobUpdateDTO.setCompanyName(job.getCompanyName());
        jobUpdateDTO.setLocation(job.getLocation());
        return jobUpdateDTO;
    }

    public List<JobDTO> mapJobsToDTOS(List<JobEntity> jobs){
        return jobs.stream().map(this::mapJobToDTO).collect(Collectors.toList());
    }

    public void updateJob(JobUpdateDTO updatedJob, Long jobId) {
        JobEntity job = this.findJobById(jobId);

        String updatedTitle = updatedJob.getTitle();
        String updatedDescription = updatedJob.getDescription();
        Integer updatedSalary = updatedJob.getSalary();
        SalaryCurrency updatedSalaryCurrency = updatedJob.getSalaryCurrency();
        SalaryType updatedSalaryType = updatedJob.getSalaryType();
        String updatedCompanyName = updatedJob.getCompanyName();
        String updatedLocation = updatedJob.getLocation();
        Long updatedCategoryId = updatedJob.getCategoryId();

        if (updatedTitle != null && !(updatedTitle.equals(job.getTitle()))){
            job.setTitle(updatedTitle);
        }

        if (updatedDescription != null && !(updatedDescription.equals(job.getDescription()))){
            job.setDescription(updatedDescription);
        }

        if (updatedSalary != null){
            if (!(updatedSalary.equals(job.getSalary()))){
                job.setSalary(updatedSalary);
            }
        } else {
            job.setSalary(null);
        }

        if (updatedSalaryCurrency != null){
            if (!(updatedSalaryCurrency.equals(job.getSalaryCurrency()))){
                job.setSalaryCurrency(updatedSalaryCurrency);
            }
        } else {
            job.setSalaryCurrency(null);
        }

        if (updatedSalaryType != null){
            if (!(updatedSalaryType.equals(job.getSalaryType()))){
                job.setSalaryType(updatedSalaryType);
            }
        } else {
            job.setSalaryType(null);
        }

        if (updatedCompanyName != null && !(updatedCompanyName.equals(job.getCompanyName()))){
            job.setCompanyName(updatedCompanyName);
        }

        if (updatedLocation != null && !(updatedLocation.equals(job.getLocation()))){
            job.setLocation(updatedLocation);
        }

        if(updatedCategoryId != null && !(updatedCategoryId.equals(job.getCategoryId()))){
            this.assignJobToCategory(job.getId(), updatedCategoryId);
        }

    }

    public void deleteJob(Long jobId) {
        if (this.jobDAO.existsById(jobId)) {
            this.jobDAO.deleteJobEntityById(jobId);
            this.fileService.deleteImage(jobId);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Job with id: " + jobId + " was not found!");
        }
    }
}
