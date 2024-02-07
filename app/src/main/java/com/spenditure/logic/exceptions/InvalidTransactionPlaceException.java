package com.spenditure.logic.exceptions;

public class InvalidTransactionPlaceException extends InvalidTransactionException {

    public InvalidTransactionPlaceException(String error) {

        super("\"Place\" field must not be left empty:\n" + error);

    }

}
