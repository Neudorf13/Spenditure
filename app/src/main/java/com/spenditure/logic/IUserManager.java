package com.spenditure.logic;

public interface IUserManager {

    public int login(String username, String password);

    public String getUserName(int userID);

    public boolean changePassword(int userID, String oldPassword, String newPassword);

    public boolean changeUsername(int providedUserID, String newUsername);

    public int register(String username, String password);

    public void logout();

}
