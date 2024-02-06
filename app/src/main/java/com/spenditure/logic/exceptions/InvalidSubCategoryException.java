package com.spenditure.logic.exceptions;

public class InvalidSubCategoryException extends RuntimeException {
    public InvalidSubCategoryException(String error){
        super("Fail to read Sub-Category data:\n" +  error);
    }
}
