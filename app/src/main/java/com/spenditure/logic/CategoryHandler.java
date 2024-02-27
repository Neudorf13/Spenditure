package com.spenditure.logic;

import com.spenditure.application.Services;
import com.spenditure.database.CategoryPersistence;
import com.spenditure.logic.exceptions.InvalidCategoryException;
import com.spenditure.logic.exceptions.InvalidSubCategoryException;
import com.spenditure.object.MainCategory;
import com.spenditure.object.SubCategory;

import java.util.List;

/**
 * Category Handler
 * @author Bao Ngo
 * @version 06 Feb 2024
 * PURPOSE: Handle all logic related to Category
 */

public class CategoryHandler {
    //Attribute
    private final CategoryPersistence dataAccessCategory;

    //Constructor
    public CategoryHandler(boolean inDeveloping){
        this.dataAccessCategory = Services.getCategoryPersistence(inDeveloping);
    }

    //Business methods
    public List<MainCategory> getAllCategory(int userID){
        return this.dataAccessCategory.getAllCategory(userID);
    }

    public MainCategory getCategoryByID(int id) throws InvalidCategoryException{
        return this.dataAccessCategory.getCategoryByID(id);
    }

//    public MainCategory addCategory(String newCategoryName) throws InvalidCategoryException {
//        if (newCategoryName == null){
//            throw new InvalidCategoryException("Null pointer");
//        }else if(newCategoryName.equals("")){
//            throw new InvalidCategoryException("Name of category must not be blank");
//        }
//        return this.dataAccessCategory.addCategory(newCategoryName);
//    }
    public MainCategory addCategory(MainCategory category) throws InvalidCategoryException {
        if (category == null)
            throw new InvalidCategoryException("Null pointer");

        return this.dataAccessCategory.addCategory(category);
    }

    public void deleteCategory(int id) throws InvalidCategoryException {
        this.dataAccessCategory.deleteCategoryByID(id);
    }

    /*
     * All methods implemented below are optional for future development for next iteration(2,3)
     * We are still considering about whether to allow user to add sub-category to each category
     */
    public SubCategory addSubCategory(int parentCategoryID,String newSubCategory) throws InvalidCategoryException,InvalidSubCategoryException{
        if(newSubCategory == null){
            throw new InvalidSubCategoryException("Null pointer");
        }else if( newSubCategory.equals("")){
            throw new InvalidSubCategoryException("Name of sub-category must not be blank");
        }
        MainCategory parentCategory = getCategoryByID(parentCategoryID);
        return parentCategory.addSubCategory(newSubCategory);
    }

    public void deleteSubCategory(int parentCategoryID,int subCategoryID) throws InvalidCategoryException,InvalidSubCategoryException {
        MainCategory parentCategory = getCategoryByID(parentCategoryID);
        parentCategory.removeSubCategory(subCategoryID);
    }

    public List<SubCategory> getAllSubCategoriesFromParent(int parentCategoryID) throws InvalidCategoryException{
        MainCategory parentCategory = getCategoryByID(parentCategoryID);
        return parentCategory.getSubCategories();
    }

    public SubCategory getSubCategoryFromParent(int parentCategoryID,int subCategoryID) throws InvalidCategoryException,InvalidSubCategoryException{
        MainCategory parentCategory = getCategoryByID(parentCategoryID);
        return parentCategory.getSubCategoryByID(subCategoryID);
    }


}
