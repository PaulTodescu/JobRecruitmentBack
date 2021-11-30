package com.wt.jrs.category;

import com.wt.jrs.job.JobDTO;
import com.wt.jrs.job.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryDAO categoryDAO;
    private final JobService jobService;

    @Autowired
    public CategoryService(CategoryDAO categoryDAO, JobService jobService) {
        this.categoryDAO = categoryDAO;
        this.jobService = jobService;
    }

    public List<CategoryDTO> findAllCategories(){

        List<CategoryEntity> categories = categoryDAO.findAll();
        return mapCategoriesToDTOs(categories);
    }

    public CategoryDTO mapCategoryToDTO(CategoryEntity category){
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());
        return categoryDTO;
    }

    public List<CategoryDTO> mapCategoriesToDTOs(List<CategoryEntity> categories){
        return categories.stream().map(this::mapCategoryToDTO).collect(Collectors.toList());
    }

    public CategoryEntity findCategoryById(Long categoryId) {
        Optional<CategoryEntity> category = this.categoryDAO.findCategoryEntityById(categoryId);
        return category.orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category with id: " + categoryId + " was not found"));
    }

    public CategoryEntity findCategoryByName(String categoryName) {
        Optional<CategoryEntity> category = this.categoryDAO.findCategoryEntityByName(categoryName);
        return category.orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category with name: " + categoryName + " was not found"));
    }

    public List<JobDTO> findJobsForCategory(Long categoryId) {
        CategoryEntity category = categoryDAO.findCategoryEntityById(categoryId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category with id: " + categoryId + " was not found"));
        return this.jobService.mapJobsToDTOS(new ArrayList<>(category.getJobs()));
    }
}
