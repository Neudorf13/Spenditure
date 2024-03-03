package com.spenditure.logic.exceptions;

public class InvalidLogInException extends RuntimeException {
    public InvalidLogInException(String error){
        super("No user is logged in");
    }
}
