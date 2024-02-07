package com.spenditure.object;

import com.spenditure.logic.exceptions.InvalidCategoryException;

import java.util.List;
/**
 * Main Category interface
 * @author Bao Ngo
 * @version 06 Feb 2024
 */

public interface IMainCategory extends ICategory {
    public List<SubCategory> getSubCategories() throws InvalidCategoryException;
    public SubCategory addSubCategory(String newSubCategory) throws InvalidCategoryException;
    public void removeSubCategory(int id) throws InvalidCategoryException;
    public SubCategory getSubCategoryByID(int id) throws  InvalidCategoryException;


}
