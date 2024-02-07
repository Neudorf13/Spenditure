package com.spenditure.database.stub;

import com.spenditure.database.CategoryPersistence;
import com.spenditure.logic.exceptions.InvalidCategoryException;
import com.spenditure.object.MainCategory;

import java.util.*;

/**
 * Category stub database
 * @author Bao Ngo
 * @version 06 Feb 2024
 * PURPOSE: Stub database store list of category. For testing purpose, there are three Category are initialized and added
 * to list in the constructor.
 */

public class CategoryStub implements CategoryPersistence {
    //Attributes
    private List<MainCategory> categoryList;
    private int currentID = 1;

    //Constructors
    public CategoryStub(){
        this.categoryList = new ArrayList<>();
        this.categoryList.add(new MainCategory("Grocery",generateUniqueID()));
        this.categoryList.add(new MainCategory("Food",generateUniqueID()));
        this.categoryList.add(new MainCategory("Hang out",generateUniqueID()));
    }

    //Business methods
    @Override
    public List<MainCategory> getAllCategory() {
        return this.categoryList;
    }

    @Override
    public MainCategory addCategory(String newCategoryName) throws InvalidCategoryException{
        if (validateUnique(newCategoryName)){
            MainCategory newCategory = new MainCategory(newCategoryName,generateUniqueID());
            this.categoryList.add(newCategory);
            return newCategory ;
        }else{
            throw new InvalidCategoryException("Category's ID not exist");
        }


    }

    @Override
    public void deleteCategoryByID(int id) throws InvalidCategoryException {
        Iterator<MainCategory> iterator = this.categoryList.iterator();
        boolean found = false;
        while (iterator.hasNext() && !found) {
            MainCategory curr = iterator.next();
            if(curr.getID() == id){
                iterator.remove();
                found = true;
            }
        }
        if(!found) throw new InvalidCategoryException("Category's ID not exist");
    }

    @Override
    public MainCategory getCategoryByID(int id) throws InvalidCategoryException {
        for(MainCategory category : this.categoryList){
            if(category.getID() == id){
                return  category;
            }
        }
        throw new InvalidCategoryException("Category's ID not exist: " + id);
    }

    //Support methods
    private  int generateUniqueID(){
        return currentID++;
    }

    private boolean validateUnique(String categoryName){
        for(MainCategory currCategory : this.categoryList){
            if (categoryName.equalsIgnoreCase(currCategory.getName())) return false;
        }
        return true;
    }

}
