package com.wt.jrs.user;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "EMPLOYEE")
public class EmployeeEntity extends UserEntity{

    public EmployeeEntity() {
    }

    public EmployeeEntity(String firstName, String lastName, String email, String phoneNumber,
                          PreferredContactMethod contactMethod, String password, UserRole role) {
        super(firstName, lastName, email, phoneNumber, contactMethod, password, role);
    }
}
