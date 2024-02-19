package com.spenditure.object;

import java.util.ArrayList;
import java.util.List;

public class Report implements IReport{

    private double avgTransSize;

    private int numTrans;

    private double stdDev;

    private ArrayList<MainCategory> categoryList;

    private ArrayList<Integer> amtPerCategory;

    public Report(double avgTransSize, int numTrans, double stdDev, ArrayList<MainCategory> categoryList, ArrayList<Integer> amtPerCategory)
    {
        this.avgTransSize = avgTransSize;
        this.numTrans = numTrans;
        this.stdDev = stdDev;
        this.categoryList = categoryList;
        this.amtPerCategory = amtPerCategory;
    }

    public double getAvgTransSize()
    {
        return avgTransSize;
    }

    public int getNumTrans()
    {
        return numTrans;
    }

    public double getStdDev()
    {
        return stdDev;
    }

    public List<MainCategory> getCategoryList()
    {
        return categoryList;
    }

    public List<Integer> getAmtPerCategory()
    {
        return amtPerCategory;
    }
}
