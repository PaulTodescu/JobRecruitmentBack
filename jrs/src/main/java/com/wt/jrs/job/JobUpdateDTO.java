package com.wt.jrs.job;

public class JobUpdateDTO {
    private Long id;
    private String title;
    private String description;
    private Integer salary;
    private SalaryCurrency salaryCurrency;
    private SalaryType salaryType;
    private String companyName;
    private String location;
    private Long categoryId;

    public JobUpdateDTO() {
    }

    public JobUpdateDTO(Long id, String title, String description, Integer salary,
                        SalaryCurrency salaryCurrency, SalaryType salaryType,
                        String companyName, String location, Long categoryId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.salary = salary;
        this.salaryCurrency = salaryCurrency;
        this.salaryType = salaryType;
        this.companyName = companyName;
        this.location = location;
        this.categoryId = categoryId;
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

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
