package com.spenditure.logic.exceptions;

public class InvalidDateException extends InvalidDateTimeException {

    public InvalidDateException(String error) {

        super("The provided date is not a valid date: \n" + error);

    }
}
