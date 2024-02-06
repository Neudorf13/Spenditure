package com.spenditure.logic;

import com.spenditure.application.Services;
import com.spenditure.database.CategoryPersistence;
import com.spenditure.database.TransactionPersistence;
import com.spenditure.logic.exceptions.InvalidCategoryException;
import com.spenditure.object.CT;
import com.spenditure.object.Category;
import com.spenditure.object.Transaction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ReportManager {

    //instance vars
    private TransactionPersistence dataAccessTransaction;
    private CategoryPersistence dataAccessCategory;

    public ReportManager(boolean inDeveloping) {
        this.dataAccessTransaction = Services.getTransactionPersistence(inDeveloping);
        this.dataAccessCategory = Services.getCategoryPersistence(inDeveloping);
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
    public ArrayList<ReportManagerNode> buildCategoryList() {
        ArrayList<ReportManagerNode> categoryList = new ArrayList<>();
        int numCategories = countAllCategories();

        for(int i = 1; i < numCategories+1; i++) {
            //for each Category calculate -> total, average, %
            Category category = dataAccessCategory.getCategoryByID(i);
            double total = getTotalForCategory(i);
            double average = getAverageForCategory(i);
            double percent = getPercentForCategory(i);

            ReportManagerNode node = new ReportManagerNode(category,total,average,percent);
            categoryList.add(node);
        }

        return categoryList;
    }

    public ArrayList<Category> sortByTotal(boolean descending) {
        ArrayList<ReportManagerNode> categoryList = buildCategoryList();

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
        ArrayList<Category> sortedCategories = new ArrayList<>();

        for (ReportManagerNode node : categoryList) {
            sortedCategories.add(node.getCategory());
        }

        return sortedCategories;
    }

    public ArrayList<Category> sortByPercent(boolean descending) {
        ArrayList<ReportManagerNode> categoryList = buildCategoryList();

        Collections.sort(categoryList, (node1, node2) -> {
            // Compare based on the 'total' attribute
            if(descending) {
                return Double.compare(node2.getPercent(), node1.getPercent());
            }
            else {
                return Double.compare(node1.getPercent(), node2.getPercent());
            }

        });

        // Create a new ArrayList to store sorted categories
        ArrayList<Category> sortedCategories = new ArrayList<>();

        for (ReportManagerNode node : categoryList) {
            sortedCategories.add(node.getCategory());
        }

        return sortedCategories;
    }


    public ArrayList<Category> sortByAverage(boolean descending) {
        ArrayList<ReportManagerNode> categoryList = buildCategoryList();

        Collections.sort(categoryList, (node1, node2) -> {
            // Compare based on the 'total' attribute
            if(descending) {
                return Double.compare(node2.getAverage(), node1.getAverage());
            }
            else {
                return Double.compare(node1.getAverage(), node2.getAverage());
            }

        });

        // Create a new ArrayList to store sorted categories
        ArrayList<Category> sortedCategories = new ArrayList<>();

        for (ReportManagerNode node : categoryList) {
            sortedCategories.add(node.getCategory());
        }

        return sortedCategories;
    }


    private class ReportManagerNode {
        //instance vars
        private Category category;
        private double total;
        private double average;
        private double percent;

        public ReportManagerNode(Category category, double total, double average, double percent) {
            this.category = category;
            this.total = total;
            this.average = average;
            this.percent = percent;
        }

        public Category getCategory() {
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
