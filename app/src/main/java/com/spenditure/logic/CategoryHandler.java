package com.spenditure.logic;

import com.spenditure.application.Services;
import com.spenditure.database.CategoryPersistence;
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

    public Category getCategoryByID(int id){
        return this.dataAccessCategory.getCategoryByID(id);
    }

    public Category addCategory(Category newCategory){
        return this.dataAccessCategory.addCategory(newCategory);
    }

    public boolean deleteCategory(Category targetCategory){
        return this.dataAccessCategory.deleteCategory(targetCategory);
    }



}
