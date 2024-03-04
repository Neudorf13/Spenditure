package com.spenditure.business.integrationTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.spenditure.logic.CategoryHandler;
import com.spenditure.logic.ReportManager;
import com.spenditure.object.MainCategory;
import com.spenditure.object.SubCategory;
import com.spenditure.utils.TestUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class CategoryHandlerIT {

    private CategoryHandler categoryHandler;

    private File tempDB;

    @Before
    public void setup() throws IOException {
        this.tempDB = TestUtils.copyDB();
        this.categoryHandler = new CategoryHandler(false);
        categoryHandler.cleanup(false);
    }
    @After
    public void tearDown(){
        this.categoryHandler = null;
        this.tempDB = null;
    }

    @Test
    public void testCategorySet() {
        int expectedSize = 3;

        List<MainCategory> categoryList = categoryHandler.getAllCategory(1);
        System.out.println("Category Size for user 1: " + categoryList.size());
        assertEquals(expectedSize, categoryList.size());

        MainCategory category1 = categoryHandler.getCategoryByID(1);
        MainCategory category2 = categoryHandler.getCategoryByID(2);
        MainCategory category3 = categoryHandler.getCategoryByID(3);

        assertEquals("Grocery", category1.getName());
        assertEquals("Food", category2.getName());
        assertEquals("Hang out", category3.getName());
    }



    @Test
    public void testInsert(){
        int expectedSize = 3;
        int userID = 1;
//        MainCategory bills = new MainCategory("bills", 99, 1);
        assertEquals(categoryHandler.getAllCategory(1).size(),expectedSize);


        categoryHandler.addCategory("bills",  userID);
        assertEquals(expectedSize + 1,categoryHandler.getAllCategory(userID).size());
        assertEquals("Grocery",categoryHandler.getCategoryByID(userID).getName());
//        assertEquals("bills",categoryHandler.getCategoryByID(1).getName());
    }

    @Test
    public void testDelete(){
        int expectedSize = 3;

        assertEquals(categoryHandler.getAllCategory(1).size(),expectedSize);
        assertEquals("Grocery",categoryHandler.getCategoryByID(1).getName());
        assertEquals("Food",categoryHandler.getCategoryByID(2).getName());
        assertEquals("Hang out",categoryHandler.getCategoryByID(3).getName());

        categoryHandler.printCategoryTable();
        categoryHandler.deleteCategory(1);
        categoryHandler.printCategoryTable();

        int count = categoryHandler.getAllCategory(1).size();
        System.out.println("Count: " + count);
        //assertEquals(2 ,count);
        assertEquals("Food",categoryHandler.getCategoryByID(2).getName());
        assertEquals("Hang out",categoryHandler.getCategoryByID(3).getName());
    }
//
//    @Test
//    public void testAddSubCategory(){
//        assertEquals(0,categoryHandler.getAllSubCategoriesFromParent(1).size());
//        SubCategory newSubCategoryKid =categoryHandler.addSubCategory(1,"For kids");
//        assertEquals(1,categoryHandler.getAllSubCategoriesFromParent(1).size());
//        SubCategory newSubFromListKid = categoryHandler.getSubCategoryFromParent(1,newSubCategoryKid.getCategoryID());
//        assertNotNull(newSubFromListKid);
//        assertEquals(newSubCategoryKid.getCategoryID(),newSubFromListKid.getCategoryID());
//        assertEquals(newSubCategoryKid.getName(),newSubFromListKid.getName());
//
//        SubCategory newSubCategoryAdult =categoryHandler.addSubCategory(1,"For adult");
//        assertEquals(2,categoryHandler.getAllSubCategoriesFromParent(1).size());
//        SubCategory newSubFromListAdult = categoryHandler.getSubCategoryFromParent(1,newSubCategoryAdult.getCategoryID());
//        assertNotNull(newSubFromListKid);
//        assertEquals(newSubCategoryAdult.getCategoryID(),newSubFromListAdult.getCategoryID());
//        assertEquals(newSubCategoryAdult.getName(),newSubFromListAdult.getName());
//
//        SubCategory newSubCategoryLunch = categoryHandler.addSubCategory(2,"For lunch");
//        assertEquals(1,categoryHandler.getAllSubCategoriesFromParent(2).size());
//        SubCategory newSubFromListLunch = categoryHandler.getSubCategoryFromParent(2,newSubCategoryLunch.getCategoryID());
//        assertNotNull(newSubFromListLunch);
//        assertEquals(newSubCategoryLunch.getCategoryID(),newSubFromListLunch.getCategoryID());
//        assertEquals(newSubCategoryLunch.getName(),newSubFromListLunch.getName());
//    }
//
//    @Test
//    public void testDeleteSubCategory(){
//        assertEquals(0,categoryHandler.getAllSubCategoriesFromParent(1).size());
//        SubCategory newSubCategoryKid =categoryHandler.addSubCategory(1,"For kids");
//        SubCategory newSubCategoryAdult =categoryHandler.addSubCategory(1,"For adult");
//        assertEquals(2,categoryHandler.getAllSubCategoriesFromParent(1).size());
//
//        categoryHandler.deleteSubCategory(1,newSubCategoryKid.getCategoryID());
//        assertEquals(1,categoryHandler.getAllSubCategoriesFromParent(1).size());
//        SubCategory newSubFromListAdult = categoryHandler.getSubCategoryFromParent(1,newSubCategoryAdult.getCategoryID());
//        assertEquals(newSubCategoryAdult.getCategoryID(),newSubFromListAdult.getCategoryID());
//        assertEquals(newSubCategoryAdult.getName(),newSubFromListAdult.getName());
//    }
}
