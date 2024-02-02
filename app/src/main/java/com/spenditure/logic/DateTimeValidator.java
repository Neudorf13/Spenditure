package com.spenditure.logic;


import com.spenditure.object.DateTime;

public class DateTimeValidator {

    //Months with 30 days rather than 31
    private static final int[] THIRTY_DAY_MONTHS = { 4, 6, 9, 11 };
    //February, which has 28 or 29 days
    private static final int FEBRUARY = 2;
    //Number of hours in a day, can be changed to 12 if only 12-hour time is used
    private static final int MAX_HOURS = 24;
    //Earliest year allowed to be entered
    private static final int MIN_YEAR = 2000;



    /*
        validateDateTime

        returns true if the DateTime has valid values.
     */
    public static boolean validateDateTime(DateTime dateTime) {

        return validateDate(dateTime) && validateTime(dateTime);

    }

    /*
        validateDate

        returns true if the date values are valid according
        to a standard Gregorian calendar.
     */
    public static boolean validateDate(DateTime date) {
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
    public static boolean validateTime(DateTime time) {

        int hour = time.getHour();
        int minute = time.getMinute();

        return validateHour(hour) && validateMinute(minute);

    }

    /*
        validateHour

        ensures hour value is less than MAX_HOURS and at least 0.
     */
    private static boolean validateHour(int hour) { return MAX_HOURS > hour && hour >= 0; }

    /*
        validateMinute

        ensures minute value is less than 60 and at least 0.
     */
    private static boolean validateMinute(int minute) { return 60 > minute && minute >= 0; }

    /*
        validateYear

        ensures year is greater than or equal to the minimum allowed year
     */
    private static boolean validateYear(int year) { return year >= MIN_YEAR; }

    /*
        validateMonth

        ensures the month value is between 12 (inclusive) and 0 (exclusive).
     */
    private static boolean validateMonth(int month) { return 12 >= month && month > 0; }

    /*
        validateDay

        ensures the day valie is between 31 (inclusive) and 0 (exclusive).
     */
    private static boolean validateDay(int day) { return 31 >= day && day > 0; }

    /*
        validateMonthDay

        checks to make sure the day value is within the appropriate range for
        the month it's paired with
     */
    private static boolean validateMonthDay(int month, int day, boolean leapYear) {

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

        return numDays >= day && day > 0;

    }

    /*
        checkLeapYear

        checks the year to see if it's a leap year
     */
    private static boolean checkLeapYear(int year) {

        return year % 4 == 0
                && year % 100 == 0
                && year % 400 == 0;

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
