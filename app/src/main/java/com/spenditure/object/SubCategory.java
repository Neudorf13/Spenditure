package com.spenditure.object;

/**
 * Sub-Category interface (for future extension)
 * @author Bao Ngo
 * @version 06 Feb 2024
 */
public class SubCategory implements ICategory{
    //Attibutes
    private String name;
    private int categoryID;

    //Constructors
    public SubCategory(String name, int categoryID) {
        this.categoryID = categoryID;
        this.name = name;

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
