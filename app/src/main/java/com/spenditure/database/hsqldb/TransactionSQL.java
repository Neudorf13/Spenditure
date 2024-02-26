package com.spenditure.database.hsqldb;

import com.spenditure.database.TransactionPersistence;
import com.spenditure.object.DateTime;
import com.spenditure.object.Transaction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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


    private Transaction fromResultSet(final ResultSet rs) throws SQLException {
        final int transactionID = rs.getInt("TRANSACTIONID");
        final int userID = rs.getInt("USERID");
        final String name = rs.getString("NAME");
        final String date = rs.getString("DATE");
        final String place = rs.getString("PLACE");
        final double amount = rs.getDouble("AMOUNT");
        final String comments = rs.getString("COMMENTS");
        final boolean withdrawal = rs.getBoolean("WITHDRAWAL");
        final byte[] image = rs.getBytes("IMAGE");
        final int categoryID = rs.getInt("CATEGORYID");

        //need to write a method in DateTime to convert from String
        DateTime dateTime = null;

        return new Transaction(transactionID, userID, name, dateTime, place, amount, comments, withdrawal, image, categoryID);
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

        try(final Connection connection = connection()) {
            final PreparedStatement statement = connection.prepareStatement("INSERT INTO TRANSACTIONS VALUES(?,?,?,?,?,?,?,?,?)");
            statement.setInt(1,newTransaction.getUserID());
            statement.setString(2,newTransaction.getName());
            statement.setString(3,newTransaction.getDateTime().toString());
            statement.setString(4,newTransaction.getPlace());
            statement.setDouble(5,newTransaction.getAmount());
            statement.setString(6,newTransaction.getComments());
            statement.setBoolean(7,newTransaction.getType());
            statement.setBytes(8, newTransaction.getImage());
            statement.setInt(9,newTransaction.getCategoryID());

            statement.executeUpdate();

            statement.close();

            return true;
        }
        catch (final SQLException e) {
            throw new RuntimeException("An error occurred while processing the SQL operation", e);  //temp exception
        }
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
    public Transaction getTransactionByID(int transactionID) {
        try(final Connection connection = connection()) {
            final PreparedStatement statement = connection.prepareStatement("SELECT * FROM transactions\nWHERE TRANSACTIONID=?");
            statement.setInt(1,transactionID);

            final ResultSet resultSet = statement.executeQuery();
            final Transaction transaction = fromResultSet(resultSet);

            resultSet.close();
            statement.close();

            return transaction;

        }
        catch (final SQLException e) {
            throw new RuntimeException("An error occurred while processing the SQL operation", e);  //temp exception
        }
    }

    @Override
    public ArrayList<Transaction> getTransactionByName(String name) {
        final ArrayList<Transaction> transactions = new ArrayList<>();

        try(final Connection connection = connection()) {
            final PreparedStatement statement = connection.prepareStatement("SELECT * FROM transactions\nWHERE NAME=?");
            statement.setString(1,name);

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
    public ArrayList<Transaction> getTransactionsByPlace(String place) {
        final ArrayList<Transaction> transactions = new ArrayList<>();

        try(final Connection connection = connection()) {
            final PreparedStatement statement = connection.prepareStatement("SELECT * FROM transactions\nWHERE PLACE=?");
            statement.setString(1,place);

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
    public ArrayList<Transaction> getTransactionsByAmount(double lower, double upper) {
        final ArrayList<Transaction> transactions = new ArrayList<>();

        try(final Connection connection = connection()) {
            final PreparedStatement statement = connection.prepareStatement("SELECT * FROM transactions WHERE amount >= ? AND amount <= ?\"");
            statement.setDouble(1,lower);
            statement.setDouble(2,upper);

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
            //e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Transaction> getTransactionsByDateTime(DateTime lower, DateTime upper) {
        final ArrayList<Transaction> transactions = new ArrayList<>();

        try(final Connection connection = connection()) {
            final PreparedStatement statement = connection.prepareStatement("SELECT * FROM transactions WHERE date >= ? AND date <= ?\"");
            statement.setString(1,lower.toString());
            statement.setString(2,upper.toString());

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
    public ArrayList<Transaction> getTransactionsByCategoryID(int categoryID) {
        ArrayList<Transaction> transactions = new ArrayList<>();

        try(final Connection connection = connection()) {
            final PreparedStatement statement = connection.prepareStatement("SELECT * FROM transactions\nWHERE CATEGORYID=?");
            statement.setInt(1,categoryID);

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
    public ArrayList<Transaction> sortByDateNewestFirst() {
        return null;
    }

    @Override
    public ArrayList<Transaction> sortByDateOldestFirst() {
        return null;
    }
}
