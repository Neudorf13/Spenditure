package com.spenditure.object;

public class SubCategory implements ICategory{
    private String name;
    private int categoryID;
    public SubCategory(String name, int categoryID) {
        this.categoryID = categoryID;
        this.name = name;

    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getID() {
        return this.categoryID;
    }
}
