package com.spenditure.logic;

import com.spenditure.logic.exceptions.InvalidUserInformationException;

public class UserValidator {

    public static final int USERNAME_CHARACTER_LIMIT = 32;
    public static final int PASSWORD_CHARACTER_LIMIT = 32;
    public static final int PASSWORD_MIN_LENGTH = 9;
    public static final int MIN_PASSWORD_NUMERICAL = 3;

    public static void validateUsername(String username) throws InvalidUserInformationException {

        if( username == null || username.isEmpty() || username.trim().isEmpty()
                || username.length() > USERNAME_CHARACTER_LIMIT ) {

            throw new InvalidUserInformationException("Username cannot be empty or over "+USERNAME_CHARACTER_LIMIT
                +"characters long.");

        }

    }

    public static void validatePassword(String password) throws InvalidUserInformationException {

        if( password == null || password.isEmpty() || password.trim().isEmpty() || password.length() < PASSWORD_MIN_LENGTH
                || password.length() > PASSWORD_CHARACTER_LIMIT ) {

            throw new InvalidUserInformationException("Password cannot be empty or over "+PASSWORD_CHARACTER_LIMIT
                    +" characters long.");

        }

        containsNumber(password);

    }

    private static void containsNumber(String password) {

        int numbersPresent = 0;

        for(int i=0; i<10; i++) {

            if(password.contains(String.valueOf(i)))
                numbersPresent++;

        }

        if(numbersPresent < MIN_PASSWORD_NUMERICAL)
            throw new InvalidUserInformationException("Password must contain at least "+MIN_PASSWORD_NUMERICAL
            +" numerical characters.");

    }

}
