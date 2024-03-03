/**
 * IGeneralReportHandler
 *
 * COMP3350 SECTION A02
 *
 * @author JR,
 * @date Mar 2
 *
 * PURPOSE:
 *  Interface detailing what functions GeneralReportHandler should have
 *
 **/


package com.spenditure.logic;

import com.spenditure.object.ICategoryReport;
import com.spenditure.object.IMainCategory;
import com.spenditure.object.MainCategory;

import java.util.ArrayList;

public interface IGeneralReportHandler {

    public int numTransactions(int userID, int categoryID);

    public double totalSpending(int userID, int categoryID);

    public double averageSpending(int userID, int categoryID);

    public ICategoryReport getCategoryReport(int userID, int categoryID);

    public ArrayList<IMainCategory> sortByTotal(int userID, boolean descending);

    public ArrayList<IMainCategory> sortByPercent(int userID, boolean descending);

    public ArrayList<IMainCategory> sortByAverage(int userID, boolean descending);
}
