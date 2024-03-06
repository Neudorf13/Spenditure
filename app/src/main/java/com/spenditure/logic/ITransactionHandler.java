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

import com.spenditure.logic.exceptions.InvalidTransactionException;
import com.spenditure.object.DateTime;
import com.spenditure.object.Transaction;

import java.util.ArrayList;
import java.util.List;


public interface ITransactionHandler {
    boolean addTransaction(int userID, String whatTheHeck, DateTime date, String place, double amount, String comments, boolean type);

    boolean addTransaction(int userID, String whatTheHeck, DateTime date, String place, double amount, String comments, boolean withdrawal, byte[] image, int categoryID)
        throws InvalidTransactionException;

    boolean modifyTransaction(Transaction t);

    boolean deleteTransaction(Transaction t);

    Transaction getTransactionByID(int id);

    List<Transaction> getAllTransactions(int userID);

    ArrayList<Transaction> getAllByNewestFirst(int userID);

    ArrayList<Transaction> getAllByOldestFirst(int userID);

    ArrayList<Transaction> getTransactionByCategoryID(int categoryID);

    ArrayList<Transaction> getTransactionByName(int userID, String name);

    ArrayList<Transaction> getTransactionByPlace(int userID, String place);

    ArrayList<Transaction> getTransactionByAmountBetween(int userID, double lower, double upper);

    ArrayList<Transaction> getTransactionByDateTime(int userID, DateTime lower, DateTime upper);

}
