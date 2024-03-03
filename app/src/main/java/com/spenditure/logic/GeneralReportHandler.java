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

    public GeneralReportHandler(boolean getStubDB) {
        this.dataAccessTransaction = Services.getTransactionPersistence(getStubDB);
        this.dataAccessCategory = Services.getCategoryPersistence(getStubDB);
    }

    public int numTransactions(int categoryID)
    {
        if(categoryID < 0)
        {
            // make some sort of exception for this case
            return dataAccessTransaction.getAllTransactionsForUser(UserManager.getUserID()).size();
        }
        else
        {
            return dataAccessTransaction.getTransactionsByCategoryID(categoryID).size();
        }

    }

    public double totalSpending(int categoryID) throws InvalidLogInException
    {
        List<Transaction> allTransactions;
        int userID;


        userID = UserManager.getUserID();

        if(userID == -1 )
            throw new InvalidLogInException("No user is logged in");

        double total = 0;

        if(categoryID < 0)
        {
            allTransactions = dataAccessTransaction.getAllTransactionsForUser(userID);
        }
        else
        {
            allTransactions = dataAccessTransaction.getTransactionsByCategoryID(categoryID);
        }


        for(int i = 0; i < allTransactions.size(); i++)
        {
            total += allTransactions.get(i).getAmount();
        }

        return total;

    }

    public double averageSpending(int categoryID)
    {

        double average;

        if(numTransactions(categoryID) == 0)
        {
            average = 0;
        }
        else
        {
            average = totalSpending(categoryID) / numTransactions(categoryID);
        }

        return average;

    }

    public ICategoryReport getCategoryReport(int categoryID)
    {
        double totalSpending;
        int numTransactions;
        double average;
        double percentage;

        totalSpending = totalSpending(categoryID);
        numTransactions = numTransactions(categoryID);
        average = averageSpending(categoryID);
        percentage = (totalSpending(categoryID)/totalSpending(-1)) * 100;

        return new CategoryReport(dataAccessCategory.getCategoryByID(categoryID), totalSpending, numTransactions, average, percentage);
    }



    public ArrayList<IMainCategory> sortByTotal(boolean descending) throws InvalidLogInException
    {
        ArrayList<ICategoryReport> categoryReportList = new ArrayList<ICategoryReport>();
        List<MainCategory> mainCategoryList;
        ArrayList<IMainCategory> sortedCategories = new ArrayList<IMainCategory>();
        int userID;


        // do something with exception
        userID = UserManager.getUserID();

        if(userID == -1 )
            throw new InvalidLogInException("No user is logged in");

        // get all categories for a user
        mainCategoryList = dataAccessCategory.getAllCategory(userID);


        // generate report for each category user has
        for(MainCategory node : mainCategoryList)
        {
            categoryReportList.add(getCategoryReport(node.getCategoryID()));
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

    //returns list of categories sorted by percent
    public ArrayList<IMainCategory> sortByPercent(boolean descending) throws InvalidLogInException
    {
        ArrayList<ICategoryReport> categoryReportList = new ArrayList<ICategoryReport>();
        List<MainCategory> mainCategoryList;
        ArrayList<IMainCategory> sortedCategories = new ArrayList<IMainCategory>();
        int userID;


        // do something with exception
        userID = UserManager.getUserID();

        if(userID == -1 )
            throw new InvalidLogInException("No user is logged in");

        // get all categories for a user
        mainCategoryList = dataAccessCategory.getAllCategory(userID);


        // generate report for each category user has
        for(MainCategory node : mainCategoryList)
        {
            categoryReportList.add(getCategoryReport(node.getCategoryID()));
        }

        // sort categories by total
        Collections.sort(categoryReportList, (node1, node2) -> {
            // Compare based on the 'total' attribute
            if(descending) {
                return Double.compare(node2.getPercentage(), node1.getPercentage());
            }
            else {
                return Double.compare(node1.getPercentage(), node2.getPercentage());
            }

        });


        // take out category objects from sorted list of category reports
        for (ICategoryReport node : categoryReportList) {
            sortedCategories.add(node.getCategory());
        }

        return sortedCategories;
    }

    //returns list of categories sorted by average amount
    public ArrayList<IMainCategory> sortByAverage(boolean descending) throws InvalidLogInException
    {
        ArrayList<ICategoryReport> categoryReportList = new ArrayList<ICategoryReport>();
        List<MainCategory> mainCategoryList;
        ArrayList<IMainCategory> sortedCategories = new ArrayList<IMainCategory>();
        int userID;


        // do something with exception
        userID = UserManager.getUserID();

        if(userID == -1 )
            throw new InvalidLogInException("No user is logged in");

        // get all categories for a user
        mainCategoryList = dataAccessCategory.getAllCategory(userID);


        // generate report for each category user has
        for(MainCategory node : mainCategoryList)
        {
            categoryReportList.add(getCategoryReport(node.getCategoryID()));
        }

        // sort categories by total
        Collections.sort(categoryReportList, (node1, node2) -> {
            // Compare based on the 'total' attribute
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
