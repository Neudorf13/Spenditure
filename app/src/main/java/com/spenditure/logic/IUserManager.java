package com.spenditure.logic;

import java.security.NoSuchAlgorithmException;

public interface IUserManager {

    public int login(String username, String password) throws NoSuchAlgorithmException;

    public String getUserName(int userID);

    public boolean changePassword(int userID, String oldPassword, String newPassword) throws NoSuchAlgorithmException;

    public boolean changeUsername(int providedUserID, String newUsername);

    public int register(String username, String password, String email, String securityAnswer, int securityQuestionID)
            throws NoSuchAlgorithmException;

    public void logout();

}
