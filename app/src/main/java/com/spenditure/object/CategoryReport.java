package com.spenditure.object;

public class CategoryReport implements ICategoryReport{

    double totalSpending;

    int numTransactions;

    double averageTransaction;

    double percentage;


    public CategoryReport(double totalSpending, int numTransactions, double averageTransaction, double percentage)
    {
        this.totalSpending = totalSpending;
        this.numTransactions = numTransactions;
        this.averageTransaction = averageTransaction;
        this.percentage = percentage;
    }
    public double getTotalSpending()
    {
        return totalSpending;
    }

    public int getNumTransactions()
    {
        return numTransactions;
    }

    public double getAverageTransactions()
    {
        return averageTransaction;
    }

    public double getPercentage()
    {
        return percentage;
    }
}
