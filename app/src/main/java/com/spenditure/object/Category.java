package com.spenditure.object;

public class Category implements ICategory {
    //Attributes
    private String name;
    private int categoryID;

    //Constructors
    public Category(String name, int categoryID){
        this.name = name;
        this.categoryID = categoryID;
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

}
