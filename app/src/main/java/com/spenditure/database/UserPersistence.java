package com.spenditure.database;

public interface UserPersistence {
    public int login(String username, String password); //Return user id
    public String getUserName(int userID);
    public boolean changePassword(int userID, String oldPassword, String newPassword);
    public boolean changeUsername(int userID, String newUsername);
    public int register(String username, String password,String email);
}
