package com.spenditure.database;

import com.spenditure.object.Category;
import java.util.*;

public interface CategoryPersistence {
    List <Category> getAllCategory();
    Category addCategory(Category newCategory);

    boolean deleteCategory(Category targetCategory);

    Category getCategoryByID(int id);

}
