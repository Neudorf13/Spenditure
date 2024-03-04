package com.spenditure.logic;

import com.spenditure.logic.exceptions.InvalidStringFormat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class StringInputValidator {

    private static final int STRING_MAX_LENGTH = 20;

    public static void  validateInputString(String text) throws InvalidStringFormat {

        if(text.length() > STRING_MAX_LENGTH) throw  new InvalidStringFormat("Maximum 20 character length!");
    }
}
