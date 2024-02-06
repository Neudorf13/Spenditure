package com.spenditure.logic.exceptions;

public class InvalidTransactionNameException extends InvalidTransactionException {

    public InvalidTransactionNameException(String error) {

        super("\"Title\" field must not be left empty:\n" + error);

    }
}
