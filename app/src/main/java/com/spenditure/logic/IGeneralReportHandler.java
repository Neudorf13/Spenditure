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

import com.spenditure.object.CategoryReport;
import com.spenditure.object.MainCategory;

import java.util.ArrayList;
import java.util.List;

public interface IGeneralReportHandler {

    public int numTransactions(int userID, int categoryID);

    public double totalSpending(int userID, int categoryID);

    public double averageSpending(int userID, int categoryID);

    public double percentage(int userID, int categoryID);

    public int numTransactions(int userID);

    public double totalSpending(int userID);

    public double averageSpending(int userID);

    public CategoryReport getCategoryReport(int userID, int categoryID);

    public ArrayList<MainCategory> sortByTotal(int userID, boolean descending);

    public ArrayList<MainCategory> sortByPercent(int userID, boolean descending);

    public ArrayList<MainCategory> sortByAverage(int userID, boolean descending);

    public List<CategoryReport> getAllCategoryReport(int userID);
}
