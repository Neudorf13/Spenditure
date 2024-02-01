package com.spenditure.database;

import com.spenditure.logic.exceptions.InvalidCategoryException;
import com.spenditure.object.Category;
import java.util.*;

public interface CategoryPersistence {
    List <Category> getAllCategory();
    Category addCategory(String newCategory);

    void deleteCategoryByID(int id) throws InvalidCategoryException;

    Category getCategoryByID(int id) throws InvalidCategoryException;

}
