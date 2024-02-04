package com.spenditure.business;

//import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.*;

import com.spenditure.logic.CategoryHandler;
import com.spenditure.logic.ReportManager;
import com.spenditure.object.Category;

import org.junit.Before;
import org.junit.Test;

public class ReportManagerTest {

    private ReportManager reportManager;

    private CategoryHandler categoryHandler;

    @Before
    public void setup() {
        this.reportManager = new ReportManager(true);

        categoryHandler = new CategoryHandler(true);

//        Category grocery = this.categoryHandler.getCategoryByID(1);
//        Category food = this.categoryHandler.getCategoryByID(2);
//        Category stuff = this.categoryHandler.getCategoryByID(3);



    }

    @Test
    public void testPercentSum() {
        //should use stub data to get a percent for each category and make sure it sums to 100
        double category1 = reportManager.getPercentForCategory(1);
        double category2 = reportManager.getPercentForCategory(2);
        double category3 = reportManager.getPercentForCategory(3);
        double sum = category1 + category2 + category3;
        assertEquals(sum,100, 0.1);
    }

    @Test
    public void testTotalForCategory() {
        double categoryTotal = reportManager.getTotalForCategory(1);
        double total = reportManager.getTotalForAllTransactions();
        assertTrue(categoryTotal <= total);
    }

    @Test
    public void testAverageForCategory() {
        //should have average for
        double testCategory1 = reportManager.getAverageForCategory(1);
        double testCategory2 = reportManager.getAverageForCategory(2);
        double testCategory3 = reportManager.getAverageForCategory(3);



    }
}
