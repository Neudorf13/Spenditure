package com.spenditure.business;

import com.spenditure.database.stub.CategoryStub;
import com.spenditure.logic.CategoryHandler;
import com.spenditure.object.Category;

import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import static org.junit.Assert.*;

import android.util.Log;

public class CategoryHandlerTest {

    private CategoryHandler categoryHandler;
    @Before
    public void setup(){
        CategoryStub.cleanup();
        this.categoryHandler = new CategoryHandler(true);
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

}
