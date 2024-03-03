/**
 * GeneralReportHandler
 *
 * COMP3350 SECTION A02
 *
 * @author JR,
 * @date Mar 2
 *
 * PURPOSE:
 *  Handles all reporting related to statistics that are not dependant on a time frame.
 *  Namely: summnation statistics for categories, summnation statistics for all transactions, and sorting categories by these statistics
 *
 **/


package com.spenditure.logic;

import com.spenditure.application.Services;
import com.spenditure.database.CategoryPersistence;
import com.spenditure.database.TransactionPersistence;
import com.spenditure.database.stub.TransactionStub;
import com.spenditure.logic.exceptions.InvalidTransactionException;
import com.spenditure.object.CategoryReport;
import com.spenditure.object.CategoryStatistics;
import com.spenditure.object.ICategoryReport;
import com.spenditure.object.IDateTime;
import com.spenditure.object.IMainCategory;
import com.spenditure.object.MainCategory;
import com.spenditure.object.Transaction;
import com.spenditure.logic.exceptions.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GeneralReportHandler implements IGeneralReportHandler{
    private TransactionPersistence dataAccessTransaction;
    private CategoryPersistence dataAccessCategory;

    /**
     * constructor
     *
     * initializes needed databases
     * @param boolean getStubDB - if true then we use stub not sql
     * @returns NA
     */
    public GeneralReportHandler(boolean getStubDB) {
        this.dataAccessTransaction = Services.getTransactionPersistence(getStubDB);
        this.dataAccessCategory = Services.getCategoryPersistence(getStubDB);
    }


    /**
     * numTransactions
     *
     * Returns the number of transactions in a particular category
     * @param int userID - userID of person logged in
     * @param int categoryID - categoryID of category to be accessed, if negative then it returns number of all transactions for this user
     * @returns int - number of transactions
     */
    public int numTransactions(int userID, int categoryID) throws InvalidLogInException
    {
        if(userID == -1 )
            throw new InvalidLogInException();

        // if less than 0 then return num of transactions for this user
        if(categoryID < 0)
        {
            return dataAccessTransaction.getAllTransactionsForUser(userID).size();
        }
        else
        {
            return dataAccessTransaction.getTransactionsByCategoryID(categoryID).size();
        }

    }

    /**
     * totalSpending
     *
     * Returns the total spending in a particular category
     * @param int userID - userID of person logged in
     * @param int categoryID - categoryID of category to be accessed, if negative then it returns total spending for all transactions for this user
     * @returns double - total spending
     */
    public double totalSpending(int userID, int categoryID) throws InvalidLogInException
    {
        List<Transaction> allTransactions;

        if(userID == -1 )
            throw new InvalidLogInException();

        double total = 0;


        if(categoryID < 0)
        {
            allTransactions = dataAccessTransaction.getAllTransactionsForUser(userID);
        }
        else
        {
            allTransactions = dataAccessTransaction.getTransactionsByCategoryID(categoryID);
        }

        // calculate total
        for(int i = 0; i < allTransactions.size(); i++)
        {
            total += allTransactions.get(i).getAmount();
        }

        return total;

    }

    /**
     * averageSpending
     *
     * Returns the average spending in a particular category
     * @param int userID - userID of person logged in
     * @param int categoryID - categoryID of category to be accessed, if negative then it returns average spending for all transactions for this user
     * @returns double - average spending
     */
    public double averageSpending(int userID, int categoryID) throws InvalidLogInException
    {

        double average;

        if(userID == -1 )
            throw new InvalidLogInException();

        // to avoid dividing by 0 check if there are no transactions
        if(numTransactions(userID, categoryID) == 0)
        {
            average = 0;
        }
        else
        {
            average = totalSpending(userID, categoryID) / numTransactions(userID, categoryID);
        }

        return average;

    }

    /**
     * getCategoryReport
     *
     * Returns a category report object with statistics about the selected category
     * @param int userID - userID of person logged in
     * @param int categoryID - categoryID of category to be reprted on
     * @returns ICategoryReport - report object
     */
    public ICategoryReport getCategoryReport(int userID, int categoryID) throws InvalidLogInException, InvalidCategoryException
    {
        double totalSpending;
        int numTransactions;
        double average;
        double percentage;

        if(userID == -1 )
            throw new InvalidLogInException();

        if(categoryID < 0)
            throw new InvalidCategoryException("That category does not exist");

        totalSpending = totalSpending(userID, categoryID);
        numTransactions = numTransactions(userID, categoryID);
        average = averageSpending(userID, categoryID);
        percentage = (totalSpending(userID, categoryID)/totalSpending(userID, -1)) * 100;

        return new CategoryReport(dataAccessCategory.getCategoryByID(categoryID), totalSpending, numTransactions, average, percentage);
    }



    /**
     * sortByTotal
     *
     * Returns a list of all categories for a user sorted by total number of transactions in each
     * @param int userID - userID of person logged in
     * @param boolean descending - if true then sorted in descending order, else ascending
     * @returns ArrayList<IMainCategory> - sorted array list
     */
    public ArrayList<IMainCategory> sortByTotal(int userID, boolean descending) throws InvalidLogInException
    {
        ArrayList<ICategoryReport> categoryReportList = new ArrayList<ICategoryReport>();
        List<MainCategory> mainCategoryList;
        ArrayList<IMainCategory> sortedCategories = new ArrayList<IMainCategory>();

        // ensure someone is logged in
        if(userID == -1 )
            throw new InvalidLogInException();

        // get all categories for a user
        mainCategoryList = dataAccessCategory.getAllCategory(userID);

        // generate report for each category user has
        for(MainCategory node : mainCategoryList)
        {
            categoryReportList.add(getCategoryReport(userID, node.getCategoryID()));
        }

        // sort categories by total
        Collections.sort(categoryReportList, (node1, node2) -> {
            // Compare based on the 'total' attribute
            if(descending) {
                return Double.compare(node2.getTotalSpending(), node1.getTotalSpending());
            }
            else {
                return Double.compare(node1.getTotalSpending(), node2.getTotalSpending());
            }

        });

        // take out category objects from sorted list of category reports
        for (ICategoryReport node : categoryReportList) {
            sortedCategories.add(node.getCategory());
        }

        return sortedCategories;
    }

    /**
     * sortByPercent
     *
     * Returns a list of all categories for a user sorted by percentage of total amount spend
     * @param int userID - userID of person logged in
     * @param boolean descending - if true then sorted in descending order, else ascending
     * @returns ArrayList<IMainCategory> - sorted array list
     */
    public ArrayList<IMainCategory> sortByPercent(int userID, boolean descending) throws InvalidLogInException
    {
        ArrayList<ICategoryReport> categoryReportList = new ArrayList<ICategoryReport>();
        List<MainCategory> mainCategoryList;
        ArrayList<IMainCategory> sortedCategories = new ArrayList<IMainCategory>();

        // ensure someone is logged in
        if(userID == -1 )
            throw new InvalidLogInException();

        // get all categories for a user
        mainCategoryList = dataAccessCategory.getAllCategory(userID);

        // generate report for each category user has
        for(MainCategory node : mainCategoryList)
        {
            categoryReportList.add(getCategoryReport(userID, node.getCategoryID()));
        }

        // sort categories by percentage
        Collections.sort(categoryReportList, (node1, node2) -> {
            // Compare based on the 'percentage' attribute
            if(descending) {
                return Double.compare(node2.getPercentage(), node1.getPercentage());
            }
            else {
                return Double.compare(node1.getPercentage(), node2.getPercentage());
            }
            // =D, you found the secret smiley face, have a nice day!
        });

        // take out category objects from sorted list of category reports
        for (ICategoryReport node : categoryReportList) {
            sortedCategories.add(node.getCategory());
        }

        return sortedCategories;
    }

    /**
     * sortByAverage
     *
     * Returns a list of all categories for a user sorted by average amount spent in that category
     * @param int userID - userID of person logged in
     * @param boolean descending - if true then sorted in descending order, else ascending
     * @returns ArrayList<IMainCategory> - sorted array list
     */
    public ArrayList<IMainCategory> sortByAverage(int userID, boolean descending) throws InvalidLogInException
    {
        ArrayList<ICategoryReport> categoryReportList = new ArrayList<ICategoryReport>();
        List<MainCategory> mainCategoryList;
        ArrayList<IMainCategory> sortedCategories = new ArrayList<IMainCategory>();

        // ensure someone is logged in
        if(userID == -1 )
            throw new InvalidLogInException();

        // get all categories for a user
        mainCategoryList = dataAccessCategory.getAllCategory(userID);


        // generate report for each category user has
        for(MainCategory node : mainCategoryList)
        {
            categoryReportList.add(getCategoryReport(userID, node.getCategoryID()));
        }

        // sort categories by average
        Collections.sort(categoryReportList, (node1, node2) -> {
            // Compare based on the 'average' attribute
            if(descending) {
                return Double.compare(node2.getAverageTransactions(), node1.getAverageTransactions());
            }
            else {
                return Double.compare(node1.getTotalSpending(), node2.getTotalSpending());
            }

        });

        // take out category objects from sorted list of category reports
        for (ICategoryReport node : categoryReportList) {
            sortedCategories.add(node.getCategory());
        }

        return sortedCategories;
    }





}
