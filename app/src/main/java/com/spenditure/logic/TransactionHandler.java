package com.spenditure.logic;

import com.spenditure.application.Services;
import com.spenditure.database.TransactionPersistence;
import com.spenditure.object.Transaction;

import java.util.ArrayList;
import java.util.List;
public class TransactionHandler implements ITransactionHandler {

    //Handles communication with data layer
    private final TransactionPersistence dataAccessTransaction;
    //Expected ID for new transactions that have not yet been added
    private static final int NEW_TRANSACTION_ID = -1;

    public TransactionHandler(boolean inDevelopment) {
        dataAccessTransaction = Services.getTransactionPersistence(inDevelopment);
    }

    /*
        addTransaction

        Checks the ID to make sure it's a new transaction,
        validates it, then sends it to the data layer to be added.
     */
    @Override
    public boolean addTransaction(Transaction t) {

        if(checkNewTransactionID(t)
                && TransactionValidator.validateTransaction(t))
            return dataAccessTransaction.addTransaction(t);
        else
            return false;

    }

    /*
        modifyTransaction

        Checks the ID to make sure it's not a new transaction,
        validates it, then sends it to the data layer to overwrite
        the old one.
     */
    @Override
    public boolean modifyTransaction(Transaction t) {

        if(!checkNewTransactionID(t)
                && TransactionValidator.validateTransaction(t))
            return dataAccessTransaction.modifyTransaction(t);
        else
            return false;

    }

    /*
        deleteTransaction

        Validates the transaction, then sends it to the data layer
        so it can be deleted.
     */
    @Override
    public boolean deleteTransaction(Transaction t) {

        return dataAccessTransaction.deleteTransaction(t);

    }

    /*
        getTransactionByID

        Takes an ID, and returns the corresponding transaction if
        one is found by the data layer.
     */
    @Override
    public Transaction getTransactionByID(int id) {

        return dataAccessTransaction.getTransactionByID(id);

    }

    /*
        getAllTransactions

        Returns all transactions given by the data layer.
     */
    @Override
    public List<Transaction> getAllTransactions() {

        return dataAccessTransaction.getAllTransactions();

    }

    /*
        getAllByNewestFirst

        Returns all transactions, sorted by newest first
     */
    @Override
    public ArrayList<Transaction> getAllByNewestFirst() {

        return dataAccessTransaction.sortByDateNewestFirst();

    }

    /*
        getAllByOldestFirst

        Returns all transactions, sorted by oldest first
     */
    @Override
    public ArrayList<Transaction> getAllByOldestFirst() {

        return dataAccessTransaction.sortByDateOldestFirst();

    }

    /*
        checkNewTransactionID

        Checks to see if the transaction has a new ID, meaning it
        hasn't yet been added to the database.
     */
    private boolean checkNewTransactionID(Transaction t) {

        return t.getTransactionID() == NEW_TRANSACTION_ID;

    }




}
