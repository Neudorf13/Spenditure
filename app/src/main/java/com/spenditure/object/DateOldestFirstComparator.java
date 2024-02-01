package com.spenditure.object;

import java.util.Comparator;

public class DateOldestFirstComparator implements Comparator<Transaction> {

    @Override
    public int compare(Transaction a, Transaction b) {
        // compare years
        if(b.getDateTime().getYear() - a.getDateTime().getYear() != 0)
        {
            return b.getDateTime().getYear() - a.getDateTime().getYear();
        }
        // compare months
        else if(b.getDateTime().getMonth() - a.getDateTime().getMonth() != 0)
        {
            return b.getDateTime().getMonth() - a.getDateTime().getMonth();
        }
        // compare days
        else if(b.getDateTime().getDay() - a.getDateTime().getDay() != 0)
        {
            return b.getDateTime().getDay() - a.getDateTime().getDay();
        }
        // compare hours
        else if(b.getDateTime().getHour() - a.getDateTime().getHour() != 0)
        {
            return b.getDateTime().getHour() - a.getDateTime().getHour();
        }
        else if(b.getDateTime().getMinute() - a.getDateTime().getMinute() != 0)
        {
            return b.getDateTime().getMinute() - a.getDateTime().getMinute();
        }
        else
        {
            return 0;
        }
    }
}
