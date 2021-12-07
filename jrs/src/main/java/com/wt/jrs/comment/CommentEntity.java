package com.wt.jrs.comment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wt.jrs.job.JobEntity;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "comment")
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition="text")
    private String content;

    @Column(nullable = false)
    private String createdAt;

    @Column(nullable = false)
    private String author;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "job_id")
    private JobEntity job;

    public CommentEntity() {
    }

    public CommentEntity(String content, String createdAt, String author, JobEntity job) {
        this.content = content;
        this.createdAt = createdAt;
        this.author = author;
        this.job = job;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatedAt() {
        LocalDateTime dateTime = LocalDateTime.parse(createdAt);
        LocalDate currentDate = dateTime.toLocalDate();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return currentDate.format(dateFormatter);
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public JobEntity getJob() {
        return job;
    }

    public void setJob(JobEntity job) {
        this.job = job;
    }
}
