package com.spenditure.logic;


import com.spenditure.application.Services;
import com.spenditure.database.UserPersistence;
import com.spenditure.logic.exceptions.InvalidUserInformationException;

import static com.spenditure.logic.UserValidator.*;

public class UserManager {

    private UserPersistence accountPersistence;
    private static int userID = -1; //Store ID of user that are currently using the app

    //Constructor
    public UserManager(boolean getStubDB){
        accountPersistence = Services.getAccountPersistence(getStubDB);

    }
    public int login(String username, String password) throws InvalidUserInformationException {
        if(username == null || password == null || username.equals("") || password.equals("")){
            throw new InvalidUserInformationException("Username and password fields may not be left empty.");
        }else{
            if(userID <0){
                int userIDReturn = accountPersistence.login(username,password);
                UserManager.userID = userIDReturn;
                return userIDReturn;
            }else{
                throw new InvalidUserInformationException("User must be logged out before logging in.");
            }

        }
    }; //Return user id
    public String getUserName(int userID){
        return accountPersistence.getUserName(userID);
    };
    public boolean changePassword(int userID, String oldPassword, String newPassword){
        if(oldPassword == null || newPassword == null || oldPassword.equals("") || newPassword.equals("")){
            throw new InvalidUserInformationException("Current password and previous password fields may not be left blank.");
        } else if(oldPassword.equals(newPassword)) {
            throw new InvalidUserInformationException("New password cannot be the same as the previous password.");
        } else {
            validatePassword(newPassword);
            return accountPersistence.changePassword(userID,oldPassword,newPassword);
        }
    };
    public boolean changeUsername(int providedUserID, String newUsername){
        if(newUsername == null || newUsername.equals("")){
            throw new InvalidUserInformationException("New username field must not be blank.");
        } else if (newUsername.equals(getUserName(providedUserID))) {
            throw new InvalidUserInformationException("New username cannot be the same as the previous username.");
        } else {
            validateUsername(newUsername);
            return accountPersistence.changeUsername(providedUserID, newUsername);
        }
    };
    public int register(String username, String password){
        if(username == null || password == null || username.equals("") || password.equals("")){
            throw new InvalidUserInformationException("Username and password fields may not be left empty.");
        }else{
            validateUsername(username);
            validatePassword(password);
            int newUserID = accountPersistence.register(username,password);
            UserManager.userID = newUserID;
            return newUserID;
        }
    };

    public void logout() throws InvalidUserInformationException{
        if (userID >= 0){
            userID = -1;
        }else{
            throw new InvalidUserInformationException("User must be logged in before being able to log out.");
        }
    }

    public static int getUserID() throws InvalidUserInformationException{
        if(userID >= 0) {
            return userID;
        }else {
            throw  new InvalidUserInformationException("User must be logged in before getting a User ID.");
        }
    }

    //For testing purpose
    public static void cleanup(){
        Services.restartAccountDB(true);
        UserManager.userID = -1;
    }
}
