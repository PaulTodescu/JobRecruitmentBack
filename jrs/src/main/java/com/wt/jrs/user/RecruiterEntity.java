package com.wt.jrs.user;

import com.wt.jrs.job.JobEntity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@DiscriminatorValue(value = "RECRUITER")
public class RecruiterEntity extends UserEntity{

    @OneToMany(mappedBy = "recruiter")
    @OrderBy("createdAt DESC")
    private Set<JobEntity> jobs = new LinkedHashSet<>();

    public RecruiterEntity(){}

    public RecruiterEntity(String firstName, String lastName, String email, String phoneNumber,
                           PreferredContactMethod contactMethod, String password, UserRole role) {
        super(firstName, lastName, email, phoneNumber, contactMethod, password, role);
        this.jobs = null;
    }

    public Set<JobEntity> getJobs() {
        return jobs;
    }

    public void setJobs(Set<JobEntity> jobs) {
        this.jobs = jobs;
    }
}
