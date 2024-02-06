package com.spenditure.logic.exceptions;

public class InvalidTimeException extends InvalidDateTimeException {

    public InvalidTimeException(String error) {

        super("The provided time is not valid; ensure it is between 00:00 and 23:59:\n" + error);

    }

}
