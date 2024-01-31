package com.spenditure.database.stub;

import com.spenditure.database.CategoryPersistence;
import com.spenditure.object.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryStub implements CategoryPersistence {
    //Attributes
    private List<Category> categoryList;
    private static int currentID = 1;

    //Constructors
    public CategoryStub(){
        this.categoryList = new ArrayList<>();
        this.categoryList.add(new Category("Grocery",generateUniqueID()));
        this.categoryList.add(new Category("Food",generateUniqueID()));
        this.categoryList.add(new Category("Hang out",generateUniqueID()));
    }
    //Business methods
    @Override
    public List<Category> getAllCategory() {
        return this.categoryList;
    }

    @Override
    public Category addCategory(Category newCategory) {
        if (validateUnique(newCategory.getName())){
            this.categoryList.add(newCategory);
            return newCategory;
        };
        return null;
    }

    @Override
    public boolean deleteCategory(Category targetCategory) {
        int index = this.categoryList.indexOf(targetCategory);
        if(index >0){
            categoryList.remove(targetCategory);

        }
        return index >0;
    }

    @Override
    public Category getCategoryByID(int id) {
        for(Category category : this.categoryList){
            if(category.getID() == id){
                return  category;
            }
        }
        return null;
    }

    //Support methods
    private static int generateUniqueID(){
        return currentID++;
    }

    private boolean validateUnique(String categoryName){
        for(Category currCategory : this.categoryList){
            if (categoryName.equalsIgnoreCase(currCategory.getName())) return false;
        }
        return true;
    }

}
