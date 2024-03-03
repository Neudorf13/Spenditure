package com.spenditure.database.hsqldb;

import com.spenditure.database.CategoryPersistence;
import com.spenditure.logic.exceptions.InvalidCategoryException;
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

    public CategorySQL(final String dbPath) {
        this.dbPath = dbPath;
    }

    private Connection connection() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }

    private MainCategory fromResultSet(final ResultSet rs) throws SQLException {
        final String categoryName = rs.getString("CATEGORYNAME");
        final String categoryID = rs.getString("CATEGORYID");
        final String userID = rs.getString("USERID");
        return new MainCategory(categoryName, Integer.parseInt(categoryID), Integer.parseInt(userID));
    }


    @Override
    public List<MainCategory> getAllCategory(int userID) {
        final List<MainCategory> categories = new ArrayList<>();

        try(final Connection connection = connection()) {
            final PreparedStatement statement = connection.prepareStatement("SELECT * FROM categories\nWHERE USERID=?"); //might need to change this from * to name, id
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

        MainCategory newCategoty = new MainCategory(categoryName,1,userID);//Replace this with static variable
        try(final Connection connection = connection()) {
            final PreparedStatement statement = connection.prepareStatement("INSERT INTO CATEGORIES VALUES(?, ?)");
            statement.setString(1, newCategoty.getName());
            statement.setInt(2, newCategoty.getUserID());

            statement.executeUpdate();

            return newCategoty;
        }
        catch (final SQLException e) {
            throw new RuntimeException("An error occurred while processing the SQL operation", e);  //temp exception
        }
    }

    @Override
    public void deleteCategoryByID(int categoryID) throws InvalidCategoryException {

        try(final Connection connection = connection()) {
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
            final MainCategory category = fromResultSet(resultSet);

            resultSet.close();
            statement.close();

            return category;

        }
        catch (final SQLException e) {
            throw new RuntimeException("An error occurred while processing the SQL operation", e);  //temp exception
        }

    }


}
