package com.wt.jrs.job;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wt.jrs.application.ApplicationEntity;
import com.wt.jrs.category.CategoryEntity;
import com.wt.jrs.comment.CommentEntity;
import com.wt.jrs.user.RecruiterEntity;
import com.wt.jrs.user.UserEntity;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

@Entity
@JsonIgnoreProperties({"applications", "comments"})
@Table(name = "job")
public class JobEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    @JsonIgnore
    @ManyToOne()
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

    @JsonBackReference(value = "reference1")
    @OrderBy("createdAt DESC")
    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL)
    private Set<ApplicationEntity> applications = new HashSet<>();

    @JsonBackReference(value = "reference2")
    @OrderBy("createdAt DESC")
    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL)
    private Set<CommentEntity> comments = new HashSet<>();

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

    public CategoryEntity getCategory() {
        return category;
    }

    public Set<ApplicationEntity> getApplications() {
        return applications;
    }

    public void setApplications(Set<ApplicationEntity> applications) {
        this.applications = applications;
    }

    public Set<CommentEntity> getComments() {
        return comments;
    }

    public void setComments(Set<CommentEntity> comments) {
        this.comments = comments;
    }
}
