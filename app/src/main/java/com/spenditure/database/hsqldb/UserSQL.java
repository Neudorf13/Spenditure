package com.spenditure.database.hsqldb;

import android.annotation.SuppressLint;

import com.spenditure.database.UserPersistence;
import com.spenditure.logic.exceptions.InvalidUserInformationException;
import com.spenditure.object.MainCategory;
import com.spenditure.object.Transaction;
import com.spenditure.object.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserSQL implements UserPersistence {

    private final String dbPath;

    public UserSQL(final String dbPath) {
        this.dbPath = dbPath;
    }

    private Connection connection() throws SQLException {
        //System.out.println("jdbc:hsqldb:file:" + dbPath + ";shutdown=true");
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }

    private User fromResultSet(final ResultSet rs) throws SQLException {

        return null;
    }


    @Override
    public String getUserName(int userID) {
        String userName = "";
        try(final Connection connection = connection()) {
            final PreparedStatement statement = connection.prepareStatement("SELECT * FROM users\nWHERE userID=?");
            statement.setInt(1,userID);

            final ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                userName = resultSet.getString("USERNAME");
            }


            resultSet.close();
            statement.close();

            return userName;


        }
        catch (final SQLException e) {
            throw new RuntimeException("An error occurred while processing the SQL operation", e);  //temp exception
        }

    }

    @Override
    public int getNumberOfUsers() {
        int count = 0;
        try(final Connection connection = connection()) {
            final Statement st = connection.createStatement();
            final ResultSet rs = st.executeQuery("SELECT * FROM users");
            while (rs.next())
            {
                int userID = rs.getInt("USERID");
                System.out.println("UserID: " + userID);

                String username = rs.getString("USERNAME");
                System.out.println("Username: " + username);

                String password = rs.getString("PASSWORD");
                System.out.println("Password: " + password);

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
    public int login(String username, String password) {
        //if username + password combo exists in db
        //return userID
        try(final Connection connection = connection()) {
            final PreparedStatement statement = connection.prepareStatement("SELECT * FROM users\nWHERE USERNAME=? AND PASSWORD=?;");
            statement.setString(1, username);
            statement.setString(2, password);

            final ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()) {
                return resultSet.getInt("USERID");
            }
            else {
                throw new InvalidUserInformationException("Wrong username or password");
            }

        }
        catch (final SQLException e) {
            throw new RuntimeException("An error occurred while processing the SQL operation", e);  //temp exception
        }

    }

    @Override
    public boolean changePassword(int userID, String oldPassword, String newPassword) {
        //need to first check if the
        try(final Connection connection = connection()) {
            final PreparedStatement selectStatement  = connection.prepareStatement("SELECT * FROM users\nWHERE USERID=? AND PASSWORD=?;");
            selectStatement .setInt(1,userID);
            selectStatement .setString(2, oldPassword);

            final ResultSet resultSet = selectStatement .executeQuery();

            if(resultSet.next()) {
                final PreparedStatement updateStatement = connection.prepareStatement("UPDATE users\nSET PASSWORD=?\nWHERE USERID=?");
                updateStatement.setString(1, newPassword);
                updateStatement.setInt(2,userID);

                int rowsAffected = updateStatement.executeUpdate();

                if (rowsAffected == 1) {
                    return true; // Password updated successfully
                } else {
                    throw new RuntimeException("Failed to update password.");
                }

            }
            else {
                throw new InvalidUserInformationException("UserID and password combination does not exist.");
            }
        }
        catch (final SQLException e) {
            throw new RuntimeException("An error occurred while processing the SQL operation", e);  //temp exception
        }


    }

    @Override
    public boolean changeUsername(int userID, String newUsername) {
        try(final Connection connection = connection()) {
            final PreparedStatement updateStatement = connection.prepareStatement("UPDATE users\nSET USERNAME=?\nWHERE USERID=?");
            updateStatement.setString(1, newUsername);
            updateStatement.setInt(2, userID);

            int rowsAffected = updateStatement.executeUpdate();
            if (rowsAffected == 1) {
                return true; // Password updated successfully
            } else {
                throw new RuntimeException("Failed to update username.");
            }

        }
        catch (final SQLException e) {
            throw new RuntimeException("An error occurred while processing the SQL operation", e);  //temp exception
        }

    }

    @Override
    public int register(int userID, String username, String password, String email) {
        try(final Connection connection = connection()) {
            String insertQuery = "INSERT INTO users (userid, username, password, email) VALUES (?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                // Set values for the parameters
                preparedStatement.setInt(1, userID);
                preparedStatement.setString(2, username);
                preparedStatement.setString(3, password);
                preparedStatement.setString(4, email);

                // Execute the query
                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    return userID;
                }
                else {
                    throw new RuntimeException("Register failed.");
                }
            }
        }
        catch (final SQLException e) {
            throw new RuntimeException("An error occurred while processing the SQL operation", e);  //temp exception
        }

    }

    public void printUserTable() {
        try(final Connection connection = connection()) {
            final Statement st = connection.createStatement();
            final ResultSet rs = st.executeQuery("SELECT * FROM users");
            while (rs.next())
            {
                int userID = rs.getInt("USERID");
                String username = rs.getString("USERNAME");
                String password = rs.getString("PASSWORD");

                @SuppressLint("DefaultLocale") String printUser = String.format("UserID: %d, Username: %s, Password: %s", userID, username, password);
                System.out.println(printUser);
            }
            rs.close();
            st.close();

        }
        catch (final SQLException e) {
            throw new RuntimeException("An error occurred while processing the SQL operation", e);  //temp exception
        }

    }


}
