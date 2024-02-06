package com.spenditure.logic.exceptions;

public class InvalidTransactionAmountException extends InvalidTransactionException {

    public InvalidTransactionAmountException(String error) {

        super("\"Amount\" value cannot be a negative number:\n" + error);

    }
}
