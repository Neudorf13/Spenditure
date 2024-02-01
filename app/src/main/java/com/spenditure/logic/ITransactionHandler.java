package com.spenditure.logic;

import com.spenditure.object.Transaction;

import java.util.ArrayList;
import java.util.List;

public interface ITransactionHandler {
    boolean addTransaction(Transaction t);

    boolean modifyTransaction(Transaction t);

    boolean deleteTransaction(Transaction t);

    Transaction getTransactionByID(int id);

    List<Transaction> getAllTransactions();

    ArrayList<Transaction> getAllByNewestFirst();

    ArrayList<Transaction> getAllByOldestFirst();
}
