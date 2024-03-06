package com.spenditure.logic;

import com.spenditure.logic.exceptions.InvalidCategoryException;
import com.spenditure.object.MainCategory;
import com.spenditure.object.SubCategory;

import java.util.List;

public interface ICategoryHandler {

    public void cleanup(boolean getSubDB);

    public List<MainCategory> getAllCategory(int userID);

    public MainCategory getCategoryByID(int id) throws InvalidCategoryException;


    public MainCategory addCategory(String newCategoryName,int userID);

    public void deleteCategory(int id);
    
    public SubCategory addSubCategory(int parentCategoryID, String newSubCategory);

    public void deleteSubCategory(int parentCategoryID,int subCategoryID);

    public List<SubCategory> getAllSubCategoriesFromParent(int parentCategoryID);

    public SubCategory getSubCategoryFromParent(int parentCategoryID,int subCategoryID);
}
