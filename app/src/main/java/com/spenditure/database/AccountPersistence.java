package com.spenditure.database;

public interface AccountPersistence {
    public int login(String username, String password); //Return user id
    public int getUserName(int userID);
    public boolean changePassword(int userID, String oldPassword, String newPassword);
    public boolean changeUsername(int userID, String newUsername);
}
