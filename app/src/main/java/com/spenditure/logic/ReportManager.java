package com.spenditure.logic;

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
import java.util.Collections;
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


public class ReportManager implements IReportManager {

    //instance vars
    private TransactionPersistence dataAccessTransaction;
    private CategoryPersistence dataAccessCategory;

    public ReportManager(boolean getStubDB) {
        this.dataAccessTransaction = Services.getTransactionPersistence(getStubDB);
        this.dataAccessCategory = Services.getCategoryPersistence(getStubDB);
    }

    public IReport reportOnLastYear(int userID)
    {
        // manually set to current date (probably an API for getting this info)
        IDateTime yearEnd = getCurrentDate();
        // manually set to 1 year ago from current date
        IDateTime yearStart = new DateTime(yearEnd.getYear()-1, yearEnd.getMonth());


        double avgTransSize = getAverageTransactionSizeByDate(yearStart, yearEnd);
        int numTrans = countAllTransactionsByDate(yearStart, yearEnd);
        double stdDev = getStandardDeviationByDate(yearStart, yearEnd);

        ArrayList<CategoryStatistics> listOfCategoryStatistics = buildCategoryList(yearStart, yearEnd, userID);

        return new Report(avgTransSize, numTrans, stdDev, listOfCategoryStatistics);

    }

    public ArrayList<IReport> reportOnLastYearByMonth(int userID)
    {
        ArrayList<IReport> monthReports = new ArrayList<IReport>();
        DateTime today = getCurrentDate();

        for(int i = 0; i < 12; i++)
        {
            monthReports.add(reportOnUserProvidedDates(new DateTime(today.getYear()-1, i),
                    new DateTime(today.getYear()-1, i), userID));
        }

        return monthReports;
    }

    public ArrayList<IReport> reportOnLastMonthByWeek(int userID) {

        DateTime[] weekDates = new DateTime[5];
        ArrayList<IReport> result = new ArrayList<IReport>();

        weekDates[0] = getCurrentDate(); //current day

        int weeks = 7;

        for( int i = 1; i < weekDates.length; i ++ ) {

            DateTime week = weekDates[i - 1].copy();
            week.adjust(0, 0, -i * weeks, 0, 0, 0);

            weekDates[i] = week;

            result.add(reportOnUserProvidedDates(weekDates[i - 1], weekDates[i], userID));
        }

        return result;
    }

    public IReport reportOnUserProvidedDates(IDateTime start, IDateTime end, int userID) {

        validateDateTime(start);
        validateDateTime(end);

        double averageTransactionSize = getAverageTransactionSizeByDate(start, end);
        int numTransactions = countAllTransactionsByDate(start, end);
        double standardDeviation = getStandardDeviationByDate(start, end);

        ArrayList<CategoryStatistics> categoryStatistics = buildCategoryList(start, end, userID);

        return new Report( averageTransactionSize, numTransactions, standardDeviation, categoryStatistics );
    }

    private DateTime getCurrentDate()
    {
        LocalDate javaDateObject = LocalDate.now(); // Create a date object
        return new DateTime(javaDateObject.getYear(), javaDateObject.getMonthValue());
    }

    private double getAverageTransactionSizeByDate(IDateTime startDate, IDateTime endDate) {

        List<Transaction> transactions = dataAccessTransaction.getTransactionsByDateTime(startDate, endDate);

        double total = 0.00;

        for( int i = 0; i < transactions.size(); i ++ ) {

            total += transactions.get(i).getAmount();

        }

        if(transactions.size() > 0)
            return total / transactions.size();
        else
            return 0;

    }

    private double getStandardDeviationByDate(IDateTime startDate, IDateTime endDate) {

        List<Transaction> transactions = dataAccessTransaction.getTransactionsByDateTime(startDate, endDate);

        double mean = getAverageTransactionSizeByDate(startDate, endDate);
        double sum = 0.00;
        double variance = 0.00;
        double standardDeviation = 0.00;

        for( int i = 0; i < transactions.size(); i ++ ) {

            double current = transactions.get(i).getAmount();

            current = abs(current - mean);
            current *= current;

            sum += current;
        }

        variance = sum / (transactions.size() - 1);

        standardDeviation = sqrt(variance);

        if(transactions.size() > 0)
            return standardDeviation;
        else
            return 0;

    }

    //return count of total transactions
    private int countAllTransactionsByDate(IDateTime startDate, IDateTime endDate) {
        List<Transaction> transactions = dataAccessTransaction.getTransactionsByDateTime(startDate, endDate);
        return transactions.size();
    }

    //return count of total categories
    public int countAllCategories(int userID) {
        List<MainCategory> categories = dataAccessCategory.getAllCategory(userID);
        return categories.size();
    }

    //return count of transactions with specific category
    private int countTransactionsByCategoryByDate(int categoryID, IDateTime startDate, IDateTime endDate) {
        ArrayList<Transaction> categoryTransactions = new ArrayList<Transaction>();// = dataAccessTransaction.getTransactionByCategoryID(categoryID);
        ArrayList<Transaction> transactionsInTimeframe = dataAccessTransaction.getTransactionsByDateTime(startDate, endDate);

        for(int i = 0; i < transactionsInTimeframe.size(); i ++) {

            if( transactionsInTimeframe.get(i).getCategoryID() == categoryID )
                categoryTransactions.add(transactionsInTimeframe.get(i));

        }

        return categoryTransactions.size();
    }

    //return sum of total amount for all transactions
    private double getTotalForAllTransactionsByDate(IDateTime startDate, IDateTime endDate) {
        List<Transaction> transactions = dataAccessTransaction.getTransactionsByDateTime(startDate, endDate);
        double total = 0.0;

        for(Transaction element : transactions) {
            total += element.getAmount();
        }

        return total;
    }

    //return sum of total amount for specified category
    private double getTotalForCategoryByDate(int categoryID, IDateTime startDate, IDateTime endDate) {
        ArrayList<Transaction> transactionsInTimeframe = dataAccessTransaction.getTransactionsByDateTime(startDate, endDate);
        double total = 0.0;

        for( int i = 0; i < transactionsInTimeframe.size(); i++ ) {

            Transaction element = transactionsInTimeframe.get(i);

            if(element.getCategoryID() == categoryID)
                //for each CT, access its transaction, add the transaction value to total
                total += element.getAmount();

        }

        return total;
    }

    //return average transaction amount for a given category
    private double getAverageForCategoryByDate(int categoryID, IDateTime startDate, IDateTime endDate) {
        double total = getTotalForCategoryByDate(categoryID, startDate, endDate);
        int count = countTransactionsByCategoryByDate(categoryID, startDate, endDate);

        if(count > 0)
            return (total / count);
        else
            return 0;
    }

    //return % of total transaction sum for given category
    private double getPercentForCategoryByDate(int categoryID, IDateTime startDate, IDateTime endDate) {

        double totalAllTransactions = getTotalForAllTransactionsByDate(startDate, endDate);
        double totalForCategory = getTotalForCategoryByDate(categoryID, startDate, endDate);

        if(totalAllTransactions > 0)
            return ((totalForCategory / totalAllTransactions) * 100);
        else
            return 0;
    }

    //sorting methods

    //returns list of CategoryStatisticss (one for each category)
    private ArrayList<CategoryStatistics> buildCategoryList(IDateTime startDate, IDateTime endDate, int userID) {
        ArrayList<CategoryStatistics> categoryList = new ArrayList<>();
        int numCategories = countAllCategories(userID);

        for(int i = 1; i < numCategories+1; i++) {
            //for each Category calculate -> total, average, %
            MainCategory category = dataAccessCategory.getCategoryByID(i);
            double total = getTotalForCategoryByDate(i, startDate, endDate);
            double average = getAverageForCategoryByDate(i, startDate, endDate);
            double percent = getPercentForCategoryByDate(i, startDate, endDate);

            CategoryStatistics node = new CategoryStatistics(category,total,average,percent);
            categoryList.add(node);
        }

        return categoryList;
    }


}
