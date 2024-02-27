/**
 * TransactionHandler.java
 *
 * COMP3350 SECTION A02
 *
 * @author Toran Pillay, 7842389
 * @date Tuesday, February 6, 2024
 *
 * PURPOSE:
 *  This file interprets information from Transactions sent by the UI layer,
 * sends information to the Database layer, and also validates all necessary
 * information in the Transactions.
 **/

package com.spenditure.logic;

import com.spenditure.application.Services;
import com.spenditure.database.TransactionPersistence;
import com.spenditure.object.DateTime;
import com.spenditure.object.Transaction;
import com.spenditure.logic.exceptions.*;

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
    public boolean addTransaction(Transaction t) throws InvalidTransactionException {


        if( t == null )
            throw new InvalidTransactionException("No transaction was provided to add!");

        else if(!checkNewTransactionID(t))
            throw new InvalidTransactionException("Transaction I.D. of new transaction (I.D.: "
                    + t.getTransactionID() +") is invalid; the transaction may already exist.");

        if(TransactionValidator.validateTransaction(t))
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
    public boolean modifyTransaction(Transaction t) throws InvalidTransactionException {

        if( t == null )
            throw new InvalidTransactionException("No transaction was provided to modify!");

        else if(checkNewTransactionID(t))
            throw new InvalidTransactionException("Transaction I.D. of new transaction (I.D.: "
                    + t.getTransactionID() +") is invalid; the transaction may not exist.");

        if(TransactionValidator.validateTransaction(t))
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
    public boolean deleteTransaction(Transaction t) throws InvalidTransactionException {

        if( t == null )
            throw new InvalidTransactionException("No transaction was provided to delete!");

        else if(checkNewTransactionID(t))
            throw new InvalidTransactionException("Transaction I.D. of new transaction (I.D.: "
                    + t.getTransactionID() +") is invalid; the transaction may not exist.");

        return dataAccessTransaction.deleteTransaction(t.getTransactionID());

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

        return dataAccessTransaction.getNewestTransactionsForUser();

    }

    /*
        getAllByOldestFirst

        Returns all transactions, sorted by oldest first
     */
    @Override
    public ArrayList<Transaction> getAllByOldestFirst() {

        return dataAccessTransaction.getOldestTransactionsForUser();

    }

    /*

        getTransactionByCategoryID

        Returns all transactions associated with the specified
        category.

     */
    @Override
    public ArrayList<Transaction> getTransactionByCategoryID(int categoryID) {

        return dataAccessTransaction.getTransactionsByCategoryID(categoryID);

    }

    /*

        getTransactionByName

        Returns all transactions with names matching the provided String.

     */
    @Override
    public ArrayList<Transaction> getTransactionByName(String name) {

        return dataAccessTransaction.getTransactionByName(name);

    }

    @Override
    public ArrayList<Transaction> getTransactionByPlace(String place) {

        return dataAccessTransaction.getTransactionsByPlace(place);

    }

    /*

        getTransactionByAmount

        Returns all transactions with amount values equal to the specified value.

     */
    @Override
    public ArrayList<Transaction> getTransactionByAmount(double amount) {

        return dataAccessTransaction.getTransactionsByAmount(amount, amount);

    }

    /*

        getTransactionByAmountBetween

        Returns all transactions with amount values equal to or between the given lower
        and upper bounds.

     */
    @Override
    public ArrayList<Transaction> getTransactionByAmountBetween(double lower, double upper) {

        return dataAccessTransaction.getTransactionsByAmount(lower, upper);

    }

    /*

        getTransactionByAmountGreaterThan

        Returns all transactions with amount values greater than the given amount.

     */
    @Override
    public ArrayList<Transaction> getTransactionByAmountGreaterThan(double amount) {

        return dataAccessTransaction.getTransactionsByAmount(amount + 1, Double.MAX_VALUE);

    }

    /*

        getTransactionByAmountLessThan

        Returns all transactions with amount values less than the given amount.

     */
    @Override
    public ArrayList<Transaction> getTransactionByAmountLessThan(double amount) {

        return dataAccessTransaction.getTransactionsByAmount(0, amount - 1);

    }

    /*

        getTransactionByDateTime

        Returns all transactions which occur at the specified date and time

     */
    @Override
    public ArrayList<Transaction> getTransactionByDateTime(DateTime target) {

        return dataAccessTransaction.getTransactionsByDateTime(target, target);

    }

    /*

        getTransactionByDate

        Returns all transactions which occur at any time on the specified date.

     */
    @Override
    public ArrayList<Transaction> getTransactionByDate(DateTime target) {

        DateTime lower = new DateTime(target.getYear(), target.getMonth(), target.getDay(),
                00, 00);
        DateTime upper = new DateTime(target.getYear(), target.getMonth(), target.getDay(),
                DateTimeValidator.MAX_HOURS, DateTimeValidator.MAX_MINUTES);

        return dataAccessTransaction.getTransactionsByDateTime(lower, upper);

    }

    /*

        getTransactionByDateTimeBetween

        Returns all transactions which occurred between the specified dates and times.

     */
    @Override
    public ArrayList<Transaction> getTransactionByDateTimeBetween(DateTime lower, DateTime upper) {

        return dataAccessTransaction.getTransactionsByDateTime(lower, upper);

    }

    /*

        getTransactionByDateTimeBefore

        Returns all transactions which occurred before the specified date and time.

     */
    @Override
    public ArrayList<Transaction> getTransactionByDateTimeBefore(DateTime date) {

        return dataAccessTransaction.getTransactionsByDateTime(
                new DateTime(0, 0, 0, 0, 0), date);

    }

    /*

        getTransactionByDateTimeAfter

        Returns all transactions which occurred after the specified date and time.

     */
    @Override
    public ArrayList<Transaction> getTransactionByDateTimeAfter(DateTime lower) {

        return dataAccessTransaction.getTransactionsByDateTime(lower,
                new DateTime(9999, 99, 99, 99, 99));

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
