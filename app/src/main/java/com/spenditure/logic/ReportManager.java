package com.spenditure.logic;

import com.spenditure.application.Services;
import com.spenditure.database.AssignmentPersistence;
import com.spenditure.database.CategoryPersistence;
import com.spenditure.database.TransactionPersistence;
import com.spenditure.logic.exceptions.InvalidCategoryException;
import com.spenditure.object.CT;
import com.spenditure.object.Category;
import com.spenditure.object.Transaction;

import java.util.ArrayList;
import java.util.List;

public class ReportManager {

    //instance vars
    private TransactionPersistence dataAccessTransaction;
    private CategoryPersistence dataAccessCategory;

    public ReportManager(boolean inDeveloping) {
        this.dataAccessTransaction = Services.getTransactionPersistence(inDeveloping);
    }

    public int countAllTransactions() {
        List<Transaction> transactions = dataAccessTransaction.getAllTransactions();
        return transactions.size();
    }

    public int countAllCategories() {
        List<Category> categories = dataAccessCategory.getAllCategory();
        return categories.size();
    }

    public int countTransactionsByCategory(int categoryID) {
        ArrayList<Transaction> categoryTransactions = dataAccessTransaction.getTransactionByCategoryID(categoryID);
        return categoryTransactions.size();
    }

    public double getTotalForAllTransactions() {
        List<Transaction> transactions = dataAccessTransaction.getAllTransactions();
        double total = 0.0;

        for(Transaction element : transactions) {
            total += element.getAmount();
        }

        return total;
    }

    public double getTotalForCategory(int categoryID) {
        ArrayList<Transaction> categoryTransactions =  dataAccessTransaction.getTransactionByCategoryID(categoryID);
        double total = 0.0;

        for(Transaction element : categoryTransactions) {
            //for each CT, access its transaction, add the transaction value to total
            total += element.getAmount();
        }

        return total;
    }

    public double getAverageForCategory(int categoryID) {
        double total = getTotalForCategory(categoryID);
        int count = countTransactionsByCategory(categoryID);

        return (total / count);
    }

    public double getPercentForCategory(int categoryID) {

        double totalAllTransactions = getTotalForAllTransactions();
        double totalForCategory = getTotalForCategory(categoryID);

        return ((totalForCategory / totalAllTransactions) * 100);
    }

    //sorting methods
    public ArrayList<Category> sortByLargestTotal() {
        return null;
    }

    public ArrayList<Category> sortBySmallestTotal() {
        return null;
    }

    public ArrayList<Category> sortByLargestPercent() {
        return null;
    }

    public ArrayList<Category> sortBySmallestPercent() {
        return null;
    }

    public ArrayList<Category> sortByLargestAverage() {
        return null;
    }

    public ArrayList<Category> sortBySmallestAverage() {
        return null;
    }




}
