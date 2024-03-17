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
    private String securityAnswer;


    //Constructors
//    public User(String username,String fullName, String email, String securityQuestion, String securityAnswer) {
    public User(String username, String fullName, String email) {
        this.username = username;
        this.fullName = fullName;
        this.email = email;
//        this.securityQuestion = securityQuestion;
//        this.securityAnswer = securityAnswer;
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

//    public String getSecurityQuestion() { return this.securityQuestion; }
//
//    public String getSecurityAnswer() { return this. securityAnswer; }


    public void updateName(String newFullName) {
        this.fullName = newFullName;
    }


    public void updateUserName(String newUserName) {
        this.username = newUserName;
    }


    public void updateEmail(String newEmail) {
        this.email = newEmail;
    }

//    public void setSecurityQuestion(String update) { this.securityQuestion = update; }
//    public void setSecurityAnswer(String update) { this.securityAnswer = update; }


}
