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
    private String securityQuestion;


    //Constructors
    public User(String username,String fullName, String email){
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.securityQuestion = "";
    }

    //will convert to using this constructor once we finish implementing security question in regristration
    public User(String fullName, String username, String email, String securityQuestion) {
        this.fullName = fullName;
        this.username = username;
        this.email = email;
        this.securityQuestion = securityQuestion;
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

    public String getSecurityQuestion() {
        return securityQuestion;
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

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }
}
