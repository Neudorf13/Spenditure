package com.spenditure.object;

import com.spenditure.logic.exceptions.InvalidCategoryException;
import com.spenditure.logic.exceptions.InvalidSubCategoryException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainCategory implements IMainCategory {
    //Attributes
    private String name;
    private int categoryID;
    private List<SubCategory> listSubCategories; //For future development
    private final int MAX_SUB_CATEGORIES = 5;
    private static int currentID = 1;

    //Constructors
    public MainCategory(String name, int categoryID){
        this.name = name;
        this.categoryID = categoryID;
        this.listSubCategories = new ArrayList<>();
    }

    //Business methods
    @Override
    public String getName() {
        return this.name;
    }
    @Override
    public int getID() {
        return this.categoryID;
    }


    @Override
    public List<SubCategory> getSubCategories() { //For future development
        return this.listSubCategories;
    }

    public SubCategory addSubCategory(String newSubCategoryName) throws InvalidSubCategoryException {
        if(this.listSubCategories.size() >= 5){
            throw new InvalidSubCategoryException("Only " + this.MAX_SUB_CATEGORIES + " sub-categories are allowed per Category");
        }else{
            if (checkUniqueSub(newSubCategoryName)){
                SubCategory newSubCategory = new SubCategory(newSubCategoryName,generateUniqueID());
                this.listSubCategories.add(newSubCategory);
                return newSubCategory;
            }else{
                throw new InvalidSubCategoryException(newSubCategoryName + " sub-category already exists");
            }
        }
    }

    @Override
    public void removeSubCategory(int id) throws InvalidSubCategoryException{
        Iterator<SubCategory> iterator = this.listSubCategories.iterator();
        boolean found = false;
        while (iterator.hasNext() && !found) {
            SubCategory curr = iterator.next();
            if(curr.getID() == id){
                iterator.remove();
                found = true;
            }
        }
        if(!found) throw new InvalidSubCategoryException("Sub-category's ID not exist");
    }

    @Override
    public SubCategory getSubCategoryByID(int id) throws InvalidSubCategoryException {
        for(SubCategory currSub : this.listSubCategories){
            if(currSub.getID() == id) return currSub;
        }
        throw new InvalidSubCategoryException("Sub-category ID not exist");
    }

    private boolean checkUniqueSub(String newSubCategoryName){
        for( SubCategory currSubCategory : this.listSubCategories){
            if( currSubCategory.getName().equalsIgnoreCase(newSubCategoryName)) return false;
        }
        return true;
    }

    private static int generateUniqueID(){
        return currentID++;
    }




}
