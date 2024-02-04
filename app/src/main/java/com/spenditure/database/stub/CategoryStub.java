package com.spenditure.database.stub;

import com.spenditure.database.CategoryPersistence;
import com.spenditure.logic.exceptions.InvalidCategoryException;
import com.spenditure.object.Category;

import java.util.*;


public class CategoryStub implements CategoryPersistence {
    //Attributes
    private List<Category> categoryList;
    private int currentID = 1;

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
    public Category addCategory(String newCategoryName) throws InvalidCategoryException{
        if (validateUnique(newCategoryName)){
            Category newCategory = new Category(newCategoryName,generateUniqueID());
            this.categoryList.add(newCategory);
            return newCategory ;
        }else{
            throw new InvalidCategoryException("Category's ID not exist");
        }


    }

    @Override
    public void deleteCategoryByID(int id) throws InvalidCategoryException {
        Iterator<Category> iterator = this.categoryList.iterator();
        boolean found = false;
        while (iterator.hasNext() && !found) {
            Category curr = iterator.next();
            if(curr.getID() == id){
                iterator.remove();
                found = true;
            }
        }
        if(!found) throw new InvalidCategoryException("Category's ID not exist");
    }

    @Override
    public Category getCategoryByID(int id) throws InvalidCategoryException {
        for(Category category : this.categoryList){
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
        for(Category currCategory : this.categoryList){
            if (categoryName.equalsIgnoreCase(currCategory.getName())) return false;
        }
        return true;
    }

//    public static void cleanup(){
//        currentID = 1;
//    }

}
