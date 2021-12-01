package com.wt.jrs.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.wt.jrs.application.ApplicationEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue(value = "EMPLOYEE")
public class EmployeeEntity extends UserEntity{

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    @OrderBy("createdAt DESC")
    @JsonBackReference
    private Set<ApplicationEntity> applications = new HashSet<>();

    public EmployeeEntity() {
    }

    public EmployeeEntity(String firstName, String lastName, String email, String phoneNumber,
                          PreferredContactMethod contactMethod, String password, UserRole role) {
        super(firstName, lastName, email, phoneNumber, contactMethod, password, role);
    }

    public Set<ApplicationEntity> getApplications() {
        return applications;
    }

    public void setApplications(Set<ApplicationEntity> applications) {
        this.applications = applications;
    }

    public void addApplication(ApplicationEntity application){
        this.applications.add(application);
    }
}
