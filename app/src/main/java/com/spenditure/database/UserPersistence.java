package com.spenditure.database;

public interface UserPersistence {

    String getUserName(int userID);
    int getNumberOfUsers();
    int login(String username, String password); //Return user id
    boolean changePassword(int userID, String oldPassword, String newPassword);
    boolean changeUsername(int userID, String newUsername);
    int register(int userID, String username, String password, String email);
    void printUserTable();
}
