package com.spenditure.logic.exceptions;

public class InvalidDateTimeException extends InvalidTransactionException {

    public InvalidDateTimeException(String error) {

        super("Date/Time value is invalid:\n");

    }

}
