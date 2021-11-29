package com.wt.jrs.category;

import com.wt.jrs.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CategoryData implements CommandLineRunner {

    private final CategoryDAO categoryDAO;
    private final UserDAO userDAO;

    @Autowired
    public CategoryData(CategoryDAO categoryDAO, UserDAO userDAO) {
        this.categoryDAO = categoryDAO;
        this.userDAO = userDAO;
    }

    @Override
    public void run(String... args) {

        saveCategory(new CategoryEntity("Technology"));
        saveCategory(new CategoryEntity("Manufacturing"));
        saveCategory(new CategoryEntity("Health"));
        saveCategory(new CategoryEntity("Retail"));
        saveCategory(new CategoryEntity("Gas & Oil"));
        saveCategory(new CategoryEntity("Construction"));
        saveCategory(new CategoryEntity("Finance"));
        saveCategory(new CategoryEntity("Insurance"));
        saveCategory(new CategoryEntity("Automotive"));
        saveCategory(new CategoryEntity("Education"));
        saveCategory(new CategoryEntity("Agriculture"));
        saveCategory(new CategoryEntity("Mining"));
        saveCategory(new CategoryEntity("Trade"));
        saveCategory(new CategoryEntity("Transport"));
        saveCategory(new CategoryEntity("Goods"));
        saveCategory(new CategoryEntity("Telecommunications"));
        saveCategory(new CategoryEntity("Food"));
        saveCategory(new CategoryEntity("Engineering"));
        saveCategory(new CategoryEntity("Finances"));
        saveCategory(new CategoryEntity("Investment"));
        saveCategory(new CategoryEntity("Research"));
        saveCategory(new CategoryEntity("Distribution"));
        saveCategory(new CategoryEntity("Forestry"));
        saveCategory(new CategoryEntity("Chemical"));
        saveCategory(new CategoryEntity("Production"));
        saveCategory(new CategoryEntity("Wholesale"));
        saveCategory(new CategoryEntity("Advertising"));
        saveCategory(new CategoryEntity("Publishing"));
        saveCategory(new CategoryEntity("Sales"));
        saveCategory(new CategoryEntity("Hospitality"));
        saveCategory(new CategoryEntity("Accounting"));
        saveCategory(new CategoryEntity("Utilities"));

    }

    public void saveCategory(CategoryEntity categoryEntity){
        if(categoryDAO.findCategoryEntityByName(categoryEntity.getName()).isEmpty()){
            categoryDAO.save(categoryEntity);
        }
    }
}
