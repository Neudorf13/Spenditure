package com.spenditure.database.stub;

import com.spenditure.database.AccountPersistence;
import com.spenditure.logic.exceptions.InvalidUserInformationException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class AccountStub implements AccountPersistence {
    private List<UserRow> userTable;
    private int autoIncrementID = 1;

    public AccountStub(){
        this.userTable = new ArrayList<>();
        userTable.add(new UserRow("Me","123",autoIncrementID++));
        userTable.add(new UserRow("You","1234",autoIncrementID++));
        userTable.add(new UserRow("He","12345",autoIncrementID++));
    }

    @Override
    public int login(String username, String password) throws InvalidUserInformationException {
        for (UserRow user : userTable) {
            if(user.getUsername().equals( username)){
                if(password.equals(user.getPassword())){
                    return user.getUserID();
                }else{
                    throw new InvalidUserInformationException("Wrong password or username");
                }
            }
        }
        throw new InvalidUserInformationException("Wrong password or username");
    }

    @Override
    public String getUserName(int userID) throws InvalidUserInformationException {
        for (UserRow user : userTable) {
            if(user.getUserID() == userID){
                return user.getUsername();

            }
        }
        throw new InvalidUserInformationException("User ID not exist.");
    }

    @Override
    public boolean changePassword(int userID, String oldPassword, String newPassword) throws InvalidUserInformationException{
        for (UserRow user : userTable) {
            if(user.getUserID() == userID){
                if(user.getPassword().equals(oldPassword)){
                    user.updatePassword(newPassword);
                    return true;
                }else {
                    throw new InvalidUserInformationException("Wrong password");
                }

            }
        }
        throw new InvalidUserInformationException("User ID not exist.");
    }

    @Override
    public boolean changeUsername(int userID, String newUsername) throws InvalidUserInformationException {
        for (UserRow user : userTable) {
            if(user.getUserID() == userID){
                user.updateUserName(newUsername);
            }
        }
        throw new InvalidUserInformationException("User ID not exist.");
    }

    @Override
    public int register(String username, String password) throws InvalidUserInformationException {

        for (UserRow user : userTable) {
            if(user.getUsername().equals(username)){
                throw new InvalidUserInformationException("Username already exist");
            }
        }
        int userID = autoIncrementID++;
        userTable.add(new UserRow(username,password,userID));
        return userID;
    }

    private class UserRow{
        private String username;
        private String password;
        private int userID;
        public UserRow(String username,String password,int userID){
            this.username = username;
            this.password = password;
            this.userID = userID;
        }

        private void updateUserName(String newUsername){
            this.username= username;
        }

        private void updatePassword (String newPassword){
            this.password = newPassword;
        }

        private String getPassword(){
            return this.password;
        }

        private String getUsername (){
            return this.username;
        }

        int getUserID(){
            return this.userID;
        }


    }
}
