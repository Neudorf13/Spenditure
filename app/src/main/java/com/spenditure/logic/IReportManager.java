package com.spenditure.logic;

import com.spenditure.object.DateTime;
import com.spenditure.object.IDateTime;
import com.spenditure.object.IReport;

import java.util.ArrayList;

public interface IReportManager {

    public IReport reportBackOneYear(int userID, IDateTime yearEnd);

    public ArrayList<IReport> reportBackOnLastYearByMonth(int userID, DateTime today);

    public ArrayList<IReport> reportBackOneMonthByWeek(int userID, DateTime start);

    public IReport reportOnUserProvidedDates(int userID, IDateTime start, IDateTime end);

}
