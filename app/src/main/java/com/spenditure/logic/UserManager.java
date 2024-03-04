package com.spenditure.logic;

import com.spenditure.application.Services;
import com.spenditure.database.UserPersistence;
import com.spenditure.logic.exceptions.InvalidUserInformationException;

public class UserManager {

    private UserPersistence accountPersistence;
    private static int userID = -1; //Store ID of user that are currently using the app

    //Constructor
    public UserManager(boolean getStubDB){
        accountPersistence = Services.getUserPersistence(getStubDB);

    }
    public int login(String username, String password) throws InvalidUserInformationException {
        if(username == null || password == null || username.equals("") || password.equals("")){
            throw new InvalidUserInformationException("Please provide username and password");
        }else{
            if(userID <0){
                int userIDReturn = accountPersistence.login(username,password);
                UserManager.userID = userIDReturn;
                return userIDReturn;
            }else{
                throw new InvalidUserInformationException("Please logout before login");
            }

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
    public boolean changeUsername(int providedUserID, String newUsername){
        if(newUsername == null || newUsername == "" ){
            throw new InvalidUserInformationException("Please provide new username");
        }else{
            return accountPersistence.changeUsername(providedUserID,newUsername);
        }
    };
    public int register(String username, String password){
        if(username == null || password == null || username == "" || password == ""){
            throw new InvalidUserInformationException("Please provide username and password");
        }else{
            int newUserID = accountPersistence.register(username,password,null);
            UserManager.userID = newUserID;
            return newUserID;
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
        if(userID < 0) {
            return userID;
        }else {
            throw new InvalidUserInformationException("Please login before get user id " + userID);
        }
    }

    //For testing purpose
    public static void cleanup(){
        Services.restartAccountDB(true);
        UserManager.userID = -1;
    }
}
