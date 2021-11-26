package com.wt.jrs.category;

import com.wt.jrs.job.JobDTO;
import com.wt.jrs.job.JobEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping(path = "/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<CategoryDTO>> getAllCategories(){
        List<CategoryDTO> categories = categoryService.findAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping(path = "/{categoryId}")
    public ResponseEntity<CategoryEntity> getCategoryById(@PathVariable("categoryId") Long categoryId){
        CategoryEntity category = this.categoryService.findCategoryById(categoryId);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @GetMapping(path = "/{categoryId}/jobs")
    public ResponseEntity<List<JobDTO>> getJobsForCategory(
            @PathVariable("categoryId") Long categoryId){
        List<JobDTO> jobs = categoryService.findJobsForCategory(categoryId);
        return new ResponseEntity<>(jobs, HttpStatus.OK);
    }

}
