package com.wt.jrs.job;

public class JobDTO {

    private Long id;
    private String title;
    private String createdAt;
    private String companyName;
    private String location;

    public JobDTO() {
    }

    public JobDTO(Long id, String title, String createdAt, String companyName, String location) {
        this.id = id;
        this.title = title;
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

    public String getCreatedAt() {
        return createdAt;
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
}
