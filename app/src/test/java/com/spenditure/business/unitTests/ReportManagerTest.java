/**
 * TransactionsListViewActivity.java
 *
 * COMP3350 SECTION A02
 *
 * @author Trevor
 * @date Tuesday, February 7, 2024
 *
 * PURPOSE:
 *  Tests Report Manager class in logic layer
 **/



package com.spenditure.business.unitTests;

//import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.*;
import java.util.ArrayList; // import the ArrayList class

import com.spenditure.logic.DateTimeAdjuster;
import com.spenditure.logic.GeneralReportHandler;
import com.spenditure.logic.ReportManager;
import com.spenditure.logic.UserManager;
import com.spenditure.object.DateTime;
import com.spenditure.object.MainCategory;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ReportManagerTest {


    private ReportManager reportManager;

    @Before
    public void setup() {
//        Services.restartCategoryDB(true);
        this.reportManager = new ReportManager(true);
    }

    @After
    public void tearDown() {
        reportManager = null;
    }


    /*

    ENDED UP BEING TESTS FOR DateTimeAdjuster.

     */
    @Test
    public void testFixDateTime() {

        DateTime dateTime = new DateTime(2024, 01, 01, 00, 00, 00);

        dateTime.adjust(0, 0, -7, 0, 0, 0);

//        DateTime fixed = DateTimeAdjuster.correctDateTime(dateTime);

        assertEquals(2023, dateTime.getYear());
        assertEquals(12, dateTime.getMonth());
        assertEquals(25, dateTime.getDay());

        dateTime = new DateTime(2001, 01, 01);
        dateTime.adjust(0, 0, -1, 0, 0, 0);

//        fixed = DateTimeAdjuster.correctDateTime(dateTime);

        assertEquals(2000, dateTime.getYear());
        assertEquals(12, dateTime.getMonth());
        assertEquals(31, dateTime.getDay());

        dateTime = new DateTime(2010, 01, 01, 00, 00, 00);
        dateTime.adjust(0, 0, 0, 0, 0, -1);

//        fixed = DateTimeAdjuster.correctDateTime(dateTime);

        assertEquals(2009, dateTime.getYear());
        assertEquals(12, dateTime.getMonth());
        assertEquals(31, dateTime.getDay());
        assertEquals(23, dateTime.getHour());
        assertEquals(59, dateTime.getMinute());
        assertEquals(59, dateTime.getSeconds());

        dateTime = new DateTime(2023, 12, 31, 23, 59, 59);
        dateTime.adjust(0, 0, 0, 0, 0, 1);

//        fixed = DateTimeAdjuster.correctDateTime(dateTime);

        assertEquals(2024, dateTime.getYear());
        assertEquals(01, dateTime.getMonth());
        assertEquals(01, dateTime.getDay());
        assertEquals(00, dateTime.getHour());
        assertEquals(00, dateTime.getMinute());
        assertEquals(00, dateTime.getSeconds());

        dateTime = new DateTime(2024, 03, 01);
        dateTime.adjust(0, 0, -1, 0, 0, 0);

//        fixed = DateTimeAdjuster.correctDateTime(dateTime);

        assertEquals(2024, dateTime.getYear());
        assertEquals(02, dateTime.getMonth());
        assertEquals(29, dateTime.getDay());

        dateTime = new DateTime(2023, 03, 01);
        dateTime.adjust(0, 0, -1, 0, 0, 0);

//        fixed = DateTimeAdjuster.correctDateTime(dateTime);

        assertEquals(2023, dateTime.getYear());
        assertEquals(02, dateTime.getMonth());
        assertEquals(28, dateTime.getDay());

        dateTime = new DateTime(2001, 01, 01);
        dateTime.adjust(0, 0, 100, 0, 0, 0);

        assertEquals(2001, dateTime.getYear());
        assertEquals(04, dateTime.getMonth());

        dateTime.adjust(0, 0, -100, 0, 0, 0);

        assertEquals(2001, dateTime.getYear());
        assertEquals(01, dateTime.getMonth());

    }


/*
    @Test
    public void testPercentSum() {
        //get a percent for each category and make sure it sums to 100
        //check individual %'s against expected values
        double category1 = reportManager.getPercentForCategory(1);
        double category2 = reportManager.getPercentForCategory(2);
        double category3 = reportManager.getPercentForCategory(3);
        double sum = category1 + category2 + category3;

        assertEquals("Expected category 1 to take up approximately 19.72% of total transaction cost", category1,19.72,0.1);
        assertEquals("Expected category 2 to take up approximately 18.65% of total transaction cost",category2,18.65,0.1);
        assertEquals("Expected category 3 to take up approximately 61.62% of total transaction cost",category3,61.62,0.1);
        assertEquals("Expected percent sum of each category to be 100%",sum,100, 0.1);
    }



    @Test
    public void testTotalForCategory() {
        //tests each individual total is less than or equal to the maximum total value
        //tests each individual total against expected values
        double category1Total = reportManager.getTotalForCategory(1);
        double category2Total = reportManager.getTotalForCategory(2);
        double category3Total = reportManager.getTotalForCategory(3);

        double total = reportManager.getTotalForAllTransactions();

        assertTrue("Expected transaction sum for Category 1 to be less than or equal to the total transaction sum",category1Total <= total);
        assertTrue("Expected transaction sum for Category 2 to be less than or equal to the total transaction sum",category2Total <= total);
        assertTrue("Expected transaction sum for Category 3 to be less than or equal to the total transaction sum",category3Total <= total);

        assertEquals("Expected transaction sum for Category 1 to be 440.75",category1Total,440.75,0);
        assertEquals("Expected transaction sum for Category 2 to be 416.74",category2Total,416.74,0);
        assertEquals("Expected transaction sum for Category 3 to be 1376.45",category3Total,1376.45,0);
    }


    @Test
    public void testAverageForCategory() {
        //tests each categories average value against expected values
        double testCategory1 = reportManager.getAverageForCategory(1);
        double testCategory2 = reportManager.getAverageForCategory(2);
        double testCategory3 = reportManager.getAverageForCategory(3);

        assertEquals("Expected transaction average for Category 1 to be approximately 110.19",testCategory1,110.19,0.1);
        assertEquals("Expected transaction average for Category 2 to be approximately 104.19",testCategory2,104.19,0.1);
        assertEquals("Expected transaction average for Category 3 to be approximately 229.41",testCategory3,229.41,0.1);
    }

    @Test
    public void testSortByTotal() {
        //tests categories are sorted properly based on total attribute, both ascending + descending
        ArrayList<MainCategory> descendingCategoryList = reportManager.sortByTotal(1,true);
        assertEquals("Expected Category: 'Hang out'",descendingCategoryList.get(0).getName(), "Hang out");
        assertEquals("Expected Category: 'Grocery'",descendingCategoryList.get(1).getName(), "Grocery");
        assertEquals("Expected Category: 'Food'",descendingCategoryList.get(2).getName(), "Food");

        ArrayList<MainCategory> ascendingCategoryList = reportManager.sortByTotal(1,false);
        assertEquals("Expected Category: 'Food'",ascendingCategoryList.get(0).getName(), "Food");
        assertEquals("Expected Category: 'Grocery'",ascendingCategoryList.get(1).getName(), "Grocery");
        assertEquals("Expected Category: 'Hang out'",ascendingCategoryList.get(2).getName(), "Hang out");
    }

    @Test
    public void testSortByPercent() {
        //tests categories are sorted properly based on percent attribute, both ascending + descending
        ArrayList<MainCategory> descendingCategoryList = reportManager.sortByPercent(1,true);
        assertEquals("Expected Category: 'Hang out'",descendingCategoryList.get(0).getName(), "Hang out");
        assertEquals("Expected Category: 'Grocery'",descendingCategoryList.get(1).getName(), "Grocery");
        assertEquals("Expected Category: 'Food'",descendingCategoryList.get(2).getName(), "Food");

        ArrayList<MainCategory> ascendingCategoryList = reportManager.sortByPercent(1,false);
        assertEquals("Expected Category: 'Food'",ascendingCategoryList.get(0).getName(), "Food");
        assertEquals("Expected Category: 'Grocery'",ascendingCategoryList.get(1).getName(), "Grocery");
        assertEquals("Expected Category: 'Hang out'",ascendingCategoryList.get(2).getName(), "Hang out");
    }

    @Test
    public void testSortByAverage() {
        //tests categories are sorted properly based on average attribute, both ascending + descending
        ArrayList<MainCategory> descendingCategoryList = reportManager.sortByAverage(1,true);
        assertEquals("Expected Category: 'Hang out'",descendingCategoryList.get(0).getName(), "Hang out");
        assertEquals("Expected Category: 'Grocery'",descendingCategoryList.get(1).getName(), "Grocery");
        assertEquals("Expected Category: 'Food'",descendingCategoryList.get(2).getName(), "Food");

        ArrayList<MainCategory> ascendingCategoryList = reportManager.sortByAverage(1,false);
        assertEquals("Expected Category: 'Food'",ascendingCategoryList.get(0).getName(), "Food");
        assertEquals("Expected Category: 'Grocery'",ascendingCategoryList.get(1).getName(), "Grocery");
        assertEquals("Expected Category: 'Hang out'",ascendingCategoryList.get(2).getName(), "Hang out");
    }

     */

}
