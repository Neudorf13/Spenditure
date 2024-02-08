package com.spenditure.business;

import com.spenditure.logic.CategoryHandler;
import com.spenditure.object.SubCategory;

import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import static org.junit.Assert.*;

/**
 * Category handler unit tests
 * @author Bao Ngo
 * @version 06 Feb 2024
 */

public class CategoryHandlerTest {
    private CategoryHandler categoryHandler;
    @Before
    public void setup(){
        this.categoryHandler = new CategoryHandler(true);
        categoryHandler.cleanup(true);
    }
    @After
    public void tearDown(){
        this.categoryHandler = null;
    }

    @Test
    public void testCategorySet(){
        int expectedSize = 3;

        assertEquals(categoryHandler.getAllCategory().size(),expectedSize);
        assertEquals("Grocery",categoryHandler.getCategoryByID(1).getName());
        assertEquals("Food",categoryHandler.getCategoryByID(2).getName());
        assertEquals("Hang out",categoryHandler.getCategoryByID(3).getName());
    }
//
    @Test
    public void testInsert(){
        int expectedSize = 3;

        assertEquals(categoryHandler.getAllCategory().size(),expectedSize);
        categoryHandler.addCategory("bills");
        assertEquals(expectedSize + 1,categoryHandler.getAllCategory().size());
        assertEquals("Grocery",categoryHandler.getCategoryByID(1).getName());
        assertEquals("bills",categoryHandler.getCategoryByID(4).getName());
    }

    @Test
    public void testDelete(){
        int expectedSize = 3;

        assertEquals(categoryHandler.getAllCategory().size(),expectedSize);
        assertEquals("Grocery",categoryHandler.getCategoryByID(1).getName());
        assertEquals("Food",categoryHandler.getCategoryByID(2).getName());
        assertEquals("Hang out",categoryHandler.getCategoryByID(3).getName());

        categoryHandler.deleteCategory(1);

        assertEquals(expectedSize - 1 ,categoryHandler.getAllCategory().size());
        assertEquals("Food",categoryHandler.getCategoryByID(2).getName());
        assertEquals("Hang out",categoryHandler.getCategoryByID(3).getName());
    }

    @Test
    public void testAddSubCategory(){
        assertEquals(0,categoryHandler.getAllSubCategoriesFromParent(1).size());
        SubCategory newSubCategoryKid =categoryHandler.addSubCategory(1,"For kids");
        assertEquals(1,categoryHandler.getAllSubCategoriesFromParent(1).size());
        SubCategory newSubFromListKid = categoryHandler.getSubCategoryFromParent(1,newSubCategoryKid.getID());
        assertNotNull(newSubFromListKid);
        assertEquals(newSubCategoryKid.getID(),newSubFromListKid.getID());
        assertEquals(newSubCategoryKid.getName(),newSubFromListKid.getName());

        SubCategory newSubCategoryAdult =categoryHandler.addSubCategory(1,"For adult");
        assertEquals(2,categoryHandler.getAllSubCategoriesFromParent(1).size());
        SubCategory newSubFromListAdult = categoryHandler.getSubCategoryFromParent(1,newSubCategoryAdult.getID());
        assertNotNull(newSubFromListKid);
        assertEquals(newSubCategoryAdult.getID(),newSubFromListAdult.getID());
        assertEquals(newSubCategoryAdult.getName(),newSubFromListAdult.getName());

        SubCategory newSubCategoryLunch = categoryHandler.addSubCategory(2,"For lunch");
        assertEquals(1,categoryHandler.getAllSubCategoriesFromParent(2).size());
        SubCategory newSubFromListLunch = categoryHandler.getSubCategoryFromParent(2,newSubCategoryLunch.getID());
        assertNotNull(newSubFromListLunch);
        assertEquals(newSubCategoryLunch.getID(),newSubFromListLunch.getID());
        assertEquals(newSubCategoryLunch.getName(),newSubFromListLunch.getName());
    }

    @Test
    public void testDeleteSubCategory(){
        assertEquals(0,categoryHandler.getAllSubCategoriesFromParent(1).size());
        SubCategory newSubCategoryKid =categoryHandler.addSubCategory(1,"For kids");
        SubCategory newSubCategoryAdult =categoryHandler.addSubCategory(1,"For adult");
        assertEquals(2,categoryHandler.getAllSubCategoriesFromParent(1).size());

        categoryHandler.deleteSubCategory(1,newSubCategoryKid.getID());
        assertEquals(1,categoryHandler.getAllSubCategoriesFromParent(1).size());
        SubCategory newSubFromListAdult = categoryHandler.getSubCategoryFromParent(1,newSubCategoryAdult.getID());
        assertEquals(newSubCategoryAdult.getID(),newSubFromListAdult.getID());
        assertEquals(newSubCategoryAdult.getName(),newSubFromListAdult.getName());
    }

}
