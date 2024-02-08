/**
 * TransactionValidator.java
 *
 * COMP3350 SECTION A02
 *
 * @author Toran Pillay, 7842389
 * @date Tuesday, February 6, 2024
 *
 * PURPOSE:
 *  This file contains all of the methods necessary to validate the data in a
 * Transaction.
 **/

package com.spenditure.logic;

import com.spenditure.object.Transaction;
import com.spenditure.logic.exceptions.*;



public class TransactionValidator {

    //Max number of characters allowed in a comment
    public static final int COMMENT_CHAR_LIMIT = 300;

    /*
        validateTransaction

        returns true if all values for the transaction are valid
     */
    public static boolean validateTransaction(Transaction t) throws InvalidTransactionException {

        boolean valid = false;

        if(t != null) {
            boolean validName = validateName(t.getName());
            boolean validDate = DateTimeValidator.validateDateTime(t.getDateTime());
            boolean validPlace = validatePlace(t.getPlace());
            boolean validAmount = validateAmount(t.getAmount());
            boolean commentLimitCheck = checkComment(t.getComments());
            //Type is a boolean, so it can't be invalid

            valid = validName && validDate && validPlace && validAmount && commentLimitCheck;
        }

        return valid;

    }

    /*
        validateName

        ensures the name is not null and at least 1 character long
     */
    private static boolean validateName(String name) throws InvalidTransactionNameException {

        if( name == null || name.length() == 0 )
            throw new InvalidTransactionNameException("Provided name was blank.");
        else
            return true;

    }

    /*
        validatePlace

        ensures the place is not null and at least 1 character long
     */
    private static boolean validatePlace(String place) throws InvalidTransactionPlaceException {

        if( place == null || place.length() == 0 )
            throw new InvalidTransactionPlaceException("Provided place was blank.");
        else
            return true;
//        return place != null && place.length() > 0;

    }

    /*
        validateAmount

        ensures the amount is at least 0 and fits the minimum increment value
     */
    private static boolean validateAmount(double amount) throws InvalidTransactionAmountException {

        if(amount < 0)
            throw new InvalidTransactionAmountException("Provided amount "+amount+" is a negative number.");
        else
            return true;

    }

    /*
        checkComment

        ensures the comment meets the character limit, if a comment
        exists
     */
    private static boolean checkComment(String comment) throws InvalidTransactionCommentException {

        boolean withinLimit = true;

        if( comment != null )
            withinLimit = comment.length() <= COMMENT_CHAR_LIMIT;

        if( withinLimit )
            return withinLimit;

        else {
            int overflow = comment.length() - COMMENT_CHAR_LIMIT;
            throw new InvalidTransactionCommentException("Provided comment was " + overflow + " characters too long.");
        }

    }



}
