/**
 * DateTime.java
 *
 * COMP3350 SECTION A02
 *
 * @author Jared Rost,
 * @date Feb 7, 2024
 *
 * PURPOSE:
 *  Store all info related to datetime object
 *
 **/

package com.spenditure.object;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTime implements IDateTime{

    private int year;

    private int month;

    private int day;

    private int hour;

    private int minute;

    private int second;

    //Relates month names to month value; names correlate to month-1
    private static final String[] MONTHS = {
            "January",
            "February",
            "March",
            "April",
            "May",
            "June",
            "July",
            "August",
            "September",
            "October",
            "November",
            "December"
    };

    public DateTime(int year, int month, int day, int hour, int minute, int second)
    {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    public DateTime(int year) {

        this.year = year;
        this.month = -1; // -1 signifies it was not given
        this.day = -1;
        hour = 0;
        minute = 0;
        second = 0;
    }

    public DateTime(int year, int month) {

        this.year = year;
        this.month = month;
        this.day = -1; // -1 signifies that it was not given
        hour = 0;
        minute = 0;
        second = 0;
    }

    public DateTime(int year, int month, int day) {

        this.year = year;
        this.month = month;
        this.day = day;
        hour = 0;
        minute = 0;
        second = 0;
    }

    public DateTime(int year, int month, int day, int hour, int minute) {

        this(year, month, day);

        this.hour = hour;
        this.minute = minute;

    }

    public DateTime(String dateString) {


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime dateTime = LocalDateTime.parse(dateString, formatter);

        this.year = dateTime.getYear();
        this.month = dateTime.getMonthValue();
        this.day = dateTime.getDayOfMonth();
        this.hour = dateTime.getHour();
        this.minute = dateTime.getMinute();
        this.second = dateTime.getSecond();
    }

    public String printWrittenDate() {

        String adjustMinutesPrefix = "";
        String adjustMinutesSuffix = "";

        //Correct minute to write "??:00" instead of "??:0"
        if(minute == 0) adjustMinutesSuffix = "0";
        //Correct minute to write ??:0X" instead of "??:X"
        if(minute < 10) adjustMinutesPrefix = "0";

        return MONTHS[month - 1] + " " + day + " " + year + ", "
                + hour + ":" + adjustMinutesPrefix + minute + adjustMinutesSuffix;

    }

    public String toString() {

        String separator = "-";

        return year + separator + month + separator + day + separator
                + hour + separator + minute + separator + second;

    }

    public String printDate() {

        String separator = "/";

        return month + separator + day + separator + year;

    }


//    public static DateTime FromTimestamp(Timestamp timestamp)
//    {
//        // Convert milliseconds since the epoch to seconds
//        long secondsSinceEpoch = timestamp.Value / 1000;
//
//        // Create a DateTimeOffset object representing the timestamp
//        DateTimeOffset dateTimeOffset = DateTimeOffset.FromUnixTimeSeconds(secondsSinceEpoch);
//
//        // Extract year, month, day, hour, minute, and second components
//        int year = dateTimeOffset.Year;
//        int month = dateTimeOffset.Month;
//        int day = dateTimeOffset.Day;
//        int hour = dateTimeOffset.Hour;
//        int minute = dateTimeOffset.Minute;
//        int seconds = dateTimeOffset.Second;
//
//        // Create and return a new DateTime object using the constructor
//        return new DateTime(year, month, day, hour, minute, seconds);
//    }

    /*
        compare()

        Compares this DateTime to another DateTime. Return value will be:
        - POSITIVE if this DateTime is more recent than the other DateTime;
        - NEGATIVE if this DateTime is older than the other DateTime;
        - ZERO if the DateTimes are the same.
     */
    public int compare(IDateTime other) {

        if( year - other.getYear() != 0 ) {
            return year - other.getYear();

        } else if( month - other.getMonth() != 0 ) {
            return month - other.getMonth();

        } else if( day - other.getDay() != 0 ) {
            return day - other.getDay();

        } else if( hour - other.getHour() != 0 ) {
            return hour - other.getHour();

        } else if( minute - other.getMinute() != 0 ) {
            return minute - other.getMinute();

        } else if(second - other.getSeconds() != 0) {
            return second - other.getSeconds();
        } else
            return 0;

    }

    public void adjust( int changeYear, int changeMonth, int changeDay, int changeHour, int changeMinute ) {

        year += changeYear;

        month += changeMonth;

        day += changeDay;

        hour += changeHour;

        minute += changeMinute;

    }

    public DateTime copy() {

        return new DateTime(year, month, day, hour, minute, second);

    }

    public int getYear()
    {
        return year;
    }

    public int getMonth()
    {
        return month;
    }

    public int getDay()
    {
        return day;
    }

    public int getHour()
    {
        return hour;
    }

    public int getMinute()
    {
        return minute;
    }

    public int getSeconds() { return second; }
}
