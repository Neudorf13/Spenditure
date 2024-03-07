package com.spenditure.object;

import java.util.ArrayList;
import java.util.List;

public class Report {

    private double avgTransSize;

    private int numTrans;

    private double stdDev;

    private double percent;


    private ArrayList<CategoryStatistics> categoryStatisticsList;

    public Report(double avgTransSize, int numTrans, double stdDev, double percent, ArrayList<CategoryStatistics> categoryStatisticsList)
    {
        this.avgTransSize = avgTransSize;
        this.numTrans = numTrans;
        this.stdDev = stdDev;
        this.percent = percent;
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

    public double getPercent() { return percent; }

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

//    public double getPercentage(){
//        double percentage = 0;
//        for(CategoryStatistics categoryStatistics: this.categoryStatisticsList){
//            percentage += categoryStatistics.getPercent();
//        }
//        return percentage;
//
//    }
}
