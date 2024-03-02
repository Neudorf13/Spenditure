package com.spenditure.object;

import java.util.List;

public interface IReport {

    public double getAvgTransSize();

    public int getNumTrans();

    public double getStdDev();

    public double getTotal();
    public double getPercentage();

    public List<CategoryStatistics> getCategoryStatisticsList();
}
