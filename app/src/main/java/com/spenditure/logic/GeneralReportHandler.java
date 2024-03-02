package com.spenditure.logic;

import com.spenditure.application.Services;
import com.spenditure.database.CategoryPersistence;
import com.spenditure.database.TransactionPersistence;
import com.spenditure.database.stub.TransactionStub;
import com.spenditure.object.CategoryReport;
import com.spenditure.object.CategoryStatistics;
import com.spenditure.object.ICategoryReport;
import com.spenditure.object.IDateTime;
import com.spenditure.object.MainCategory;
import com.spenditure.object.Transaction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GeneralReportHandler {
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
            return dataAccessTransaction.getAllTransactions().size();
        }
        else
        {
            return dataAccessTransaction.getTransactionsByCategoryID(categoryID).size();
        }

    }

    public double totalSpending(int categoryID)
    {
        List<Transaction> allTransactions;

        double total = 0;

        if(categoryID < 0)
        {
            allTransactions = dataAccessTransaction.getAllTransactions();
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

        double average = 0;

        average = totalSpending(categoryID) / numTransactions(categoryID);

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

        return new CategoryReport(totalSpending, numTransactions, average, percentage);
    }



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
