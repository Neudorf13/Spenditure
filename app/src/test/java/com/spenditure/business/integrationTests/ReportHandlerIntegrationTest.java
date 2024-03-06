package com.spenditure.business.integrationTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.spenditure.application.Services;
import com.spenditure.database.CategoryPersistence;
import com.spenditure.logic.ReportManager;
import com.spenditure.logic.UserManager;
import com.spenditure.object.CategoryStatistics;
import com.spenditure.object.DateTime;
import com.spenditure.object.IReport;
import com.spenditure.object.MainCategory;
import com.spenditure.utils.TestUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReportHandlerIntegrationTest {

    private ReportManager reportManager;
    private UserManager userManager;
    private File tempDB;

    @Before
    public void setup() throws IOException {

        this.tempDB = TestUtils.copyDB();
        this.reportManager = new ReportManager(false);
        userManager = new UserManager(false);
        userManager.login("Me", "123");

        assertNotNull(this.reportManager);
    }

    @After
    public void teardown() {
        userManager.logout();
        reportManager = null;
        tempDB = null;
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
//    @Test
//    public void testAverageForCategory() {
//        //tests each categories average value against expected values
//        System.out.println("before");
//        double testCategory1 = reportManager.getAverageForCategory(1);
//        System.out.println("after");
//        double testCategory2 = reportManager.getAverageForCategory(2);
//        double testCategory3 = reportManager.getAverageForCategory(3);
//
//        assertEquals("Expected transaction average for Category 1 to be approximately 110.19",testCategory1,110.19,0.1);
//        //assertEquals("Expected transaction average for Category 1 to be approximately 110.19",testCategory1,110.19,0.1);
//        //assertEquals("Expected transaction average for Category 2 to be approximately 104.19",testCategory2,104.19,0.1);
//        //assertEquals("Expected transaction average for Category 3 to be approximately 229.41",testCategory3,229.41,0.1);
//    }

//    @Test
//    public void testCount() {
//    //    int test1 = reportManager.countAllCategories(1);
//
//     //   assertEquals(test1,4);
//    }

    @Test
    public void testCategoryTable() {
        CategoryPersistence dataAccessCategory = Services.getCategoryPersistence(false);

        List<MainCategory> categories = dataAccessCategory.getAllCategory(1);

        int expected = 3;

        for(int i=0; i<categories.size(); i++) {
            if(categories.get(i).getName().equalsIgnoreCase("bills"))
                expected = 4;
        }

        assertEquals(expected, categories.size());
    }


    @Test
    public void testReportOnLastYear() {

        IReport report = reportManager.reportBackOneYear(1, new DateTime(2024, 03, 04));

        assertEquals("13 transactions since the beginning of 2023", 13, report.getNumTrans());
        assertEquals("Average is 171.38", 171.38, report.getAvgTransSize(), 0.1);
        assertEquals("Standard deviation is 120.2", 120.2, report.getStdDev(), 0.1);

        List<CategoryStatistics> list = report.getCategoryStatisticsList();

        CategoryStatistics categoryStatistics = list.get(0);
        assertEquals("Category 1 should be first", 1, categoryStatistics.getCategory().getCategoryID());
        assertEquals("Total of items should be 440.75", 440.75, categoryStatistics.getTotal(), 0.1);
        assertEquals("Average should be 110.12", 110.12, categoryStatistics.getAverage(), 0.1);
        assertEquals("19.8% of total amount", 19.8, categoryStatistics.getPercent(), 0.1);

        categoryStatistics = list.get(1);
        assertEquals("Category 2 should be next", 2, categoryStatistics.getCategory().getCategoryID());
        assertEquals("Total of items should be 410.75", 410.75, categoryStatistics.getTotal(), 0.1);
        assertEquals("Average should be 136.92", 136.92, categoryStatistics.getAverage(), 0.1);
        assertEquals("18.4% of total amount", 18.4, categoryStatistics.getPercent(), 0.1);

        categoryStatistics = list.get(2);
        assertEquals("Category 3 should be last", 3, categoryStatistics.getCategory().getCategoryID());
        assertEquals("Total of items should be 1,376.45", 1376.45, categoryStatistics.getTotal(), 0.1);
        assertEquals("Average should be 229.41", 229.41, categoryStatistics.getAverage(), 0.1);
        assertEquals("61.8% of total amount", 61.8, categoryStatistics.getPercent(), 0.1);
    }


    //Tests: reportOnUserProvidedDates
    @Test
    public void testReportOnUserProvidedDates() {

        DateTime start = new DateTime(2023, 9, 5);
        DateTime end = new DateTime(2024, 3, 4);

        IReport report = reportManager.reportOnUserProvidedDates(1, start, end);

        assertEquals("Should only be 8 reports between Sept. 5, 2023 and Mar. 1, 2024", 8, report.getNumTrans());
        assertEquals("Average of all transactions should be 165.8", 165.8, report.getAvgTransSize(), 0.1);
        assertEquals("Standard deviation should be 138.5", 138.5, report.getStdDev(), 0.1);
        assertEquals("59.3% of all transactions in db", 59.3, report.getPercent(), 0.1);

        List<CategoryStatistics> list = report.getCategoryStatisticsList();

        CategoryStatistics categoryStatistics = list.get(0);

        assertEquals("Category 1 should be first", 1, categoryStatistics.getCategory().getCategoryID());
        assertEquals("Total of 2 items should be 240.5", 240.5, categoryStatistics.getTotal(), 0.1);
        assertEquals("Average should be 120.25", 120.25, categoryStatistics.getAverage(), 0.1);
        assertEquals("18.1% of total amount", 18.1, categoryStatistics.getPercent(), 0.1);

        categoryStatistics = list.get(1);
        assertEquals("Category 2 should be next", 2, categoryStatistics.getCategory().getCategoryID());
        assertEquals("Total of 2 items should be 260", 260, categoryStatistics.getTotal(), 0.1);
        assertEquals("Average should be 130", 130, categoryStatistics.getAverage(), 0.1);
        assertEquals("19.6% of total amount", 19.6, categoryStatistics.getPercent(), 0.1);

        categoryStatistics = list.get(2);
        assertEquals("Category 3 should be last", 3, categoryStatistics.getCategory().getCategoryID());
        assertEquals("Total of 4 items should be 825.95", 825.95, categoryStatistics.getTotal(), 0.1);
        assertEquals("Average should be 206.49", 206.49, categoryStatistics.getAverage(), 0.1);
        assertEquals("62.3% of total amount", 62.3, categoryStatistics.getPercent(), 0.1);

    }

    //Tests: reportOnLastMonthByWeek
    @Test
    public void testReportOnLastMonthByWeek() {

        ArrayList<IReport> reports = reportManager.reportBackOneMonthByWeek(1, new DateTime(2023, 10, 06));

        assertEquals("There should be 4 reports, for 4 weeks in a month", 4, reports.size());

        IReport week = reports.get(0);
        assertEquals("There should only be 2 transactions", 2, week.getNumTrans());
        assertEquals("The average is 175 for those two", 175, week.getAvgTransSize(), 0.1);

        week = reports.get(1);
        assertEquals("There is only 1 transaction for this week", 1, week.getNumTrans());
        assertEquals("It was $75", 75, week.getAvgTransSize(), 0.1);

        week = reports.get(2);
        assertEquals("There are 2 here since all transactions are at 00H00M", 2, week.getNumTrans());
        assertEquals("Average between these 2 is 75.25", 75.25, week.getAvgTransSize(), 0.1);

        week = reports.get(3);
        assertEquals("There are 2 here", 2, week.getNumTrans());
        assertEquals("Average was 145.25", 145.25, week.getAvgTransSize(), 0.1);
    }

    //Tests: reportOnLastYearByMonth
    @Test
    public void testReportOnLastYearByMonth() {

        ArrayList<IReport> reports = reportManager.reportBackOnLastYearByMonth(1, new DateTime(2024, 03, 04));

        assertEquals("There should be 12 reports, one per month", 12, reports.size());

        IReport month = reports.get(4);
        CategoryStatistics categoryStatistics = month.getCategoryStatisticsList().get(2);

        assertEquals("Only 1 transaction should be present", 1, month.getNumTrans());
        assertEquals("Amount was 250.50", 250.50, categoryStatistics.getTotal(), 0.1);

        month = reports.get(8);
        categoryStatistics = month.getCategoryStatisticsList().get(2);
        assertEquals("6 transactions in Sept. 2023", 6, month.getNumTrans());
        assertEquals("Total for cat. 3 transactions is 325", 325, categoryStatistics.getTotal(), 0.1);

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
//    @Test
//    public void testSortByAverage() {
//        System.out.println("test?");
//        //tests categories are sorted properly based on average attribute, both ascending + descending
//        ArrayList<MainCategory> descendingCategoryList = reportManager.sortByAverage(1,true);
//        System.out.println("probably not eh");
//        assertEquals("Expected Category: 'Hang out'",descendingCategoryList.get(0).getName(), "Hang out");
//        assertEquals("Expected Category: 'Grocery'",descendingCategoryList.get(1).getName(), "Grocery");
//        assertEquals("Expected Category: 'Food'",descendingCategoryList.get(2).getName(), "Food");
//
//        ArrayList<MainCategory> ascendingCategoryList = reportManager.sortByAverage(1,false);
//        assertEquals("Expected Category: 'Food'",ascendingCategoryList.get(0).getName(), "Food");
//        assertEquals("Expected Category: 'Grocery'",ascendingCategoryList.get(1).getName(), "Grocery");
//        assertEquals("Expected Category: 'Hang out'",ascendingCategoryList.get(2).getName(), "Hang out");
//    }

    @Test
    public void dummy() {
        System.out.println("nothing to see here folks");
    }

//    @Test
//    public void testFakeSQL() {
//        reportManager.callShitMethod();
//    }
}
