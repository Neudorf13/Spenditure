package com.spenditure.logic;

import com.spenditure.application.Services;
import com.spenditure.database.CategoryPersistence;
import com.spenditure.logic.exceptions.InvalidCategoryException;
import com.spenditure.object.Category;
import java.util.List;

public class CategoryHandler {
    private CategoryPersistence dataAccessCategory;
    public CategoryHandler(boolean inDeveloping){
        this.dataAccessCategory = Services.getCategoryPersistence(inDeveloping);
    }

    public List<Category> getAllCategory(){
        return this.dataAccessCategory.getAllCategory();
    }

    public Category getCategoryByID(int id) throws InvalidCategoryException{
        return this.dataAccessCategory.getCategoryByID(id);
    }

    public Category addCategory(String newCategoryName) throws InvalidCategoryException {
        if (newCategoryName == null){
            throw new InvalidCategoryException("Null pointer");
        }
        return this.dataAccessCategory.addCategory(newCategoryName);
    }

    public void deleteCategory(int id) throws InvalidCategoryException{
        this.dataAccessCategory.deleteCategoryByID(id);
    }



}
