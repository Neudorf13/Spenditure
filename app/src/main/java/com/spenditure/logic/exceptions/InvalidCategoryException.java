package com.spenditure.logic.exceptions;

public class InvalidCategoryException extends RuntimeException {
    //Comment
    public InvalidCategoryException(String error){
        super("Fail to read Category data:\n" +  error);
    }
}
