package com.spenditure.object;

import java.util.ArrayList;
import java.util.List;

public class Report implements IReport{

    private double avgTransSize;

    private int numTrans;

    private double stdDev;


    private ArrayList<CategoryStatistics> categoryStatisticsList;

    public Report(double avgTransSize, int numTrans, double stdDev, ArrayList<CategoryStatistics> categoryStatisticsList)
    {
        this.avgTransSize = avgTransSize;
        this.numTrans = numTrans;
        this.stdDev = stdDev;
        this.categoryStatisticsList = categoryStatisticsList;
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

    public List<CategoryStatistics> getCategoryStatisticsList()
    {
        return categoryStatisticsList;
    }

    public double getTotal(){
        double total = 0;
        for(CategoryStatistics categoryStatistics: this.categoryStatisticsList){
            total += categoryStatistics.getTotal();
        }
        return total;
    }

    public double getPercentage(){
        double percentage = 0;
        for(CategoryStatistics categoryStatistics: this.categoryStatisticsList){
            percentage += categoryStatistics.getPercent();
        }
        return percentage;

    }
}
