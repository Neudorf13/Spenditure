/**
 * InvalidDateTimeException.java
 *
 * COMP3350 SECTION A02
 *
 * @author Bao,
 * @date Feb 7, 2024
 *
 * PURPOSE:
 *  Provide more information on what went wrong
 *
 **/

package com.spenditure.logic.exceptions;

public class InvalidDateTimeException extends InvalidTransactionException {

    public InvalidDateTimeException(String error) {

        super("Date/Time value is invalid:\n");

    }

}
