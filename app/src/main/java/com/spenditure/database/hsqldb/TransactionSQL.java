package com.spenditure.database.hsqldb;

import com.spenditure.database.TransactionPersistence;
import com.spenditure.object.DateTime;
import com.spenditure.object.MainCategory;
import com.spenditure.object.Transaction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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


    private Transaction fromResultSet(final ResultSet rs) throws SQLException {
        final String transactionID = rs.getString("TRANSACTIONID");
        final String userID = rs.getString("USERID");
        final String name = rs.getString("NAME");
        final String date = rs.getString("DATE");
        final String place = rs.getString("PLACE");
        final String amount = rs.getString("AMOUNT");
        final String comments = rs.getString("COMMENTS");
        final String withdrawal = rs.getString("WITHDRAWAL");
        final String categoryID = rs.getString("CATEGORYID");

        //need to write a method in DateTime to convert from String
        DateTime dateTime = null;

        return new Transaction(Integer.parseInt(transactionID), Integer.parseInt(userID), name, dateTime, place, Double.parseDouble(amount), comments, Boolean.parseBoolean(withdrawal), Integer.parseInt(categoryID));
        //return new MainCategory(categoryName, Integer.parseInt(categoryID), Integer.parseInt(userID));
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return null;
    }

    public List<Transaction> getAllTransactionsForUser(int userID) {
        List<Transaction> transactions = new ArrayList<>();

        try(final Connection connection = connection()) {
            final PreparedStatement statement = connection.prepareStatement("SELECT * FROM transactions\nWHERE userID=?");
            statement.setInt(1,userID);

            final ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()) {
                final Transaction transaction = fromResultSet(resultSet);
                transactions.add(transaction);
            }


            resultSet.close();
            statement.close();

            return transactions;
        }
        catch (final SQLException e) {
            throw new RuntimeException("An error occurred while processing the SQL operation", e);  //temp exception
        }
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
