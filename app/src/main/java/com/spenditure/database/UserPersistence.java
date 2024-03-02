package com.spenditure.database;

public interface UserPersistence {

    String getUserName(int userID);
    public int getNumberOfUsers();
    public int login(String username, String password); //Return user id
    public boolean changePassword(int userID, String oldPassword, String newPassword);
    public boolean changeUsername(int userID, String newUsername);
    public int register(String username, String password);

}
