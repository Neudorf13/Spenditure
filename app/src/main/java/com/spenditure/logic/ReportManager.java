package com.spenditure.logic;

import com.spenditure.application.Services;
import com.spenditure.database.AssignmentPersistence;
import com.spenditure.database.CategoryPersistence;
import com.spenditure.database.TransactionPersistence;
import com.spenditure.object.CT;
import com.spenditure.object.Category;
import com.spenditure.object.Transaction;

import java.util.ArrayList;
import java.util.List;

public class ReportManager {

    //instance vars
    private CategoryPersistence dataAccessCategory;
    private TransactionPersistence dataAccessTransaction;
    //private AssignmentPersistence dataAccessAssignment;

    public ReportManager(boolean inDeveloping) {
        //this.dataAccessCategory = Services.getCategoryPersistence(inDeveloping);
        this.dataAccessTransaction = Services.getTransactionPersistence(inDeveloping);
        //this.dataAccessAssignment = Services.getAssignmentPersistence(inDeveloping);
    }

//    public void printCategories() {
//        List<Category> listCategories = this.dataAccessCategory.getAllCategory();
//
//        for (Category element : listCategories) {
//            System.out.println(element);
//        }
//    }

    public int countAllTransactions() {
        List<Transaction> transactions = dataAccessTransaction.getAllTransactions();
        return transactions.size();
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
        ArrayList<Transaction> categoryTransactions =  dataAccessTransaction.getTransactionByCategoryID(categoryID);
        double total = getTotalForCategory(categoryID);
        int count = countTransactionsByCategory(categoryID);

        return (total / count);
    }

    //might want to write a test to check that the sum of these percents adds to 100
    public double getPercentForCategory(int categoryID) {
        //will need total across all transactions + total for specific category
        //int countAll = countAllTransactions();
        //int countCategory = countTransactionsByCategory(categoryID);

        double totalAllTransactions = getTotalForAllTransactions();
        double totalForCategory = getTotalForCategory(categoryID);

        return ((totalForCategory / totalAllTransactions) * 100);
    }


}
