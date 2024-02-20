package com.spenditure.object;

import java.util.List;

public interface IReport {

    public double getAvgTransSize();

    public int getNumTrans();

    public double getStdDev();

    public List<MainCategory> getCategoryList();

    public List<Integer> getAmtPerCategory();

    public List<CategoryStatistics> getCategoryStatisticsList();
}
