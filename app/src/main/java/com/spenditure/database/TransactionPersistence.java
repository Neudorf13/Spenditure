package com.spenditure.database;

import com.spenditure.object.DateTime;
import com.spenditure.object.Transaction;

import java.util.ArrayList;
import java.util.List;

public interface TransactionPersistence {

    // query database
    List<Transaction> getAllTransactions();
    List<Transaction> getAllTransactionsForUser(int userID);
    boolean addTransaction(Transaction newTransaction);
    boolean modifyTransaction(Transaction targetTransaction);
    boolean deleteTransaction(Transaction targetTransaction);
    Transaction getTransactionByID(int id);
    ArrayList<Transaction> getTransactionByName(String name);
    ArrayList<Transaction> getTransactionsByPlace(String place);
    ArrayList<Transaction> getTransactionsByAmount(double lower, double upper);
    ArrayList<Transaction> getTransactionsByDateTime(DateTime lower, DateTime upper);


    ArrayList<Transaction> getTransactionsByCategoryID(int categoryID);

    // sorting
    ArrayList<Transaction> sortByDateNewestFirst();
    ArrayList<Transaction> sortByDateOldestFirst();

}
