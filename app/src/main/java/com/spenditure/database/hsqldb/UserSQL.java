package com.spenditure.database.hsqldb;

import com.spenditure.database.UserPersistence;
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
        return 0;
    }

    @Override
    public boolean changePassword(int userID, String oldPassword, String newPassword) {
        return false;
    }

    @Override
    public boolean changeUsername(int userID, String newUsername) {
        return false;
    }

    @Override
    public int register(String username, String password) {
        return 0;
    }


}
