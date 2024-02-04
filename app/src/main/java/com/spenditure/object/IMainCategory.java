package com.spenditure.object;

import com.spenditure.logic.exceptions.InvalidCategoryException;

import java.util.List;

public interface IMainCategory extends ICategory {
    public List<SubCategory> getSubCategories() throws InvalidCategoryException;
    public SubCategory addSubCategory(String newSubCategory) throws InvalidCategoryException;
    public void removeSubCategory(int id) throws InvalidCategoryException;
    public SubCategory getSubCategoryByID(int id) throws  InvalidCategoryException;


}
