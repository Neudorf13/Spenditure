package com.spenditure.object;

public class DateTime implements IDateTime{

    private int year;

    private int month;

    private int day;

    private int hour;

    private int minute;

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

    public DateTime(int year, int month, int day, int hour, int minute)
    {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
    }

    public String toString() {

        String adjustMinutesPrefix = "";
        String adjustMinutesSuffix = "";

        //Correct minute to write "??:00" instead of "??:0"
        if(minute == 0) adjustMinutesSuffix = "0";
        //Correct minute to write ??:0X" instead of "??:X"
        if(minute < 10) adjustMinutesPrefix = "0";

        return MONTHS[month - 1] + " " + day + " " + year + ", "
                + hour + ":" + adjustMinutesPrefix + minute + adjustMinutesSuffix;

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

}
