package com.spenditure.database;

import com.spenditure.object.Category;
import com.spenditure.object.Transaction;

import java.util.ArrayList;
import java.util.List;

public interface TransactionPersistence {

    // query database
    List<Transaction> getAllTransactions();
    boolean addTransaction(Transaction newTransaction);
    boolean modifyTransaction(Transaction targetTransaction);
    boolean deleteTransaction(Transaction targetTransaction);
    Transaction getTransactionByID(int id);

    // sorting
    ArrayList<Transaction> sortByDateNewestFirst();
    ArrayList<Transaction> sortByDateOldestFirst();

}
