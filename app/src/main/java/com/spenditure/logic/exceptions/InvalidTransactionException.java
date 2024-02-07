package com.spenditure.logic.exceptions;

public class InvalidTransactionException extends RuntimeException {

    public InvalidTransactionException(String error) {

        super("Transaction data is invalid:\n" + error);

    }

}
