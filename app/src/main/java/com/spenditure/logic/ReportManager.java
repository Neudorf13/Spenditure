package com.spenditure.logic;

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

    public IReport reportOnLastYear()
    {
        // manually set to current date (probably an API for getting this info)
        IDateTime yearEnd = getCurrentDate();
        // manually set to 1 year ago from current date
        IDateTime yearStart = new DateTime(yearEnd.getYear()-1, yearEnd.getMonth());


        double avgTransSize = getAverageTransactionSize(yearStart, yearEnd);
        int numTrans = countAllTransactions(yearStart, yearEnd);
        double stdDev = getStandardDeviation(yearStart, yearEnd);

        ArrayList<CategoryStatistics> listOfCategoryStatistics = buildCategoryList(yearStart, yearEnd);

        return new Report(avgTransSize, numTrans, stdDev, listOfCategoryStatistics);

    }

    public ArrayList<IReport> reportOnLastYearByMonth()
    {
        ArrayList<IReport> monthReports = new ArrayList<IReport>();
        DateTime today = getCurrentDate();

        for(int i = 0; i < 12; i++)
        {
            monthReports.add(reportOnUserProvidedDates(new DateTime(today.getYear()-1, i),
                    new DateTime(today.getYear()-1, i)));
        }

        return monthReports;
    }

    public ArrayList<IReport> reportOnLastMonthByWeek() {

        DateTime[] weekDates = new DateTime[5];
        ArrayList<IReport> result = new ArrayList<IReport>();

        weekDates[0] = getCurrentDate(); //current day

        int weeks = 7;

        for( int i = 1; i < weekDates.length; i ++ ) {

            DateTime week = weekDates[i - 1].copy();
            week.adjust(0, 0, -i * weeks, 0, 0);

            weekDates[i] = week;

            result.add(reportOnUserProvidedDates(weekDates[i - 1], weekDates[i]));
        }

        return result;
    }

    public IReport reportOnUserProvidedDates(IDateTime start, IDateTime end) {

        double averageTransactionSize = getAverageTransactionSize(start, end);
        int numTransactions = countAllTransactions(start, end);
        double standardDeviation = getStandardDeviation(start, end);

        ArrayList<CategoryStatistics> categoryStatistics = buildCategoryList(start, end);

        return new Report( averageTransactionSize, numTransactions, standardDeviation, categoryStatistics );
    }

    private DateTime getCurrentDate()
    {
        LocalDate javaDateObject = LocalDate.now(); // Create a date object
        return new DateTime(javaDateObject.getYear(), javaDateObject.getMonthValue());
    }

    private double getAverageTransactionSize(IDateTime startDate, IDateTime endDate) {

        List<Transaction> transactions = dataAccessTransaction.getTransactionsByDateTime(startDate, endDate);

        double total = 0.00;

        for( int i = 0; i < transactions.size(); i ++ ) {

            total += transactions.get(i).getAmount();

        }

        return total / transactions.size();

    }

    private double getStandardDeviation(IDateTime startDate, IDateTime endDate) {

        List<Transaction> transactions = dataAccessTransaction.getTransactionsByDateTime(startDate, endDate);

        double mean = getAverageTransactionSize(startDate, endDate);
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

        return standardDeviation;

    }

    //return count of total transactions
    private int countAllTransactions(IDateTime startDate, IDateTime endDate) {
        List<Transaction> transactions = dataAccessTransaction.getTransactionsByDateTime(startDate, endDate);
        return transactions.size();
    }

    //return count of total categories
    private int countAllCategories() {
        List<MainCategory> categories = dataAccessCategory.getAllCategory();
        return categories.size();
    }

    //return count of transactions with specific category
    private int countTransactionsByCategory(int categoryID, IDateTime startDate, IDateTime endDate) {
        ArrayList<Transaction> categoryTransactions = new ArrayList<Transaction>();// = dataAccessTransaction.getTransactionByCategoryID(categoryID);
        ArrayList<Transaction> transactionsInTimeframe = dataAccessTransaction.getTransactionsByDateTime(startDate, endDate);

        for(int i = 0; i < transactionsInTimeframe.size(); i ++) {

            if( transactionsInTimeframe.get(i).getCategory().getID() == categoryID )
                categoryTransactions.add(transactionsInTimeframe.get(i));

        }

        return categoryTransactions.size();
    }

    //return sum of total amount for all transactions
    private double getTotalForAllTransactions(IDateTime startDate, IDateTime endDate) {
        List<Transaction> transactions = dataAccessTransaction.getTransactionsByDateTime(startDate, endDate);
        double total = 0.0;

        for(Transaction element : transactions) {
            total += element.getAmount();
        }

        return total;
    }

    //return sum of total amount for specified category
    private double getTotalForCategory(int categoryID, IDateTime startDate, IDateTime endDate) {
        ArrayList<Transaction> transactionsInTimeframe = dataAccessTransaction.getTransactionsByDateTime(startDate, endDate);
        double total = 0.0;

        for( int i = 0; i < transactionsInTimeframe.size(); i++ ) {

            Transaction element = transactionsInTimeframe.get(i);

            if(element.getCategory().getID() == categoryID)
                //for each CT, access its transaction, add the transaction value to total
                total += element.getAmount();

        }

        return total;
    }

    //return average transaction amount for a given category
    private double getAverageForCategory(int categoryID, IDateTime startDate, IDateTime endDate) {
        double total = getTotalForCategory(categoryID, startDate, endDate);
        int count = countTransactionsByCategory(categoryID, startDate, endDate);

        return (total / count);
    }

    //return % of total transaction sum for given category
    private double getPercentForCategory(int categoryID, IDateTime startDate, IDateTime endDate) {

        double totalAllTransactions = getTotalForAllTransactions(startDate, endDate);
        double totalForCategory = getTotalForCategory(categoryID, startDate, endDate);

        return ((totalForCategory / totalAllTransactions) * 100);
    }

    //sorting methods

    //returns list of CategoryStatisticss (one for each category)
    private ArrayList<CategoryStatistics> buildCategoryList(IDateTime startDate, IDateTime endDate) {
        ArrayList<CategoryStatistics> categoryList = new ArrayList<>();
        int numCategories = countAllCategories();

        for(int i = 1; i < numCategories+1; i++) {
            //for each Category calculate -> total, average, %
            MainCategory category = dataAccessCategory.getCategoryByID(i);
            double total = getTotalForCategory(i, startDate, endDate);
            double average = getAverageForCategory(i, startDate, endDate);
            double percent = getPercentForCategory(i, startDate, endDate);

            CategoryStatistics node = new CategoryStatistics(category,total,average,percent);
            categoryList.add(node);
        }

        return categoryList;
    }





}
