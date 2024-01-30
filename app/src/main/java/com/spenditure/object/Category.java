package com.spenditure.object;

public class Category implements ICategory {
    //Attributes
    private String name;
    private int userID;
    private int categoryID;

    //Constructors
    public Category(String name, int userID, int categoryID){
        this.name = name;
        this.userID = userID;
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
    @Override
    public int getUserID() {
        return this.userID;
    }
}
