package com.spenditure.business;

//import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.*;

import com.spenditure.database.stub.CategoryStub;
import com.spenditure.database.stub.TransactionStub;
import com.spenditure.logic.CategoryHandler;
import com.spenditure.logic.ReportManager;
import com.spenditure.object.Category;
import com.spenditure.object.Transaction;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class ReportManagerTest {

    private ReportManager reportManager;

    @Before
    public void setup() {
        //CategoryStub.cleanup();
        this.reportManager = new ReportManager(true);

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

    @Test
    public void testThis() {
        //ArrayList<Transaction> test = testStub.getTransactionByCategoryID(1);
    }
}
