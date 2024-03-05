package com.spenditure.logic;

import static com.spenditure.logic.DateTimeAdjuster.correctDateTime;
import static com.spenditure.logic.DateTimeValidator.validateDateTime;
import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

import com.spenditure.application.Services;
import com.spenditure.database.CategoryPersistence;
import com.spenditure.database.TransactionPersistence;
import com.spenditure.object.DateTime;
import com.spenditure.object.IDateTime;
import com.spenditure.object.IReport;
import com.spenditure.object.Report;
import com.spenditure.object.CategoryStatistics;
import com.spenditure.object.MainCategory;
import com.spenditure.object.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.time.*;

/**
 * ReportManager.java
 *
 * COMP3350 SECTION A02
 *
 * @author Trevor,
 * @date Feb 7, 2024
 *
 * PURPOSE:
 *  This file handles all logic calculations related to generating reports
 *
 **/


public class ReportManager {

    //Constants for time-based reporting
    private static final int DAYS_IN_WEEK = 7;
    private static final int WEEKS_IN_MONTH = 4;

    //instance vars
    private TransactionPersistence dataAccessTransaction;
    private CategoryPersistence dataAccessCategory;

    public ReportManager(boolean getStubDB) {
        this.dataAccessTransaction = Services.getTransactionPersistence(getStubDB);
        this.dataAccessCategory = Services.getCategoryPersistence(getStubDB);
    }

    /*

    reportBackOneYear

    Creates a Report starting one year before the provided date,
    ending at the provided date.

     */
    public IReport reportBackOneYear(int userID, IDateTime yearEnd) {

        validateDateTime(yearEnd);
        assert(userID >= 0);

        // manually set to 1 year ago from provided date
        IDateTime yearStart = new DateTime(yearEnd.getYear()-1, yearEnd.getMonth(), yearEnd.getDay());

        return reportOnUserProvidedDates(userID, yearStart, yearEnd);

    }

    /*

    reportBackOnLastYearByMonth

    Creates a list of 12 Reports, one for each month of the year before the current year.
    Reports are ordered from January to December.

     */
    public ArrayList<IReport> reportBackOnLastYearByMonth(int userID, DateTime today) {

        validateDateTime(today);
        assert(userID >= 0);

        ArrayList<IReport> monthReports = new ArrayList<IReport>();

        for(int i = 1; i <= 12; i++) {
            monthReports.add(reportOnUserProvidedDates(
                    userID, new DateTime(today.getYear()-1, i, 1),
                    correctDateTime( new DateTime(today.getYear()-1, i, 31)) )
            );
        }

        return monthReports;
    }

    /*

    reportBackOneMonthByWeek

    Creates a report starting 1 month before the provided date,
    ending at the provided date.

     */
    public ArrayList<IReport> reportBackOneMonthByWeek(int userID, DateTime start) {

        validateDateTime(start);
        assert(userID >= 0);

        DateTime[] weekDates = new DateTime[WEEKS_IN_MONTH + 1];
        ArrayList<IReport> result = new ArrayList<IReport>();

        weekDates[0] = start;

        for( int i = 1; i < weekDates.length; i ++ ) {

            DateTime week = weekDates[i - 1].copy();
            week.adjust(0, 0, -DAYS_IN_WEEK, 0, 0, 0);

            weekDates[i] = week;

            result.add(reportOnUserProvidedDates(userID, weekDates[i], weekDates[i - 1]));
        }

        return result;
    }

    /*

    reportOnUserProvidedDates

    Creates a report starting and ending at user-specified times.

     */
    public IReport reportOnUserProvidedDates(int userID, IDateTime start, IDateTime end) {

        validateDateTime(start);
        validateDateTime(end);
        assert(userID >= 0);

        double averageTransactionSize = getAverageTransactionSizeByDate(userID, start, end);
        int numTransactions = countAllTransactionsByDate(userID, start, end);
        double standardDeviation = getStandardDeviationByDate(userID, start, end);
        double percent = getPercentForReport(userID, start, end);

        ArrayList<CategoryStatistics> categoryStatistics = buildCategoryList(userID, start, end);

        return new Report( averageTransactionSize, numTransactions, standardDeviation, percent, categoryStatistics );
    }

    /*

    getCurrentDate

    Returns a DateTime of the format:
    (currentYear, currentMonth, currentDay, 00H, 00M, 00S)

    Especially useful with the above functions for making Reports around the current day

     */
    public static DateTime getCurrentDate() {
        LocalDate javaDateObject = LocalDate.now(); // Create a date object
        return new DateTime(javaDateObject.getYear(), javaDateObject.getMonthValue(), javaDateObject.getDayOfMonth());
    }

    // ### PRIVATE METHODS ###
    /*

    getAverageTransactionSizeByDate

    Returns the average transaction size between the two provided dates.

     */
    private double getAverageTransactionSizeByDate(int userID, IDateTime startDate, IDateTime endDate) {

        List<Transaction> transactions = dataAccessTransaction.getTransactionsByDateTime(userID, startDate, endDate);

        double total = 0.00;

        for( int i = 0; i < transactions.size(); i ++ ) {

            total += transactions.get(i).getAmount();

        }

        if(transactions.size() > 0)
            return total / transactions.size();
        else
            return 0;

    }

    /*

    getStandardDeviationByDate

    Returns the standard deviation value for transactions between the two specified dates.

     */
    private double getStandardDeviationByDate(int userID, IDateTime startDate, IDateTime endDate) {

        List<Transaction> transactions = dataAccessTransaction.getTransactionsByDateTime(userID, startDate, endDate);

        double mean = getAverageTransactionSizeByDate(userID, startDate, endDate);
        double sum = 0.00;
        double variance = 0.00;
        double standardDeviation = 0.00;

        for( int i = 0; i < transactions.size(); i ++ ) {

            double current = transactions.get(i).getAmount();

            current = abs(current - mean);
            current *= current;

            sum += current;
        }

        variance = sum / (transactions.size());

        standardDeviation = sqrt(variance);

        if(transactions.size() > 0)
            return standardDeviation;
        else
            return 0;

    }

    /*

    countAllTransactionsByDate

    Returns the number of transactions that occured betweent the given dates.

     */
    private int countAllTransactionsByDate(int userID, IDateTime startDate, IDateTime endDate) {

        List<Transaction> transactions = dataAccessTransaction.getTransactionsByDateTime(userID, startDate, endDate);

        return transactions.size();

    }

    /*

    countAllCategories

    Returns the number of categories present for the given user.

     */
    public int countAllCategories(int userID) {

        List<MainCategory> categories = dataAccessCategory.getAllCategory(userID);

        return categories.size();

    }

    /*

    countTransactionsByCategoryByDate

    Returns the number of transactions of a given category that occured between the given dates.

     */
    private int countTransactionsByCategoryByDate(int userID, int categoryID, IDateTime startDate, IDateTime endDate) {

        ArrayList<Transaction> categoryTransactions = new ArrayList<>();

        ArrayList<Transaction> transactionsInTimeframe = dataAccessTransaction.getTransactionsByDateTime(userID, startDate, endDate);

        for(int i = 0; i < transactionsInTimeframe.size(); i ++) {

            if( transactionsInTimeframe.get(i).getCategoryID() == categoryID )
                categoryTransactions.add(transactionsInTimeframe.get(i));

        }

        return categoryTransactions.size();
    }

    /*

    getTotalForAllTransactionsByDate

    Returns the total value of all the transactions that occured between the given dates.

     */
    private double getTotalForAllTransactionsByDate(int userID, IDateTime startDate, IDateTime endDate) {

        List<Transaction> transactions = dataAccessTransaction.getTransactionsByDateTime(userID, startDate, endDate);

        double total = 0.0;

        for(Transaction element : transactions) {
            total += element.getAmount();
        }

        return total;
    }

    /*

    getTotalForAllTransactions

    Returns the total value of all transactions for a given user ID, irrespective of time.

     */
    private double getTotalForAllTransactions(int userID) {

        List<Transaction> transactions = dataAccessTransaction.getAllTransactionsForUser(userID);

        double result = 0.00;

        for(int i = 0; i < transactions.size(); i ++) {

            result += transactions.get(i).getAmount();

        }

        return result;

    }

    /*

    getTotalForCategoryByDate

    Returns the total value of all transactions in a category over a given time.

     */
    private double getTotalForCategoryByDate(int userID, int categoryID, IDateTime startDate, IDateTime endDate) {

        ArrayList<Transaction> transactionsInTimeframe = dataAccessTransaction.getTransactionsByDateTime(userID, startDate, endDate);

        double total = 0.0;

        for( int i = 0; i < transactionsInTimeframe.size(); i++ ) {

            Transaction element = transactionsInTimeframe.get(i);

            if(element.getCategoryID() == categoryID)
                //for each CT, access its transaction, add the transaction value to total
                total += element.getAmount();

        }

        return total;
    }

    /*

    getAverageForCategoryByDate

    Returns the average value of each transaction in a given category for given dates.

     */
    private double getAverageForCategoryByDate(int userID, int categoryID, IDateTime startDate, IDateTime endDate) {

        double total = getTotalForCategoryByDate(userID, categoryID, startDate, endDate);

        int count = countTransactionsByCategoryByDate(userID, categoryID, startDate, endDate);

        if(count > 0)
            return (total / count);
        else
            return 0;
    }

    /*

    getPercentForCategoryByDate

    Returns the percentage of transactions in a given category out of all transactions, for given dates

     */
    private double getPercentForCategoryByDate(int userID, int categoryID, IDateTime startDate, IDateTime endDate) {

        double totalAllTransactions = getTotalForAllTransactionsByDate(userID, startDate, endDate);

        double totalForCategory = getTotalForCategoryByDate(userID, categoryID, startDate, endDate);

        if(totalAllTransactions > 0)
            return ((totalForCategory / totalAllTransactions) * 100);
        else
            return 0;
    }

    /*

    getPercentForReport

    Returns the percentage of all transactions in the report out of all transactions in total

     */
    private double getPercentForReport(int userID, IDateTime startDate, IDateTime endDate) {

        double allTotal = getTotalForAllTransactions(userID);

        double reportTotal = getTotalForAllTransactionsByDate(userID, startDate, endDate);

        return (reportTotal / allTotal) * 100;

    }

    /*

    buildCategoryList

    For each category, gets the statistics and packages them into items in a list. Returns a list
    of CategoryStatistics items which contain the stats on each category, and a link to the category
    itself.

     */
    private ArrayList<CategoryStatistics> buildCategoryList(int userID, IDateTime startDate, IDateTime endDate) {
        ArrayList<CategoryStatistics> categoryList = new ArrayList<>();
        int numCategories = countAllCategories(userID);

        for(int i = 1; i < numCategories+1; i++) {
            //for each Category calculate -> total, average, %
            MainCategory category = dataAccessCategory.getCategoryByID(i);
            double total = getTotalForCategoryByDate(userID, i, startDate, endDate);
            double average = getAverageForCategoryByDate(userID, i, startDate, endDate);
            double percent = getPercentForCategoryByDate(userID, i, startDate, endDate);

            CategoryStatistics node = new CategoryStatistics(category,total,average,percent);
            categoryList.add(node);
        }

        return categoryList;
    }

}
