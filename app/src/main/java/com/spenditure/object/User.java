package com.spenditure.object;

public class User implements IUser{
    private String fullName;
    private String username;
    private String phoneNumber;
    private String email;


    public User(String username,String fullName, String phoneNumber, String email){
        this.username = username;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

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
