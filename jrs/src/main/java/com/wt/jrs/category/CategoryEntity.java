package com.wt.jrs.category;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wt.jrs.job.JobEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

@Entity
@Table(name = "category")
public class CategoryEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    @OrderBy("createdAt DESC")
    @JoinColumn(name = "category_id")
    private Set<JobEntity> jobs = new LinkedHashSet<>();

    public CategoryEntity() {
    }

    public CategoryEntity(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<JobEntity> getJobs() {
        return jobs;
    }

//    public void addJob(JobEntity job){
//        this.jobs.add(job);
//    }

    public void setJobs(Set<JobEntity> jobs) {
        this.jobs = jobs;
    }
}
