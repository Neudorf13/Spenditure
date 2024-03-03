package com.spenditure.database;

import com.spenditure.object.IDateTime;
import com.spenditure.object.Transaction;

import java.util.ArrayList;
import java.util.List;

/**
 * TransactionPersistence.java
 *
 * COMP3350 SECTION A02
 *
 * @author Jared Rost,
 * @date Feb 7, 2024
 *
 * PURPOSE:
 *  This file provides information on what methods the transaction database handler should implement
 *
 **/

public interface TransactionPersistence {

    // query database

    int countTransactions();
    List<Transaction> getAllTransactions();
    List<Transaction> getAllTransactionsForUser(int userID);
    boolean addTransaction(Transaction newTransaction);
    boolean modifyTransaction(Transaction targetTransaction);
    boolean deleteTransaction(int transactionID);
    Transaction getTransactionByID(int id);
    ArrayList<Transaction> getTransactionByName(String name);
    ArrayList<Transaction> getTransactionsByPlace(String place);
    ArrayList<Transaction> getTransactionsByAmount(double lower, double upper);
    ArrayList<Transaction> getTransactionsByDateTime(IDateTime lower, IDateTime upper);


    ArrayList<Transaction> getTransactionsByCategoryID(int categoryID);

    // sorting
    ArrayList<Transaction> getNewestTransactionsForUser(int userID);
    ArrayList<Transaction> getOldestTransactionsForUser(int userID);

    public void printTransactionTable();

}
