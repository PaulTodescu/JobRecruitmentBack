package com.wt.jrs.application;

import com.wt.jrs.job.JobEntity;

public class ApplicationDTO {

    private Long id;
    private String createdAt;
    private JobEntity job;

    public ApplicationDTO() {
    }

    public ApplicationDTO(Long id, String createdAt, JobEntity job) {
        this.id = id;
        this.createdAt = createdAt;
        this.job = job;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
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
}
