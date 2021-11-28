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

    @OneToMany(mappedBy = "user")
    @OrderBy("createdAt DESC")
    private Set<JobEntity> jobs = new LinkedHashSet<>();

    public RecruiterEntity(){}

    public RecruiterEntity(String firstName, String lastName, String email, String phoneNumber,
                           PreferredContactMethod contactMethod, String password,
                           Set<JobEntity> jobs) {
        super(firstName, lastName, email, phoneNumber, contactMethod, password);
        this.jobs = jobs;
    }
}
