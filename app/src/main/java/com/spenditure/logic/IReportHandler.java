package com.spenditure.logic;

import com.spenditure.object.CategoryStatistics;
import com.spenditure.object.DateTime;
import com.spenditure.object.IDateTime;
import com.spenditure.object.IReport;
import com.spenditure.object.Report;

import java.util.ArrayList;

public interface IReportHandler {

    public IReport reportOnLastYear();

    public ArrayList<IReport> reportOnLastYearByMonth();

    public ArrayList<IReport> reportOnLastMonthByWeek();

    public IReport reportOnUserProvidedDates();
}
