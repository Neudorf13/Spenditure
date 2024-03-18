package com.spenditure.database.hsqldb;

import com.spenditure.database.SecurityQuestionPersistence;
import com.spenditure.logic.exceptions.InvalidGoalException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SecurityQuestionSQL implements SecurityQuestionPersistence {

    private final String dbPath;

    public SecurityQuestionSQL(final String dbPath) {
        this.dbPath = dbPath;
    }

    private Connection connection() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }

    @Override
    public String getSecurityQuestion(int sid) {
        try(final Connection connection = connection()) {
            final PreparedStatement statement = connection.prepareStatement("SELECT * FROM SECURITYQUESTIONS\nWHERE SECURITYQID=?");
            statement.setInt(1,sid);

            final ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()) {
                return resultSet.getString("SECURITYQUESTION");
            }
            else {
                throw new InvalidGoalException("No securityQuestion with sid: " + sid);
            }

        }
        catch (final SQLException e) {
            throw new RuntimeException("An error occurred while processing the SQL operation5", e);
        }
    }

    @Override
    public ArrayList<String> getAllSecurityQuestions() {

        ArrayList<String> questions = new ArrayList<>();
        String question = "";
        try(final Connection connection = connection()) {
            final Statement st = connection.createStatement();
            final ResultSet rs = st.executeQuery("SELECT * FROM transactions");


            while(rs.next()) {
                question = rs.getString("SECURITYQUESTION");
                questions.add(question);
            }

            return questions;

        }
        catch (final SQLException e) {
            throw new RuntimeException("An error occurred while processing the SQL operation5", e);
        }
    }
}
