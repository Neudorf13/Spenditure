package com.spenditure.database;

import java.sql.*;
public class HyperDBTest {
    static final String URL = "jdbc:hsqldb:hsql://localhost/";

    public static void main(String[] args) {
        System.out.println("hello?");
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            test();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void test() throws SQLException {
        Connection conn = DriverManager.getConnection(URL,"SA","");

        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM users;");

        while(resultSet.next()) {
            System.out.println("ID: " + resultSet.getString("text"));
        }
    }
}
