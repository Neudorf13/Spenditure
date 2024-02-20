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


public class ReportHandler implements IReportHandler{

    //instance vars
    private TransactionPersistence dataAccessTransaction;
    private CategoryPersistence dataAccessCategory;

    public ReportHandler(boolean getStubDB) {
        this.dataAccessTransaction = Services.getTransactionPersistence(getStubDB);
        this.dataAccessCategory = Services.getCategoryPersistence(getStubDB);
    }

    public IReport reportOnLastYear()
    {
        // manually set to 1 year ago from current date
        IDateTime yearStart = new DateTime();
        // manually set to current date (probably an API for getting this info)
        IDateTime yearEnd = new DateTime();

        double avgTransSize = getAvgTransSize(yearStart, yearEnd);
        int numTrans = getNumTrans(yearStart, yearEnd);
        double getStdDev = getStdDev(yearStart, yearEnd);

        ArrayList<CategoryStatistics> listOfCategoryStatisticss = buildCategoryList(yearStart, yearEnd);

        return new Report(avgTransSize, numTrans, getStdDev, listOfCategoryStatisticss);

    }

    public ArrayList<IReport> reportOnLastYearByMonth()
    {

    }

    public ArrayList<IReport> reportOnLastMonthByWeek() {

        DateTime[] weekDates = new DateTime[5];
        ArrayList<IReport> result = new ArrayList<IReport>();

        weekDates[0] = new DateTime(); //current day

        int weeks = 7;

        for( int i = 1; i < weekDates.length; i ++ ) {

            DateTime week = weekDates[i - 1].copy();
            week.adjust(0, 0, -i * weeks, 0, 0);

            weekDates[i] = week;

            result.add(setupReport(weekDates[i - 1], weekDates[i]));

        }

        return result;

    }

    public IReport reportOnUserProvidedDates(IDateTime start, IDateTime end) {
//
//        double avgTransactionSize = getAvgTransSize(start, end);
//        int numTransactions = getNumTrans(start, end);
//        double standardDeviation = getStdDev(start, end);
//
//        ArrayList<CategoryStatistics> categoryStatistics = buildCategoryList(start, end);
//
//        return new Report( avgTransactionSize, numTransactions, standardDeviation, categoryStatistics );

        return setupReport(start, end);

    }

    public IReport setupReport(IDateTime start, IDateTime end) {

        double averageTransactionSize = getAverageTransactionSize(start, end);
        int numTransactions = countAllTransactions(start, end);
        double standardDeviation = getStandardDeviation(start, end);

        ArrayList<CategoryStatistics> categoryStatistics = buildCategoryList(start, end);

        return new Report( averageTransactionSize, numTransactions, standardDeviation, categoryStatistics );

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
//        double[] calculations = new double[transactions.size()];
        double sum = 0.00;
        double variance = 0.00;
        double standardDeviation = 0.00;

        for( int i = 0; i < transactions.size(); i ++ ) {

            double current = transactions.get(i).getAmount();

            current = abs(current - mean);
            current *= current;

//            calculations[i] = current;
            sum += current;
        }

        variance = sum / (transactions.size() - 1);

        standardDeviation = sqrt(variance);

        return standardDeviation;

    }

    //return count of total transactions
    private int countAllTransactions(IDateTime startDate, IDateTime endDate) {
//        List<Transaction> transactions = dataAccessTransaction.getAllTransactions();
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
//        ArrayList<Transaction> categoryTransactions =  dataAccessTransaction.getTransactionByCategoryID(categoryID);
        ArrayList<Transaction> transactionsInTimeframe = dataAccessTransaction.getTransactionsByDateTime(startDate, endDate);
        double total = 0.0;

//        for(Transaction element : categoryTransactions) {
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

    //returns list of categories sorted by total amount
    private ArrayList<MainCategory> sortByTotal(boolean descending, IDateTime startDate, IDateTime endDate) {
        ArrayList<CategoryStatistics> categoryList = buildCategoryList(startDate, endDate);

        Collections.sort(categoryList, (node1, node2) -> {
            // Compare based on the 'total' attribute
            if(descending) {
                return Double.compare(node2.getTotal(), node1.getTotal());
            }
            else {
                return Double.compare(node1.getTotal(), node2.getTotal());
            }

        });

        // Create a new ArrayList to store sorted categories
        ArrayList<MainCategory> sortedCategories = new ArrayList<>();

        for (CategoryStatistics node : categoryList) {
            sortedCategories.add(node.getCategory());
        }

        return sortedCategories;
    }

    //returns list of categories sorted by percent
    private ArrayList<MainCategory> sortByPercent(boolean descending, IDateTime startDate, IDateTime endDate) {
        ArrayList<CategoryStatistics> categoryList = buildCategoryList(startDate, endDate);

        Collections.sort(categoryList, (node1, node2) -> {
            // Compare based on the 'percent' attribute
            if(descending) {
                return Double.compare(node2.getPercent(), node1.getPercent());
            }
            else {
                return Double.compare(node1.getPercent(), node2.getPercent());
            }

        });

        // Create a new ArrayList to store sorted categories
        ArrayList<MainCategory> sortedCategories = new ArrayList<>();

        for (CategoryStatistics node : categoryList) {
            sortedCategories.add(node.getCategory());
        }

        return sortedCategories;
    }

    //returns list of categories sorted by average amount
    private ArrayList<MainCategory> sortByAverage(boolean descending, IDateTime startDate, IDateTime endDate) {
        ArrayList<CategoryStatistics> categoryList = buildCategoryList(startDate, endDate);

        Collections.sort(categoryList, (node1, node2) -> {
            // Compare based on the 'average' attribute
            if(descending) {
                return Double.compare(node2.getAverage(), node1.getAverage());
            }
            else {
                return Double.compare(node1.getAverage(), node2.getAverage());
            }

        });

        // Create a new ArrayList to store sorted categories
        ArrayList<MainCategory> sortedCategories = new ArrayList<>();

        for (CategoryStatistics node : categoryList) {
            sortedCategories.add(node.getCategory());
        }

        return sortedCategories;
    }




}
