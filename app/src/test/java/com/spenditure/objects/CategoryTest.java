package com.spenditure.objects;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import com.spenditure.object.Category;

public class CategoryTest {

    @Before
    public void setup(){
        System.out.println("Starting test for Category");
    }

    @Test
    public void testCreateCategory(){
        System.out.println("Starting create Category");
        Category category = new Category("tuition fee",1);
        assertNotNull(category);
        assertEquals("tuition fee",category.getName());
        assertEquals(1,category.getID());

        System.out.println("Finished testCreateCategory");

    }
}
