package com.spenditure.object;

/**
 * User class
 * @author Bao Ngo
 * @version 06 Feb 2024
 */
public class User implements IUser{
    //Attributes
    private String fullName;
    private String username;
    private String phoneNumber;
    private String email;

    //Constructors
    public User(String username,String fullName, String phoneNumber, String email){
        this.username = username;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    //Business methods
    @Override
    public String getName() {
        return this.fullName;
    }

    @Override
    public String getPhone() {
        return this.phoneNumber;
    }

    @Override
    public String getEmail() {
        return this.email;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public void updateName(String newFullName) {
        this.fullName = newFullName;
    }

    @Override
    public void updateUserName(String newUserName) {
        this.username = newUserName;
    }

    @Override
    public void updateEmail(String newEmail) {
        this.email = newEmail;
    }

    @Override
    public void updatePhone(String newPhone) {
        this.phoneNumber = newPhone;
    }
}
