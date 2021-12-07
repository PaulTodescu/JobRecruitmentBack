package com.wt.jrs.application;

import com.wt.jrs.job.JobEntity;
import com.wt.jrs.user.EmployeeEntity;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "application")
public class ApplicationEntity {

    @Id
    @Column(name = "application_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String createdAt;

    @ManyToOne
    @JoinColumn(name = "job_id")
    private JobEntity job;

    @ManyToOne()
    @JoinColumn(name = "employee_id")
    private EmployeeEntity employee;

    public ApplicationEntity() {
    }

    public ApplicationEntity(String createdAt, JobEntity job, EmployeeEntity employee) {
        this.createdAt = createdAt;
        this.job = job;
        this.employee = employee;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreatedAt() {
        LocalDateTime dateTime = LocalDateTime.parse(createdAt);
        LocalDate currentDate = dateTime.toLocalDate();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return currentDate.format(dateFormatter);
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public JobEntity getJob() {
        return job;
    }

    public void setJob(JobEntity job) {
        this.job = job;
    }

    public EmployeeEntity getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeEntity employee) {
        this.employee = employee;
    }
}
