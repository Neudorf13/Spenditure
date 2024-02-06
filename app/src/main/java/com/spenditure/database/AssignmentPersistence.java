package com.spenditure.database;

import com.spenditure.object.CT;
import com.spenditure.object.MainCategory;
import com.spenditure.object.Transaction;

import java.util.ArrayList;

public interface AssignmentPersistence {

    ArrayList<CT> getAllAssignments();

    boolean addAssignment(CT newAssignment);

    boolean deleteAssignmentByCategory(int categoryID);

    boolean deleteAssignmentByTransaction(int transactionID);

    boolean deleteAssignment(int categoryID, int transactionID);

    ArrayList<CT> getAssignmentByTransactionID(int transactionID);

    ArrayList<CT> getAssignmentByCategoryID(int categoryID);

    CT getAssignment(int categoryID, int transactionID);

    ArrayList<Transaction> filterByCategories(ArrayList<MainCategory> searchedCategories);
}
