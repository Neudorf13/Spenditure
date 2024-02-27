package com.spenditure.object;

import java.util.List;

public interface IReport {

    public double getAvgTransSize();

    public int getNumTrans();

    public double getStdDev();

    public List<CategoryStatistics> getCategoryStatisticsList();
}
