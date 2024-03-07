package com.spenditure.object;

/**
 * User class
 * @author Bao Ngo
 * @version 06 Feb 2024
 */
public class User {
    //Attributes
    private String fullName;
    private String username;

    private String email;


    //Constructors
    public User(String username,String fullName, String email){
        this.username = username;
        this.fullName = fullName;
        this.email = email;
    }

    //Business methods
    public String getName() {
        return this.fullName;
    }



    public String getEmail() {
        return this.email;
    }


    public String getUsername() {
        return this.username;
    }


    public void updateName(String newFullName) {
        this.fullName = newFullName;
    }


    public void updateUserName(String newUserName) {
        this.username = newUserName;
    }


    public void updateEmail(String newEmail) {
        this.email = newEmail;
    }


}
