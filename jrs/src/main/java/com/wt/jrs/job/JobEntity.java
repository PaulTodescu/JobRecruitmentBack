package com.wt.jrs.job;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wt.jrs.category.CategoryEntity;
import com.wt.jrs.user.RecruiterEntity;
import com.wt.jrs.user.UserEntity;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "job")
public class JobEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private CategoryEntity category;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "recruiter_id")
    private RecruiterEntity recruiter;

    @Column(nullable = false, columnDefinition="text")
    private String description;

    private Integer salary;

    @Enumerated(EnumType.STRING)
    private SalaryCurrency salaryCurrency;

    @Enumerated(EnumType.STRING)
    private SalaryType salaryType;

    @Column(nullable = false)
    private String createdAt;

    @Column(nullable = false)
    private String companyName;

    @Column(nullable = false)
    private String location;

    public JobEntity() {
    }

    public JobEntity(
            String title, String description, Integer salary, SalaryCurrency salaryCurrency,
            SalaryType salaryType, String createdAt, String companyName, String location) {
        this.title = title;
        this.description = description;
        this.salary = salary;
        this.salaryCurrency = salaryCurrency;
        this.salaryType = salaryType;
        this.createdAt = createdAt;
        this.companyName = companyName;
        this.location = location;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getCategoryId() {
        return category.getId();
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public SalaryCurrency getSalaryCurrency() {
        return salaryCurrency;
    }

    public void setSalaryCurrency(SalaryCurrency salaryCurrency) {
        this.salaryCurrency = salaryCurrency;
    }

    public SalaryType getSalaryType() {
        return salaryType;
    }

    public void setSalaryType(SalaryType salaryType) {
        this.salaryType = salaryType;
    }

    public String getCreatedAt() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        LocalDateTime localDateTime = LocalDateTime.parse(this.createdAt, dateTimeFormatter);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return localDateTime.toLocalDate().format(dateFormatter);
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public RecruiterEntity getRecruiter() {
        return recruiter;
    }

    public void setRecruiter(RecruiterEntity recruiter) {
        this.recruiter = recruiter;
    }
}
