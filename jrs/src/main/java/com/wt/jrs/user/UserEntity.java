package com.wt.jrs.user;

import com.wt.jrs.job.JobEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_role", discriminatorType = DiscriminatorType.STRING)
@Table(name = "users")
public class UserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String email;

    private String phoneNumber;

    @Enumerated
    private PreferredContactMethod contactMethod;

    @Column(nullable = false)
    private String password;

//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
//    private UserRole role;


//    @OneToMany(mappedBy = "user")
//    @OrderBy("createdAt DESC")
//    private Set<JobEntity> jobs = new LinkedHashSet<>();

    public UserEntity() {
    }

    public UserEntity(String firstName, String lastName, String email, String phoneNumber, PreferredContactMethod contactMethod,
                      String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.contactMethod = contactMethod;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public PreferredContactMethod getContactMethod() {
        return contactMethod;
    }

    public void setContactMethod(PreferredContactMethod contactMethod) {
        this.contactMethod = contactMethod;
    }

}
