package com.spenditure.logic;

import com.spenditure.object.ICategoryReport;
import com.spenditure.object.IMainCategory;
import com.spenditure.object.MainCategory;

import java.util.ArrayList;

public interface IGeneralReportHandler {

    public int numTransactions(int categoryID);

    public double totalSpending(int categoryID);

    public double averageSpending(int categoryID);

    public ICategoryReport getCategoryReport(int categoryID);

    public ArrayList<IMainCategory> sortByTotal(boolean descending);

    public ArrayList<IMainCategory> sortByPercent(boolean descending);

    public ArrayList<IMainCategory> sortByAverage(boolean descending);
}
