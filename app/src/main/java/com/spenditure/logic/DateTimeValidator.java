/**
 * DateTimeValidator.java
 *
 * COMP3350 SECTION A02
 *
 * @author Toran Pillay, 7842389
 * @date Tuesday, February 6, 2024
 *
 * PURPOSE:
 *  This file contains all of the methods necessary to validate DateTimes, ensuring
 * that they are actual valid dates and times.
 **/

package com.spenditure.logic;

import com.spenditure.logic.exceptions.*;
import com.spenditure.object.DateTime;

import java.time.LocalDate;


public class DateTimeValidator {

    //Number of hours in a day, can be changed to 12 if only 12-hour time is used
    public static final int MAX_HOURS = 24;
    //Number of minutes in an hour
    public static final int MAX_MINUTES = 60;
    //Number of seconds in a minute
    public static final int MAX_SECONDS = 60;
    //Months with 30 days rather than 31
    private static final int[] THIRTY_DAY_MONTHS = { 4, 6, 9, 11 };
    //February, which has 28 or 29 days
    private static final int FEBRUARY = 2;
    //Earliest year allowed to be entered
    public static final int MIN_YEAR = 2000;

    //Latest year allowed to be entered (current year)
    public static final int MAX_YEAR = LocalDate.now().getYear();


    /*
        validateDateTime

        returns true if the DateTime has valid values.
     */
    public static boolean validateDateTime(DateTime dateTime) throws InvalidDateTimeException {

        return validateDate(dateTime) && validateTime(dateTime);

    }

    /*
        validateDate

        returns true if the date values are valid according
        to a standard Gregorian calendar.
     */
    public static boolean validateDate(DateTime date) throws InvalidDateException {
        boolean valid = false;

        int year = date.getYear();
        int month = date.getMonth();
        int day = date.getDay();

        if(validateYear(year) && validateMonth(month) && validateDay(day)) {

            boolean leapYear = checkLeapYear(year);

            valid = validateMonthDay(month, day, leapYear);

        }

        return valid;

    }

    /*
        validateTime

        returns true if the time values are valid.
     */
    public static boolean validateTime(DateTime time) throws InvalidTimeException {

        int hour = time.getHour();
        int minute = time.getMinute();
        int second = time.getSeconds();

        return validateHour(hour) && validateMinute(minute) && validateSecond(second);

    }

    /*
        validateHour

        ensures hour value is less than MAX_HOURS and at least 0.
     */
    private static boolean validateHour(int hour) throws InvalidTimeException {

        if( hour >= MAX_HOURS || hour < 0 )
            throw new InvalidTimeException("Provided hour value (" + hour + ") must be at least 0 and at most 23.");

        return true;

    }

    /*
        validateMinute

        ensures minute value is less than 60 and at least 0.
     */
    private static boolean validateMinute(int minute) throws InvalidTimeException {

        if( minute >= MAX_MINUTES || minute < 0 )
            throw new InvalidTimeException("Provided minute value (" + minute + ") must be at least 0 and at most 59.");

        return true;
    }

    /*
     validateSecond

     ensures second value is less than 60 and at least 0.
    */
    private static boolean validateSecond(int second) throws InvalidTimeException {

        if( second >= MAX_SECONDS || second < 0 )
            throw new InvalidTimeException("Provided second value (" + second + ") must be at least 0 and at most 59.");

        return true;
    }

    /*
        validateYear

        ensures year is greater than or equal to the minimum allowed year
     */
    private static boolean validateYear(int year) throws InvalidDateException {

        if( year > MAX_YEAR || year < MIN_YEAR )
            throw new InvalidDateException("Provided year value (" + year + ") must be between " + MIN_YEAR + " and " + MAX_YEAR + ".");

        return true;
    }

    /*
        validateMonth

        ensures the month value is between 12 (inclusive) and 0 (exclusive).
     */
    private static boolean validateMonth(int month) throws InvalidDateException {

        if( month > 12 || month <= 0 )
            throw new InvalidDateException("Provided month value (" + month + ") must be at least 1 and at most 12.");

        return true;
    }

    /*
        validateDay

        ensures the day value is between 31 (inclusive) and 0 (exclusive).
     */
    private static boolean validateDay(int day) throws InvalidDateException {

        if( day > 31 || day <= 0 )
            throw new InvalidDateException("Provided day value (" + day + ") must be at least 1 and at most 31.");

        return true;
    }

    /*
        validateMonthDay

        checks to make sure the day value is within the appropriate range for
        the month it's paired with
     */
    private static boolean validateMonthDay(int month, int day, boolean leapYear) throws InvalidDateException {

        int numDays;

        if(checkThirtyDayMonth(month)) {

            numDays = 30;

        } else if(month == FEBRUARY) {

            if (leapYear)
                numDays = 29;
            else
                numDays = 28;

        } else {

            numDays = 31;

        }

        if( numDays >= day && day > 0 )
            return true;

        else {

            String leapYearMessage = "";

            if( month == FEBRUARY ) {
                if (leapYear)
                    leapYearMessage = "The year entered is a leap year.";
                else
                    leapYearMessage = "The year entered is not a leap year.";
            }


            throw new InvalidDateException("Provided day value (" + day + ") must be at least 1 and at most " + numDays + " for the given month. " + leapYearMessage);

        }

    }

    /*
        checkLeapYear

        checks the year to see if it's a leap year. Formula taken from
        https://www.wikihow.com/Calculate-Leap-Years#:~:text=How%20to%20Calculate%20Leap%20Years%20Using%20Division%201,but%20it%20is%20not%20evenly%20divisible...%20See%20More.
     */
    private static boolean checkLeapYear(int year) {

        return ( year % 4 == 0 && year % 100 != 0 )
                || (year % 100 == 0 && year % 400 == 0);

    }

    /*
        checkThirtyDayMonth

        checks the month to see if it's in a list of the months that
        only have 30 days
     */
    private static boolean checkThirtyDayMonth(int month) {

        for(int i = 0; i < THIRTY_DAY_MONTHS.length; i++)

            if(month == THIRTY_DAY_MONTHS[i])
                return true;

        return false;
    }

}
