/**
 * ITransactionHandler.java
 *
 * COMP3350 SECTION A02
 *
 * @author Toran Pillay, 7842389
 * @date Tuesday, February 6, 2024
 *
 * PURPOSE:
 *  Interface file for the Transaction Handler.
 **/

package com.spenditure.logic;

import com.spenditure.object.DateTime;
import com.spenditure.object.Transaction;

import java.util.ArrayList;
import java.util.List;


public interface ITransactionHandler {
    boolean addTransaction(Transaction t);

    boolean modifyTransaction(Transaction t);

    boolean deleteTransaction(Transaction t);

    Transaction getTransactionByID(int id);

    List<Transaction> getAllTransactions();

    ArrayList<Transaction> getAllByNewestFirst(int userID);

    ArrayList<Transaction> getAllByOldestFirst(int userID);

    ArrayList<Transaction> getTransactionByCategoryID(int categoryID);

    ArrayList<Transaction> getTransactionByName(String name);

    ArrayList<Transaction> getTransactionByPlace(String place);

    ArrayList<Transaction> getTransactionByAmountBetween(double lower, double upper);

    ArrayList<Transaction> getTransactionByAmountGreaterThan(double amount);

    ArrayList<Transaction> getTransactionByAmountLessThan(double amount);

    ArrayList<Transaction> getTransactionByDateTime(DateTime lower, DateTime upper);

}
