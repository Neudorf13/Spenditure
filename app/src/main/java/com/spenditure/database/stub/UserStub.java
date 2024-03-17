package com.spenditure.database.stub;

import com.spenditure.database.UserPersistence;
import com.spenditure.logic.exceptions.InvalidUserInformationException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;


public class UserStub implements UserPersistence {
    private List<UserRow> userTable;
    private int autoIncrementID = 1;

    public UserStub(){
        this.userTable = new ArrayList<>();
        userTable.add(new UserRow("Me","a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3",autoIncrementID++,"aaa@gmail.com"));
        userTable.add(new UserRow("You","03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4",autoIncrementID++,"bbb@gmail.com"));
        userTable.add(new UserRow("He","5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5",autoIncrementID++,"ccc@gmail.com"));
    }



    @Override
    public int login(String username, String password) throws InvalidUserInformationException, NoSuchAlgorithmException {
        for (UserRow user : userTable) {
            //String hashedPassword = hashPassword(password);
            System.out.println("Hashed Password: " + password);


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
                return true;
            }
        }
        throw new InvalidUserInformationException("User ID not exist.");
    }

    @Override
    public int register(String username, String password, String email) throws InvalidUserInformationException {
        System.out.println("test in register");
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
            this.username= newUsername;
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

        public int getUserID(){
            return this.userID;
        }


    }

}