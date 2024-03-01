package com.spenditure.logic;

import com.spenditure.application.Services;
import com.spenditure.database.AccountPersistence;
import com.spenditure.database.CategoryPersistence;
import com.spenditure.logic.exceptions.InvalidUserInformationException;

public class AccountManager {

    private AccountPersistence accountPersistence;

    //Constructor
    public AccountManager(boolean getStubDB){
        accountPersistence = Services.getAccountPersistence(getStubDB);

    }
    public int login(String username, String password) throws InvalidUserInformationException {
        if(username == null || password == null || username == "" || password == ""){
            throw new InvalidUserInformationException("Please provide username and password");
        }else{
            return accountPersistence.login(username,password);
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
}
