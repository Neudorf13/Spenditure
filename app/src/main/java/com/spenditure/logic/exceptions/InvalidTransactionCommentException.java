package com.spenditure.logic.exceptions;

import com.spenditure.logic.TransactionValidator;

public class InvalidTransactionCommentException extends InvalidTransactionException {

    public InvalidTransactionCommentException(String error) {

        super("Transaction comments may not be more than "
                + TransactionValidator.COMMENT_CHAR_LENGTH
                + " characters long.\n" + error);

    }

}
