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
            final PreparedStatement statement = connection.prepareStatement("SELECT * FROM categories\nWHERE userID=?"); //might need to change this from * to name, id
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
    public MainCategory addCategory(MainCategory category) {

        try(final Connection connection = connection()) {
            final PreparedStatement statement = connection.prepareStatement("INSERT INTO CATEGORIES VALUES(?, ?)");
            statement.setString(1, category.getName());
            statement.setInt(2, category.getUserID());

            statement.executeUpdate();

            return category;
        }
        catch (final SQLException e) {
            throw new RuntimeException("An error occurred while processing the SQL operation", e);  //temp exception
        }
    }

    @Override
    public void deleteCategoryByID(int userID, int categoryID) throws InvalidCategoryException {

    }

    @Override
    public MainCategory getCategoryByID(int userID, int categoryID) throws InvalidCategoryException {
        return null;
    }


}
