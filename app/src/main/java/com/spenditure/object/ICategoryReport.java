package com.spenditure.object;

import java.util.List;

public interface ICategoryReport {

    public IMainCategory getCategory();

    public double getTotalSpending();

    public int getNumTransactions();

    public double getAverageTransactions();

    public double getPercentage();

}
