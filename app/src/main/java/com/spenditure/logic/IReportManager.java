package com.spenditure.logic;

import com.spenditure.object.DateTime;
import com.spenditure.object.IDateTime;
import com.spenditure.object.IReport;

import java.util.ArrayList;

public interface IReportManager {

    public IReport reportOnLastYear(int userID);

    public ArrayList<IReport> reportOnLastYearByMonth(int userID);

    public ArrayList<IReport> reportOnLastMonthByWeek(int userID);

    public IReport reportOnUserProvidedDates(IDateTime start, IDateTime end, int userID);
}
