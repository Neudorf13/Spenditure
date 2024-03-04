package com.spenditure.logic;

import static com.spenditure.logic.DateTimeValidator.*;
import static java.lang.Math.abs;

import com.spenditure.logic.exceptions.InvalidDateException;
import com.spenditure.logic.exceptions.InvalidDateTimeException;
import com.spenditure.object.DateTime;

public class DateTimeAdjuster {

    public static DateTime correctDateTime(DateTime toFix) {

        boolean fixed = false;
        DateTime result = toFix.copy();

        while(!fixed) {

            try {

                validateDateTime(result);
                fixed = true;

            } catch(InvalidDateTimeException ignore) {

                fixSeconds(result);

                fixMinutes(result);

                fixHours(result);

                fixDays(result);

                fixMonths(result);

                checkYears(result);

            }

        }

        return result;

    }

   private static void fixSeconds(DateTime dateTime) {

        int seconds = dateTime.getSeconds();

        while(seconds < 0 || seconds >= MAX_SECONDS) {

            if (seconds < 0) {
                dateTime.absoluteAdjust(0, 0, 0, 0, -1, MAX_SECONDS);

            } else {
                dateTime.absoluteAdjust(0, 0, 0, 0, 1, -MAX_SECONDS);
            }

            seconds = dateTime.getSeconds();
        }
   }

   private static void fixMinutes(DateTime dateTime) {

       int minutes = dateTime.getMinute();

       while(minutes < 0 || minutes >= MAX_MINUTES) {

           if (minutes < 0) {
               dateTime.absoluteAdjust(0, 0, 0, -1, MAX_MINUTES, 0);

           } else {
               dateTime.absoluteAdjust(0, 0, 0, 1, -MAX_MINUTES, 0);
           }
           minutes = dateTime.getMinute();

       }
   }

   private static void fixHours(DateTime dateTime) {

        int hours = dateTime.getHour();

        while(hours < 0 || hours >= MAX_HOURS) {

            if (hours < 0) {
                dateTime.absoluteAdjust(0, 0, -1, MAX_HOURS, 0, 0);

            } else {
                dateTime.absoluteAdjust(0, 0, 1, -MAX_HOURS, 0, 0);
            }

            hours = dateTime.getHour();
        }
   }

   private static void fixDays(DateTime dateTime) {

        int days = dateTime.getDay();
        int month = dateTime.getMonth();
        int year = dateTime.getYear();
        int monthLimit = getMonthLimit(month, year);

        while(days <= 0 || days > monthLimit) {

            if (days <= 0) {
                month = month - 1;

                if(month <= 0)
                    monthLimit = getMonthLimit((abs(month)+12)%13, year);
                else
                    monthLimit = getMonthLimit(month, year);

                dateTime.absoluteAdjust(0, -1, monthLimit, 0, 0, 0);

            } else {
                month = month + 1;

                if(month >= 13)
                    monthLimit = getMonthLimit(month%12, year);
                else
                    monthLimit = getMonthLimit(month, year);

                dateTime.absoluteAdjust(0, 1, -monthLimit, 0, 0, 0);
            }

            days = dateTime.getDay();
        }
   }

   private static void fixMonths(DateTime dateTime) {

        int months = dateTime.getMonth();

        while(months <= 0 || months > 12) {

            if(months <= 0) {
                dateTime.absoluteAdjust(-1, 12, 0, 0, 0, 0);

            } else {
                dateTime.absoluteAdjust(1, -12, 0, 0, 0, 0);
            }

            months = dateTime.getMonth();
        }
   }

   private static void checkYears(DateTime dateTime) throws InvalidDateTimeException {

        int years = dateTime.getYear();

        if(years < MIN_YEAR) {
            throw new InvalidDateException("The requested adjustment goes beyond the minimum permitted year ("+MIN_YEAR+").");
        } else if(years > MAX_YEAR) {
            throw new InvalidDateException("The requested adjustment goes beyond the maximum permitted year ("+MAX_YEAR+").");
        }

   }

    private static int getMonthLimit(int month, int year) {

        int result = 28;
        boolean hitLimit = false;

        DateTime test = new DateTime(year, month, result);

        while(!hitLimit && result <= 31) {

            try {
                validateDateTime(test);

                result ++;
                test = new DateTime(year, month, result);

            } catch(InvalidDateException ignore) {
                hitLimit = true;
            }
        }

        return result - 1;

    }

}