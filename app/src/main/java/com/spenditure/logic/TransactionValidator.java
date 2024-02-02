package com.spenditure.logic;

import com.spenditure.object.Transaction;

public class TransactionValidator {

    //Minimum amount to increment values by: 0.01 to store values to the nearest cent
    private static final double MIN_AMOUNT_INCREMENT = 0.01;
    //Max number of characters allowed in a comment
    private static final int COMMENT_CHAR_LENGTH = 300;

    /*
        validateTransaction

        returns true if all values for the transaction are valid
     */
    public static boolean validateTransaction(Transaction t) {

        boolean validName = validateName(t.getName());
        boolean validDate = DateTimeValidator.validateDateTime(t.getDateTime());
        boolean validPlace = validatePlace(t.getPlace());
        boolean validAmount = validateAmount(t.getAmount());
        boolean commentLimitCheck = checkComment(t.getComments());
        //Type is a boolean, so it can't be invalid

        return validName && validDate && validPlace && validAmount && commentLimitCheck;

    }

    /*
        validateName

        ensures the name is not null and at least 1 character long
     */
    private static boolean validateName(String name) {

        return name != null && name.length() > 0;

    }

    /*
        validatePlace

        ensures the place is not null and at least 1 character long
     */
    private static boolean validatePlace(String place) {

        return place != null && place.length() > 0;

    }

    /*
        validateAmount

        ensures the amount is at least 0 and fits the minimum increment value
     */
    private static boolean validateAmount(double amount) {

        return amount >= 0
                && amount % MIN_AMOUNT_INCREMENT == 0;

    }

    /*
        checkComment

        ensures the comment meets the character limit, if a comment
        exists
     */
    private static boolean checkComment(String comment) {

        boolean withinLimit = true;

        if( comment != null )
            withinLimit = comment.length() > COMMENT_CHAR_LENGTH;

        return withinLimit;

    }



}
