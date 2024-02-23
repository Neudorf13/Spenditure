package com.spenditure.database.hsqldb;

import com.spenditure.database.TransactionPersistence;
import com.spenditure.object.DateTime;
import com.spenditure.object.Transaction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionSQL implements TransactionPersistence {

    private final String dbPath;

    public TransactionSQL(final String dbPath) {
        this.dbPath = dbPath;
    }

    private Connection connection() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }


    @Override
    public List<Transaction> getAllTransactions() {
        return null;
    }

    @Override
    public boolean addTransaction(Transaction newTransaction) {
        return false;
    }

    @Override
    public boolean modifyTransaction(Transaction targetTransaction) {
        return false;
    }

    @Override
    public boolean deleteTransaction(Transaction targetTransaction) {
        return false;
    }

    @Override
    public Transaction getTransactionByID(int id) {
        return null;
    }

    @Override
    public ArrayList<Transaction> getTransactionByName(String name) {
        return null;
    }

    @Override
    public ArrayList<Transaction> getTransactionsByPlace(String place) {
        return null;
    }

    @Override
    public ArrayList<Transaction> getTransactionsByAmount(double lower, double upper) {
        return null;
    }

    @Override
    public ArrayList<Transaction> getTransactionsByDateTime(DateTime lower, DateTime upper) {
        return null;
    }

    @Override
    public ArrayList<Transaction> getTransactionByCategoryID(int categoryID) {
        return null;
    }

    @Override
    public ArrayList<Transaction> sortByDateNewestFirst() {
        return null;
    }

    @Override
    public ArrayList<Transaction> sortByDateOldestFirst() {
        return null;
    }
}
