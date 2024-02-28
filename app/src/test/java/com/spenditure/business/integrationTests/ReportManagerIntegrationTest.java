package com.spenditure.business.integrationTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.spenditure.logic.ReportManager;
import com.spenditure.object.MainCategory;
import com.spenditure.utils.TestUtils;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ReportManagerIntegrationTest {

    private ReportManager reportManager;
    private File tempDB;

    @Before
    public void setup() throws IOException {

        this.tempDB = TestUtils.copyDB();
        this.reportManager = new ReportManager(false);

        assertNotNull(this.reportManager);
    }

//    @Test
//    public void testPercentSum() {
//        //get a percent for each category and make sure it sums to 100
//        //check individual %'s against expected values
//        double category1 = reportManager.getPercentForCategory(1);
//        double category2 = reportManager.getPercentForCategory(2);
//        double category3 = reportManager.getPercentForCategory(3);
//        double sum = category1 + category2 + category3;
//
//        assertEquals("Expected category 1 to take up approximately 19.72% of total transaction cost", category1,19.72,0.1);
//        assertEquals("Expected category 2 to take up approximately 18.65% of total transaction cost",category2,18.65,0.1);
//        assertEquals("Expected category 3 to take up approximately 61.62% of total transaction cost",category3,61.62,0.1);
//        assertEquals("Expected percent sum of each category to be 100%",sum,100, 0.1);
//    }
//
//    @Test
//    public void testTotalForCategory() {
//        //tests each individual total is less than or equal to the maximum total value
//        //tests each individual total against expected values
//        double category1Total = reportManager.getTotalForCategory(1);
//        double category2Total = reportManager.getTotalForCategory(2);
//        double category3Total = reportManager.getTotalForCategory(3);
//
//        double total = reportManager.getTotalForAllTransactions();
//
//        assertTrue("Expected transaction sum for Category 1 to be less than or equal to the total transaction sum",category1Total <= total);
//        assertTrue("Expected transaction sum for Category 2 to be less than or equal to the total transaction sum",category2Total <= total);
//        assertTrue("Expected transaction sum for Category 3 to be less than or equal to the total transaction sum",category3Total <= total);
//
//        assertEquals("Expected transaction sum for Category 1 to be 440.75",category1Total,440.75,0);
//        assertEquals("Expected transaction sum for Category 2 to be 416.74",category2Total,416.74,0);
//        assertEquals("Expected transaction sum for Category 3 to be 1376.45",category3Total,1376.45,0);
//    }
//
//
    @Test
    public void testAverageForCategory() {
        //tests each categories average value against expected values
        System.out.println("before");
        double testCategory1 = reportManager.getAverageForCategory(1);
        System.out.println("after");
        double testCategory2 = reportManager.getAverageForCategory(2);
        double testCategory3 = reportManager.getAverageForCategory(3);


        assertEquals("Expected transaction average for Category 1 to be approximately 110.19",testCategory1,110.19,0.1);
        assertEquals("Expected transaction average for Category 2 to be approximately 104.19",testCategory2,104.19,0.1);
        assertEquals("Expected transaction average for Category 3 to be approximately 229.41",testCategory3,229.41,0.1);
    }
//
//    @Test
//    public void testSortByTotal() {
//        //tests categories are sorted properly based on total attribute, both ascending + descending
//        ArrayList<MainCategory> descendingCategoryList = reportManager.sortByTotal(1,true);
//        assertEquals("Expected Category: 'Hang out'",descendingCategoryList.get(0).getName(), "Hang out");
//        assertEquals("Expected Category: 'Grocery'",descendingCategoryList.get(1).getName(), "Grocery");
//        assertEquals("Expected Category: 'Food'",descendingCategoryList.get(2).getName(), "Food");
//
//        ArrayList<MainCategory> ascendingCategoryList = reportManager.sortByTotal(1,false);
//        assertEquals("Expected Category: 'Food'",ascendingCategoryList.get(0).getName(), "Food");
//        assertEquals("Expected Category: 'Grocery'",ascendingCategoryList.get(1).getName(), "Grocery");
//        assertEquals("Expected Category: 'Hang out'",ascendingCategoryList.get(2).getName(), "Hang out");
//    }
//
//    @Test
//    public void testSortByPercent() {
//        //tests categories are sorted properly based on percent attribute, both ascending + descending
//        ArrayList<MainCategory> descendingCategoryList = reportManager.sortByPercent(1,true);
//        assertEquals("Expected Category: 'Hang out'",descendingCategoryList.get(0).getName(), "Hang out");
//        assertEquals("Expected Category: 'Grocery'",descendingCategoryList.get(1).getName(), "Grocery");
//        assertEquals("Expected Category: 'Food'",descendingCategoryList.get(2).getName(), "Food");
//
//        ArrayList<MainCategory> ascendingCategoryList = reportManager.sortByPercent(1,false);
//        assertEquals("Expected Category: 'Food'",ascendingCategoryList.get(0).getName(), "Food");
//        assertEquals("Expected Category: 'Grocery'",ascendingCategoryList.get(1).getName(), "Grocery");
//        assertEquals("Expected Category: 'Hang out'",ascendingCategoryList.get(2).getName(), "Hang out");
//    }
//
    @Test
    public void testSortByAverage() {
        System.out.println("test?");
        //tests categories are sorted properly based on average attribute, both ascending + descending
        ArrayList<MainCategory> descendingCategoryList = reportManager.sortByAverage(1,true);
        System.out.println("probably not eh");
        assertEquals("Expected Category: 'Hang out'",descendingCategoryList.get(0).getName(), "Hang out");
        assertEquals("Expected Category: 'Grocery'",descendingCategoryList.get(1).getName(), "Grocery");
        assertEquals("Expected Category: 'Food'",descendingCategoryList.get(2).getName(), "Food");

        ArrayList<MainCategory> ascendingCategoryList = reportManager.sortByAverage(1,false);
        assertEquals("Expected Category: 'Food'",ascendingCategoryList.get(0).getName(), "Food");
        assertEquals("Expected Category: 'Grocery'",ascendingCategoryList.get(1).getName(), "Grocery");
        assertEquals("Expected Category: 'Hang out'",ascendingCategoryList.get(2).getName(), "Hang out");
    }

    @Test
    public void dummy() {
        System.out.println("nothing to see here folks");
    }

//    @Test
//    public void testFakeSQL() {
//        reportManager.callShitMethod();
//    }
}
