package com.spenditure.database.hsqldb;

import android.annotation.SuppressLint;

import com.spenditure.database.CategoryPersistence;
import com.spenditure.logic.UserManager;
import com.spenditure.logic.exceptions.InvalidCategoryException;
import com.spenditure.logic.exceptions.InvalidUserInformationException;
import com.spenditure.object.MainCategory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CategorySQL implements CategoryPersistence {

    private final String dbPath;
    private int currentCategoryID;

    public CategorySQL(final String dbPath) {
        this.dbPath = dbPath;
        this.currentCategoryID = initCategoryID();
        System.out.println("Largest CID: " + currentCategoryID);
    }

    private Connection connection() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }

    private int initCategoryID() {
        int largestCategoryID = 1;

        try(final Connection connection = connection()) {
            //final PreparedStatement statement = connection.prepareStatement("SELECT * FROM categories");
            //statement.setString(1, Integer.toString(userID));
            final Statement st = connection.createStatement();
            final ResultSet rs = st.executeQuery("SELECT * FROM categories");

            //final ResultSet resultSet = statement.executeQuery();
            while(rs.next()) {
                final int categoryID = rs.getInt("CATEGORYID");
                if(categoryID > largestCategoryID) {
                    largestCategoryID = categoryID;
                }
            }
            rs.close();
            st.close();

            return largestCategoryID;
        }
        catch (final SQLException e) {
            throw new RuntimeException("An error occurred while processing the SQL operation", e);  //temp exception
        }

    }

    private MainCategory fromResultSet(final ResultSet rs) throws SQLException {
        final String categoryName = rs.getString("CATEGORYNAME");
        final int categoryID = rs.getInt("CATEGORYID");
        final int userID = rs.getInt("USERID");
        return new MainCategory(categoryName, categoryID, userID);
    }

    @Override
    public List<MainCategory> getAllCategory(int userID) {
        final List<MainCategory> categories = new ArrayList<>();

        try(final Connection connection = connection()) {
            final PreparedStatement statement = connection.prepareStatement("SELECT * FROM categories\nWHERE USERID=?");
            statement.setString(1, Integer.toString(userID));

            final ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()) {
                final MainCategory category = fromResultSet(resultSet);
                categories.add(category);
            }
            resultSet.close();
            statement.close();

            return categories;
        }
        catch (final SQLException e) {
            //throw new PersistenceException(e);
            throw new RuntimeException("An error occurred while processing the SQL operation", e);  //temp exception
        }
    }

    //int userID, String newCategory
    @Override
    public MainCategory addCategory(String categoryName, int userID) {
        currentCategoryID++;
        MainCategory category = new MainCategory(categoryName,currentCategoryID,userID);
        //MainCategory category = new MainCategory(categoryName,1,userID);
        try(final Connection connection = connection()) {
            final PreparedStatement statement = connection.prepareStatement("INSERT INTO CATEGORIES VALUES(?, ?, ?)");
//            statement.setInt(1, categoryID);//FIX this
            statement.setInt(1, category.getCategoryID());//FIX this
            statement.setString(2, categoryName);
            statement.setInt(3, userID);

            statement.executeUpdate();

            return category;
        }
        catch (final SQLException e) {
            throw new RuntimeException("An error occurred while processing the SQL operation", e);  //temp exception
        }
    }

    @Override
    public void deleteCategoryByID(int categoryID) throws InvalidCategoryException {

        try(final Connection connection = connection()) {
            final PreparedStatement updateTransactions = connection.prepareStatement("UPDATE TRANSACTIONS\n SET categoryID = -1\nWHERE categoryID = ?;");
            updateTransactions.setInt(1,categoryID);
            updateTransactions.executeUpdate();

            final PreparedStatement statement = connection.prepareStatement("DELETE FROM categories\nWHERE CATEGORYID=?");
            statement.setInt(1,categoryID);
            statement.executeUpdate();

            //should this return something?
        }
        catch (final SQLException e) {
            throw new RuntimeException("An error occurred while processing the SQL operation", e);  //temp exception
        }
    }

    @Override
    public MainCategory getCategoryByID(int categoryID) throws InvalidCategoryException {

        try(final Connection connection = connection()) {
            final PreparedStatement statement = connection.prepareStatement("SELECT * FROM categories\nWHERE CATEGORYID=?");
            statement.setInt(1,categoryID);

            final ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()) {
                return fromResultSet(resultSet);
            }
            else {
                throw new InvalidUserInformationException("No category with id: " + categoryID);
            }





//            resultSet.close();
//            statement.close();



        }
        catch (final SQLException e) {
            throw new RuntimeException("An error occurred while processing the SQL operation", e);  //temp exception
        }

    }


    @Override
    public void printCategoryTable() {
        try(final Connection connection = connection()) {
            final Statement st = connection.createStatement();
            final ResultSet rs = st.executeQuery("SELECT * FROM categories");
            while (rs.next())
            {
                int userID = rs.getInt("USERID");
                String categoryName = rs.getString("CATEGORYNAME");
                int categoryID = rs.getInt("CATEGORYID");


                @SuppressLint("DefaultLocale") String printUser = String.format("UserID: %d, CategoryID: %d, Category Name: %s", userID, categoryID, categoryName);
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
