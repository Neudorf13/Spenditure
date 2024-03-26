package com.spenditure.logic;

import com.spenditure.logic.exceptions.InvalidUserInformationException;

import org.jetbrains.annotations.TestOnly;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator {

    //Maximum characters for a username
    public static final int USERNAME_CHARACTER_LIMIT = 32;
    //Maximum characters for a password
    public static final int PASSWORD_CHARACTER_LIMIT = 32;
    //Minumum characters for a password
    public static final int PASSWORD_MIN_LENGTH = 3;
    //Minumum number of numerical characters to be present in a password
    public static final int MIN_PASSWORD_NUMERICAL = 3;
    //Maximum number of characters in an email, according to Simple Mail Transfer Protocol RFC 3521 4.5.3.1.3
    public static final int MAX_EMAIL_LENGTH = 256;
    //Minimum possible length of an email; a@b.co
    public static final int MIN_EMAIL_LENGTH = 6;

    /*

        validateUsernameAndPassword

        Validates both the username and password conveniently in one call.
        Both are given as strings, and evaluated according to the constraints
        defined by the class constants above.

     */
    public static void validateUsernameAndPassword(String username, String password) throws InvalidUserInformationException {

        validateUsername(username);

        validatePassword(password);

    }

    /*

        validateUsername

        Takes in a String and determines if it's a valid username. If not, throws an appropriate exception.

     */
    public static void validateUsername(String username) throws InvalidUserInformationException {

        if( username == null || username.isEmpty() || username.trim().isEmpty()
                || username.length() > USERNAME_CHARACTER_LIMIT ) {

            throw new InvalidUserInformationException("Username cannot be blank or over "+USERNAME_CHARACTER_LIMIT
                +" characters long.\nUsername provided: "+username);

        }

    }

    /*

        validatePassword

        Takes in a String a determines if it's a valid password. If not, throws an appropriate exception.

     */
    public static void validatePassword(String password) throws InvalidUserInformationException {

        if( password == null || password.isEmpty() || password.trim().isEmpty() || password.length() < PASSWORD_MIN_LENGTH
                || password.length() > PASSWORD_CHARACTER_LIMIT ) {

            throw new InvalidUserInformationException("Password cannot be blank, over "+PASSWORD_CHARACTER_LIMIT
                    +" characters long, or less than "+PASSWORD_MIN_LENGTH+" characters long.\nPassword provided: "+password);

        }

        containsNumber(password);

    }

    /*

       containsNumber

       Takes in a String, and ensures that it has the appropriate amount of numerical characters to qualify
       as a password. If not, throws an appropriate exception.

    */
    private static void containsNumber(String password) {

        int numbersPresent = 0;

        for(int i=0; i<10; i++) {

            if(password.contains(String.valueOf(i)))
                numbersPresent++;

        }

        if(numbersPresent < MIN_PASSWORD_NUMERICAL)
            throw new InvalidUserInformationException("Password must contain at least "+MIN_PASSWORD_NUMERICAL
                    +" numerical characters.\nPassword provided: "+password);

    }

    /*

        validateEmail

        Takes in a String and determines if it is a valid email address.

     */
    public static void validateEmail(String email) throws InvalidUserInformationException {

        if( email == null || email.isEmpty() || email.trim().isEmpty() || email.length() < MIN_EMAIL_LENGTH
                || email.length() > MAX_EMAIL_LENGTH ) {

            throw new InvalidUserInformationException("Email cannot be blank, over "+MAX_EMAIL_LENGTH
                    +" characters long, or less than "+MIN_EMAIL_LENGTH+"characters.\nEmail provided: "+email);

        }

        if(!isValidEmail(email)) {
            throw new InvalidUserInformationException("Provided email is not formatted correctly. Emails must" +
                    "contain exactly one \"@\" symbol, one \".\" symbol, and be in the general format of:" +
                    "\n\"johnny.appleseed@domain.com\".\nEmail provided: "+email);
        }

    }


    public static boolean isValidEmail(String email) {
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }


}
