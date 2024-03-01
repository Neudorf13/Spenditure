package com.spenditure.logic;

import com.spenditure.application.Services;
import com.spenditure.database.AccountPersistence;
import com.spenditure.database.CategoryPersistence;
import com.spenditure.logic.exceptions.InvalidUserInformationException;

public class AccountManager {

    private AccountPersistence accountPersistence;
    private static int userID = -1; //Store ID of user that are currently using the app

    //Constructor
    public AccountManager(boolean getStubDB){
        accountPersistence = Services.getAccountPersistence(getStubDB);

    }
    public int login(String username, String password) throws InvalidUserInformationException {
        if(username == null || password == null || username == "" || password == ""){
            throw new InvalidUserInformationException("Please provide username and password");
        }else{
            int userIDReturn = accountPersistence.login(username,password);
            userID = userIDReturn;
            return userIDReturn;
        }
    }; //Return user id
    public String getUserName(int userID){
        return accountPersistence.getUserName(userID);
    };
    public boolean changePassword(int userID, String oldPassword, String newPassword){
        if(oldPassword == null || newPassword == null || oldPassword == "" || newPassword == ""){
            throw new InvalidUserInformationException("Please provide current and new password");
        }else{
            return accountPersistence.changePassword(userID,oldPassword,newPassword);
        }
    };
    public boolean changeUsername(int userID, String newUsername){
        if(newUsername == null || newUsername == "" ){
            throw new InvalidUserInformationException("Please provide new username");
        }else{
            return accountPersistence.changeUsername(userID,newUsername);
        }
    };
    public int register(String username, String password){
        if(username == null || password == null || username == "" || password == ""){
            throw new InvalidUserInformationException("Please provide username and password");
        }else{
            return accountPersistence.register(username,password);
        }
    };

    public void logout() throws InvalidUserInformationException{
        if (userID >= 0){
            userID = -1;
        }else{
            throw  new InvalidUserInformationException("Please login before logout");
        }
    }

    public static int getUserID() throws InvalidUserInformationException{
        if(userID >= 0) {
            return userID;
        }else {
            throw  new InvalidUserInformationException("Please login before logout");
        }
    }
}
