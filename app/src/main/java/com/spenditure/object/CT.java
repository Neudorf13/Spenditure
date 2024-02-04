package com.spenditure.object;

public class CT implements ICT {

    MainCategory category;
    Transaction transaction;

    public CT(MainCategory category, Transaction transaction)
    {
        this.category = category;
        this.transaction = transaction;
    }

    public MainCategory getCategory()
    {
        return category;
    }

    public Transaction getTransaction()
    {
        return transaction;
    }
}
