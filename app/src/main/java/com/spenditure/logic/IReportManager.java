package com.spenditure.logic;

import com.spenditure.object.IDateTime;
import com.spenditure.object.IReport;

import java.util.ArrayList;

public interface IReportManager {

    public IReport reportOnLastYear();

    public ArrayList<IReport> reportOnLastYearByMonth();

    public ArrayList<IReport> reportOnLastMonthByWeek();

    public IReport reportOnUserProvidedDates(IDateTime start, IDateTime end);
}
