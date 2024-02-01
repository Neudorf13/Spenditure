package com.spenditure.business;

import org.junit.Test;
import static org.junit.Assert.*;

import com.spenditure.logic.CategoryHandler;
import com.spenditure.logic.exceptions.InvalidCategoryException;


public class ExceptionTest {

    @Test
    public void testInvalidCategory(){
        CategoryHandler categoryHandler = new CategoryHandler(true);
        boolean caught = false;
        try {
            categoryHandler.getCategoryByID(99);
        }catch (InvalidCategoryException e){
            caught = true;
        }
        assertTrue(caught);

        caught = false;
        try {
            categoryHandler.addCategory("Food");
        }catch (InvalidCategoryException e){
            caught = true;
        }
        assertTrue(caught);

        caught = false;
        try {
            categoryHandler.deleteCategory(99);
        }catch (InvalidCategoryException e){
            caught = true;
        }
        assertTrue(caught);
    }
}
