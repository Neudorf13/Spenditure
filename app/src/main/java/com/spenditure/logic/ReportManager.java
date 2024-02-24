package com.spenditure.logic;

import com.spenditure.application.Services;
import com.spenditure.database.CategoryPersistence;
import com.spenditure.database.TransactionPersistence;
import com.spenditure.object.MainCategory;
import com.spenditure.object.Transaction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReportManager {

    //instance vars
    private TransactionPersistence dataAccessTransaction;
    private CategoryPersistence dataAccessCategory;

    public ReportManager(boolean inDeveloping) {
        this.dataAccessTransaction = Services.getTransactionPersistence(inDeveloping);
        this.dataAccessCategory = Services.getCategoryPersistence(inDeveloping);
    }

    //return count of total transactions
    public int countAllTransactions() {
        List<Transaction> transactions = dataAccessTransaction.getAllTransactions();
        return transactions.size();
    }

    //return count of total categories
    public int countAllCategories(int userID) {
        List<MainCategory> categories = dataAccessCategory.getAllCategory(userID);
        return categories.size();
    }

    //return count of transactions with specific category
    public int countTransactionsByCategory(int categoryID) {
        ArrayList<Transaction> categoryTransactions = dataAccessTransaction.getTransactionByCategoryID(categoryID);
        return categoryTransactions.size();
    }

    //return sum of total amount for all transactions
    public double getTotalForAllTransactions() {
        List<Transaction> transactions = dataAccessTransaction.getAllTransactions();
        double total = 0.0;

        for(Transaction element : transactions) {
            total += element.getAmount();
        }

        return total;
    }

    //return sum of total amount for specified category
    public double getTotalForCategory(int categoryID) {
        ArrayList<Transaction> categoryTransactions =  dataAccessTransaction.getTransactionByCategoryID(categoryID);
        double total = 0.0;

        for(Transaction element : categoryTransactions) {
            //for each CT, access its transaction, add the transaction value to total
            total += element.getAmount();
        }

        return total;
    }

    //return average transaction amount for a given category
    public double getAverageForCategory(int categoryID) {
        double total = getTotalForCategory(categoryID);
        int count = countTransactionsByCategory(categoryID);

        return (total / count);
    }

    //return % of total transaction sum for given category
    public double getPercentForCategory(int categoryID) {

        double totalAllTransactions = getTotalForAllTransactions();
        double totalForCategory = getTotalForCategory(categoryID);

        return ((totalForCategory / totalAllTransactions) * 100);
    }

    //sorting methods

    //returns list of ReportManagerNodes (one for each category)
    public ArrayList<ReportManagerNode> buildCategoryList(int userID) {
        ArrayList<ReportManagerNode> categoryList = new ArrayList<>();
        int numCategories = countAllCategories(userID);

        for(int i = 1; i < numCategories+1; i++) {
            //for each Category calculate -> total, average, %
            MainCategory category = dataAccessCategory.getCategoryByID(i);
            double total = getTotalForCategory(i);
            double average = getAverageForCategory(i);
            double percent = getPercentForCategory(i);

            ReportManagerNode node = new ReportManagerNode(category,total,average,percent);
            categoryList.add(node);
        }

        return categoryList;
    }

    //returns list of categories sorted by total amount
    public ArrayList<MainCategory> sortByTotal(int userID, boolean descending) {
        ArrayList<ReportManagerNode> categoryList = buildCategoryList(userID);

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

        for (ReportManagerNode node : categoryList) {
            sortedCategories.add(node.getCategory());
        }

        return sortedCategories;
    }

    //returns list of categories sorted by percent
    public ArrayList<MainCategory> sortByPercent(int userID, boolean descending) {
        ArrayList<ReportManagerNode> categoryList = buildCategoryList(userID);

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

        for (ReportManagerNode node : categoryList) {
            sortedCategories.add(node.getCategory());
        }

        return sortedCategories;
    }

    //returns list of categories sorted by average amount
    public ArrayList<MainCategory> sortByAverage(int userID, boolean descending) {
        ArrayList<ReportManagerNode> categoryList = buildCategoryList(userID);

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

        for (ReportManagerNode node : categoryList) {
            sortedCategories.add(node.getCategory());
        }

        return sortedCategories;
    }

    //ReportManagerNode class used for internal ReportManager purposes
    //Stores each Category with associated total, average and percent values
    private class ReportManagerNode {
        //instance vars
        private MainCategory category;
        private double total;
        private double average;
        private double percent;

        public ReportManagerNode(MainCategory category, double total, double average, double percent) {
            this.category = category;
            this.total = total;
            this.average = average;
            this.percent = percent;
        }

        public MainCategory getCategory() {
            return category;
        }

        public double getTotal() {
            return total;
        }

        public double getAverage() {
            return average;
        }

        public double getPercent() {
            return percent;
        }
    }


}
