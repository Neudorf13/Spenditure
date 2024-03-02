package com.spenditure.database.hsqldb;

import com.spenditure.database.TransactionPersistence;
import com.spenditure.object.DateTime;
import com.spenditure.object.IDateTime;
import com.spenditure.object.Transaction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class TransactionSQL implements TransactionPersistence {

    private final String dbPath;

    public TransactionSQL(final String dbPath) {
        this.dbPath = dbPath;
    }

    private Connection connection() throws SQLException {
        //System.out.println("jdbc:hsqldb:file:" + dbPath + ";shutdown=true");
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }


    private Transaction fromResultSet(final ResultSet rs) throws SQLException {
        System.out.println("in fromResultSet");
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


        DateTime dateTime = new DateTime(date);

        return new Transaction(transactionID, userID, name, dateTime, place, amount, comments, withdrawal, image, categoryID);
        //return null;
        //return new MainCategory(categoryName, Integer.parseInt(categoryID), Integer.parseInt(userID));
    }

    public int countTransactions() {
        int count = 0;
        try(final Connection connection = connection()) {
            final Statement st = connection.createStatement();
            final ResultSet rs = st.executeQuery("SELECT * FROM transactions");
            while (rs.next())
            {
                count++;
            }
            rs.close();
            st.close();

            return count;

        }
        catch (final SQLException e) {
            throw new RuntimeException("An error occurred while processing the SQL operation", e);  //temp exception
        }
    }

    @Override
    public List<Transaction> getAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();

        try(final Connection connection = connection()) {
            final Statement st = connection.createStatement();
            final ResultSet rs = st.executeQuery("SELECT * FROM transactions");
            while (rs.next())
            {
                final Transaction transaction = fromResultSet(rs);
                transactions.add(transaction);
            }
            rs.close();
            st.close();

            return transactions;

        }
        catch (final SQLException e) {
            throw new RuntimeException("An error occurred while processing the SQL operation", e);  //temp exception
        }

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
            statement.setBoolean(7,newTransaction.getWithdrawal());
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
    public boolean modifyTransaction(Transaction transaction) {
//        try (Connection connection = connection()) {
//            // Delete the existing transaction with the given transactionID
//            try (PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM transactions WHERE TRANSACTIONID = ?")) {
//                deleteStatement.setInt(1, transaction.getTransactionID());
//                deleteStatement.executeUpdate();
//            }
//
//            // Insert the new transaction object
//            try (PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO transactions (transactionID, amount, image) VALUES (?, ?, ?)")) {
//                insertStatement.setInt(1, transaction.getTransactionID());
//                insertStatement.setDouble(2, transaction.getAmount());
//                insertStatement.setBytes(3, transaction.getImage());
//                int rowsInserted = insertStatement.executeUpdate();
//
//                return rowsInserted > 0; // Return true if row(s) inserted successfully
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException("An error occurred while processing the SQL operation", e);
//        }
        return false;
    }

    @Override
    public boolean deleteTransaction(int transactionID) {
        try(final Connection connection = connection()) {
            final PreparedStatement statement = connection.prepareStatement("DELETE FROM transactions\nWHERE TRANSACTIONID=?");
            statement.setInt(1,transactionID);

            int row = statement.executeUpdate();

            return row > 0; //true if row number is greater than 0
        }
        catch (final SQLException e) {
            throw new RuntimeException("An error occurred while processing the SQL operation", e);  //temp exception
        }
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
    public ArrayList<Transaction> getTransactionsByDateTime(IDateTime lower, IDateTime upper) {
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
        System.out.println("getTransactionsByCategoryID in TransactionSQL");
        ArrayList<Transaction> transactions = new ArrayList<>();

        try(final Connection connection = connection()) {
            System.out.println("getTransactionsByCategoryID in TransactionSQL");
            final PreparedStatement statement = connection.prepareStatement("SELECT * FROM transactions\nWHERE CATEGORYID=?");
            System.out.println("getTransactionsByCategoryID in TransactionSQL");
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
    public ArrayList<Transaction> getNewestTransactionsForUser(int userID) {
        ArrayList<Transaction> transactions = new ArrayList<>();

        try(final Connection connection = connection()) {
            final PreparedStatement statement = connection.prepareStatement("SELECT * FROM transactions\nWHERE USERID = ?\nORDER BY DATE DESC;");
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
    public ArrayList<Transaction> getOldestTransactionsForUser(int userID) {
        ArrayList<Transaction> transactions = new ArrayList<>();

        try(final Connection connection = connection()) {
            final PreparedStatement statement = connection.prepareStatement("SELECT * FROM transactions\nWHERE USERID = ?\nORDER BY DATE;");
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

    public void getCourseSequential() {
        //final List<Course> courses = new ArrayList<>();

        try (final Connection c = connection()) {
            final Statement st = c.createStatement();
            final ResultSet rs = st.executeQuery("SELECT TABLE_NAME\n" +
                    "FROM INFORMATION_SCHEMA.TABLES\n" +
                    "WHERE TABLE_SCHEMA = 'PUBLIC';\n");

            System.out.println("LOOK HERE!!!!!!!!!!!!!");
            if(!rs.next()) {
                System.out.println("F's in chat gentlemen");
            }

            while (rs.next())
            {
//                final Course course = fromResultSet(rs);
//                courses.add(course);
                //System.out.println(rs.getString("TABLE_NAME"));
                System.out.println("Maybe Baby");
            }
            rs.close();
            st.close();

            //return courses;
        }
        catch (final SQLException e)
        {
            //throw new PersistenceException(e);
            throw new RuntimeException("An error occurred while processing the SQL operation", e);  //temp exception
        }
    }

}
