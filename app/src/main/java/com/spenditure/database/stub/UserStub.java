package com.spenditure.database.stub;

import com.spenditure.database.UserPersistence;
import com.spenditure.logic.exceptions.InvalidUserInformationException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;


public class UserStub implements UserPersistence {
    private List<UserRow> userTable;
    private int autoIncrementID = 1;

    public UserStub(){
        this.userTable = new ArrayList<>();
        userTable.add(new UserRow("Me","123",autoIncrementID++,"aaa@gmail.com"));
        userTable.add(new UserRow("You","1234",autoIncrementID++,"bbb@gmail.com"));
        userTable.add(new UserRow("He","12345",autoIncrementID++,"ccc@gmail.com"));
    }

    @Override
    public int login(String username, String password) throws InvalidUserInformationException {
        for (UserRow user : userTable) {
            if(user.getUsername().equals( username)){
                if(password.equals(user.getPassword())){
                    return user.getUserID();
                }else{
                    throw new InvalidUserInformationException("Wrong username or password");
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
    public int getNumberOfUsers() {
        return userTable.size();
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
    public int register(int userID_need_to_fix, String username, String password, String email) throws InvalidUserInformationException {

        for (UserRow user : userTable) {
            if(user.getUsername().equals(username)){
                throw new InvalidUserInformationException("Username already exist");
            }
        }
        int userID = autoIncrementID++;
        userTable.add(new UserRow(username,password,userID, email));
        return userID;
    }

    private class UserRow{
        private String username;
        private String password;
        private int userID;
        private String email;
        public UserRow(String username,String password,int userID, String email){
            this.username = username;
            this.password = password;
            this.userID = userID;
            this.email = email;
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

        public String getEmail() {
            return email;
        }

        int getUserID(){
            return this.userID;
        }


    }

}
