package com.wt.jrs.user;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "EMPLOYEE")
public class EmployeeEntity extends UserEntity{
}
