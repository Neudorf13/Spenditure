package com.spenditure.database;

import com.spenditure.logic.exceptions.InvalidCategoryException;
import com.spenditure.object.MainCategory;
import java.util.*;

public interface CategoryPersistence {
    List <MainCategory> getAllCategory();
    MainCategory addCategory(String newCategory);

    void deleteCategoryByID(int id) throws InvalidCategoryException;

    MainCategory getCategoryByID(int id) throws InvalidCategoryException;

}
